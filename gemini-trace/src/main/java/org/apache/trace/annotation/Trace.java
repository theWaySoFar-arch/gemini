package org.apache.trace.annotation;

import java.lang.annotation.*;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe trace注解
 * @date 2023/9/8 21:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
public @interface Trace {

}
