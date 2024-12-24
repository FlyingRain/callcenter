package com.flyingrain.freeswitch.outbound.handlers;

import com.flyingrain.freeswitch.model.FsEvent;
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

@Slf4j
public class FsOutboundHandler extends ChannelInboundHandlerAdapter {

    private static final String MESSAGE_TERMINATOR = "\n\n";

    private final Map<String, String> headers = new HashMap<>(64);

    private FsMessageReadStatus READ_STATUS = FsMessageReadStatus.HEADER;

    private final FsCallInListener callInListener;

    public FsOutboundHandler(FsCallInListener callInListener) {
        this.callInListener = callInListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("current state: [{}]", READ_STATUS);
        ByteBuf byteBuf = (ByteBuf) msg;
        switch (READ_STATUS) {
            case HEADER:
                readHead(byteBuf, ctx.channel());
                break;
            case BODY:
                break;
            default:
                throw new RuntimeException("unknown read status: " + READ_STATUS);
        }
        ctx.fireChannelRead(msg);
    }

    private void readHead(ByteBuf byteBuf, Channel channel) {
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
            callInListener.onCallIn(FsEvent.fromMap(headers), channel);
            if (headers.containsKey("content-Length")) {
                log.info("content-length: [{}]", headers.get("content-Length"));
                READ_STATUS = FsMessageReadStatus.BODY;
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("channel active send connect to fs");
        channel.writeAndFlush("connect" + MESSAGE_TERMINATOR);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel inactive!");
        super.channelInactive(ctx);
    }
}
