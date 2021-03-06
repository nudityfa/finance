package com.adminUtil.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.JsonJacksonCodec;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/3 5:39
 */
public class LjrwJsonJacksonCodec extends JsonJacksonCodec {

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }

    private final Encoder encoder = new Encoder() {
        @Override
        public ByteBuf encode(Object in) throws IOException {
            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
            try {
                ByteBufOutputStream os = new ByteBufOutputStream(out);
                Object write = in;
//                if (!isJavaClass(in.getClass())){
//                   write = JSON.toJSONString(in);
//                }
                mapObjectMapper.writeValue((OutputStream) os,write);
                return os.buffer();
            } catch (IOException e) {
                out.release();
                throw e;
            } catch (Exception e) {
                out.release();
                throw new IOException(e);
            }
        }
    };

    /**
     * 判断是否为自定义类
     * @param clz
     * @return
     */
    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }
}
