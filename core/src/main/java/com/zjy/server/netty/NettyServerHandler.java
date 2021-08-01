package com.zjy.server.netty;

import com.zjy.domain.RpcRequest;
import com.zjy.domain.RpcResponse;
import com.zjy.registry.RegistryService;
import com.zjy.registry.RegistryServiceImpl;
import com.zjy.server.socket.RequestHandler;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static RequestHandler requestHandler;
    private static RegistryService registryService;

    static {
        requestHandler = new RequestHandler();
        registryService = new RegistryServiceImpl();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            log.info("服务器接收到请求:{}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = registryService.getService(interfaceName);
            RpcResponse result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(result);
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程中发生错误", cause.getMessage());
        ctx.close();
    }
}
