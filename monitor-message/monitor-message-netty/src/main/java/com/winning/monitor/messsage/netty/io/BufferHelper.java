package com.winning.monitor.messsage.netty.io;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by nicholasyan on 16/9/6.
 */
public class BufferHelper {

    private EscapingBufferWriter bufferWriter = new EscapingBufferWriter();

    public int write(ByteBuf buf, byte b) {
        buf.writeByte(b);
        return 1;
    }

    public int write(ByteBuf buf, String str) {
        if (str == null) {
            str = "null";
        }

        byte[] data = str.getBytes();

        buf.writeBytes(data);
        return data.length;
    }

    public int writeRaw(ByteBuf buf, String str) {
        if (str == null) {
            str = "null";
        }

        byte[] data;

        try {
            data = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            data = str.getBytes();
        }

        return bufferWriter.writeTo(buf, data);
    }

}
