package com.flyingrain.freeswitch.outbound.handlers;

import com.flyingrain.freeswitch.FsCommandExecuteHelper;
import com.flyingrain.freeswitch.model.FsEvent;
import com.flyingrain.freeswitch.model.FsExecuteResult;
import com.flyingrain.freeswitch.model.FsMsgNameConstants;
import com.flyingrain.freeswitch.outbound.FsCallInListener;
import com.flyingrain.freeswitch.outbound.enums.FsMessageReadStatus;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FsOutboundHandler extends ChannelInboundHandlerAdapter implements FsCommandExecuteHelper {

    private static final String MESSAGE_TERMINATOR = "\n\n";

    private static final String COMMAND_TERMINATOR = "\n";

    private final Map<String, String> headers = new HashMap<>(64);

    private FsMessageReadStatus READ_STATUS = FsMessageReadStatus.HEADER;

    private final FsCallInListener callInListener;

    private ArrayBlockingQueue<FsExecuteResult> results = new ArrayBlockingQueue<>(1024);

    private Channel currentChannel = null;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2, Runtime.getRuntime().availableProcessors()*4, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));

    public FsOutboundHandler(FsCallInListener callInListener) {
        this.callInListener = callInListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        switch (READ_STATUS) {
            case HEADER:
                readHead(byteBuf);
                break;
            case BODY:
                break;
            case COMPLETE:
                throw new RuntimeException("wrong read status: " + READ_STATUS);
            default:
                throw new RuntimeException("unknown read status: " + READ_STATUS);
        }
        if (READ_STATUS == FsMessageReadStatus.COMPLETE) {
            if ("CHANNEL_DATA".equals(headers.get(FsMsgNameConstants.EVENT_NAME))) {
                FsEvent fsEvent = FsEvent.fromMap(headers);
                executor.execute(()-> callInListener.onCallIn(fsEvent, this));
            }
            if ("command/reply".equals(headers.get(FsMsgNameConstants.CONTENT_TYPE))) {
                results.offer(FsExecuteResult.fromMap(headers));
            }
            headers.clear();
            READ_STATUS = FsMessageReadStatus.HEADER;
        }
        ctx.fireChannelRead(msg);
    }

    private void readHead(ByteBuf byteBuf) {
        String headerParam = byteBuf.readCharSequence(byteBuf.readableBytes(), Charset.defaultCharset()).toString();
        if (StringUtils.isNotBlank(headerParam)) {
            String[] keyValues = headerParam.split(":");
            if (keyValues.length != 2) {
                log.error("error header:[{}]", headerParam);
            } else {
                headers.put(keyValues[0].trim(), keyValues[1].trim());
            }
        } else {
            log.info("head end!");
            if (headers.containsKey("content-Length")) {
                log.info("content-length: [{}]", headers.get("content-Length"));
                READ_STATUS = FsMessageReadStatus.BODY;
            } else {
                READ_STATUS = FsMessageReadStatus.COMPLETE;
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        this.currentChannel = channel;
        log.info("channel active send connect to fs");
        channel.writeAndFlush("connect" + MESSAGE_TERMINATOR);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel inactive!");
        super.channelInactive(ctx);
    }

    @Override
    public FsExecuteResult execute(String command, String args) {
        log.info("start to execute command:[{}],args:[{}]", command, args);
        runCommand(command, args);
        try {
            FsExecuteResult fsExecuteResult = results.take();
            log.info("execute result:[{}]", fsExecuteResult);
            return fsExecuteResult;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void executeAsync(String command, String args) {
        log.info("start to executeAsync command:[{}],args:[{}]", command, args);
        runCommand(command, args);
    }

    private void runCommand(String command, String args) {
        currentChannel.writeAndFlush("sendmsg" + COMMAND_TERMINATOR);
        currentChannel.writeAndFlush("call-command: execute" + COMMAND_TERMINATOR);
        if (StringUtils.isEmpty(args)) {
            currentChannel.writeAndFlush("execute-app-name: " + command + MESSAGE_TERMINATOR);
        } else {
            currentChannel.writeAndFlush("execute-app-name: " + command + COMMAND_TERMINATOR);
            currentChannel.writeAndFlush("execute-app-arg: " + args + MESSAGE_TERMINATOR);
        }
    }
}
