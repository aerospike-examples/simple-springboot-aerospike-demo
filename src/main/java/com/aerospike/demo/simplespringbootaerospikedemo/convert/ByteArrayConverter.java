package com.aerospike.demo.simplespringbootaerospikedemo.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

public class ByteArrayConverter {
    @ReadingConverter
    public enum ByteDataReadConverter implements Converter<byte[], byte[]> {
        INSTANCE;

        @Override
        public byte[] convert(byte[] source) {
            return source;
        }
    }
}
