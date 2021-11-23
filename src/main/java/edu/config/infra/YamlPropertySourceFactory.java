package edu.config.infra;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;
import java.util.Objects;
import java.util.Properties;


public class YamlPropertySourceFactory implements PropertySourceFactory {

    @NonNull
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        Properties loadedProperties = this.loadYamlIntoProperties(resource.getResource());
        return new PropertiesPropertySource(Objects.requireNonNull(resource.getResource().getFilename()), loadedProperties);
    }

    private Properties loadYamlIntoProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}