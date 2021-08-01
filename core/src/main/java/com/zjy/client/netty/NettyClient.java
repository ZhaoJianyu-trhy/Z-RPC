package com.zjy.client.netty;

import com.zjy.client.RpcClient;
import com.zjy.code.CommonDecoder;
import com.zjy.code.CommonEncoder;
import com.zjy.domain.RpcRequest;
import com.zjy.domain.RpcResponse;
import com.zjy.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 7:29 下午
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class NettyClient implements RpcClient {

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    static {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });

    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            log.info("连接到服务器, ip:{}, port:{}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        log.info(String.format("客户端发送消息：%s", rpcRequest));
                    } else {
                        log.error("发送消息错误", future1.cause());
                    }
                });
            }
            channel.closeFuture().sync();
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("response");
            RpcResponse rpcResponse = channel.attr(key).get();
            return rpcResponse.getData();
        } catch (InterruptedException e) {
            log.error("发送消息时有错误发生：", e);
        }
        return null;
    }
}
