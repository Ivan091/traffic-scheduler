package edu.config.infra;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@PropertySource(value = "", factory = YamlPropertySourceFactory.class)
public @interface YamlSource {

    @AliasFor(annotation = PropertySource.class)
    String[] value();
}
