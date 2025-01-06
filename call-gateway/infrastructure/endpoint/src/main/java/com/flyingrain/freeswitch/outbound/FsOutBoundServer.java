package com.flyingrain.freeswitch.outbound;

import com.flyingrain.freeswitch.adapter.FreeswitchCallAdapter;
import com.flyingrain.freeswitch.configs.FreeswitchConfig;
import com.flyingrain.freeswitch.outbound.configs.OutboundServerConfig;
import com.flyingrain.freeswitch.outbound.handlers.FsOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class FsOutBoundServer   {

    private ServerBootstrap bootstrap;

    @Autowired
    private FreeswitchConfig config;

    @Autowired
    private FreeswitchCallAdapter callAdapter;


    @PostConstruct
    public void init() throws IOException {
        bootstrap = new ServerBootstrap();
        log.info("outbound server start on port:[{}]",config.getFsOutboundPort());
        bootstrap.group(new NioEventLoopGroup(4))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("stringEncode", new StringEncoder());
                        ch.pipeline().addLast("lineDecoder", new LineBasedFrameDecoder(8192));
//                        ch.pipeline().addLast("myHandler", new ChannelInboundHandlerAdapter() {
//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf byteBuf = (ByteBuf) msg;
//                                ByteBuf temp = byteBuf.copy();
//                                log.info(temp.readCharSequence(temp.readableBytes(), Charset.defaultCharset()).toString());
//                                log.info("----------------");
//                                ctx.fireChannelRead(msg);
//                            }
//                        });
                        ch.pipeline().addLast("eslOutboundHandler", new FsOutboundHandler(callAdapter));
                    }
                }).bind(config.getFsOutboundPort());
    }

}
