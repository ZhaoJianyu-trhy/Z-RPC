package com.zjy.code;

import com.zjy.constant.PackageType;
import com.zjy.constant.RpcError;
import com.zjy.domain.RpcRequest;
import com.zjy.domain.RpcResponse;
import com.zjy.exception.RpcException;
import com.zjy.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CommonDecoder extends ReplayingDecoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();
        if (magic != MAGIC_NUMBER) {
            log.error("不识别的协议包:{}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packageType = in.readInt();
        Class<?> packageClass;
        if (packageType == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageType == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            log.error("不识别的数据包:{}", packageType);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getBycode(serializerCode);
        if (serializer == null) {
            log.error("不识别的反序列化器:{}", packageType);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, packageClass);
        out.add(obj);
    }
}
