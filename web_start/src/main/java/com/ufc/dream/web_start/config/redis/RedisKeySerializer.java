package com.ufc.dream.web_start.config.redis;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @program: cem
 * @description:
 * @author: superfish
 * @create: 2020-12-17 15:08
 **/
public class RedisKeySerializer implements RedisSerializer<Object> {
    private final Charset charset;
    private final ConversionService converter;

    public RedisKeySerializer() {
        this(StandardCharsets.UTF_8);
    }

    public RedisKeySerializer(Charset charset) {
        Objects.requireNonNull(charset, "Charset must not be null");
        this.charset = charset;
        this.converter = DefaultConversionService.getSharedInstance();
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return bytes == null ? null : new String(bytes, this.charset);
    }

    @Nullable
    @Override
    public byte[] serialize(Object object) {
        Objects.requireNonNull(object, "redis key is null");
        String key;
        if (object instanceof SimpleKey) {
            key = "";
        } else if (object instanceof String) {
            key = (String) object;
        } else {
            key = (String) this.converter.convert(object, String.class);
        }

        return key.getBytes(this.charset);
    }
}