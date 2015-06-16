package apalala.exploros.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR })
public @interface ApplicationConfiguration {
	    // no default meaning a value is mandatory
	    @Nonbinding
	    String name();
}
