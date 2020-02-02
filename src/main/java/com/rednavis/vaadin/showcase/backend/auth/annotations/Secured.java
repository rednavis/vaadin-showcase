package com.rednavis.vaadin.showcase.backend.auth.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.rednavis.vaadin.showcase.backend.auth.jwt.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Custom annotation for binding auth filter with secured resources.
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {
  Role[] value() default {};
}
