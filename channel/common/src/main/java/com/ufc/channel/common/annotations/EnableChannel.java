package com.ufc.channel.common.annotations;

import com.ufc.channel.common.config.ChannelConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ChannelConfig.class})
public @interface EnableChannel {


}
