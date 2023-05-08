package com.ufc.channel.common.annotations;

import com.ufc.channel.common.service.IBackService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelAfter {

    String value();

    String name();

}
