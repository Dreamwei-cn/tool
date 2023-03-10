package com.ufc.dream.commons.csv;

import java.lang.annotation.*;

/**
 * @author 四维穿梭
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CsvProperty {

    String value() default "";

    int index() default -1;
}
