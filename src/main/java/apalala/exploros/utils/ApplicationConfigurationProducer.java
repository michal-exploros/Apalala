package apalala.exploros.utils;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;

public class ApplicationConfigurationProducer {

    @Inject
    private ExplorosConfig configuration;

    @Produces
    @ApplicationConfiguration(name = "")
    public Configuration getConfiguration(InjectionPoint injectionPoint) {

        String propertyName = injectionPoint.getAnnotated().getAnnotation(ApplicationConfiguration.class).name();
        Configuration value = configuration.getConfig(propertyName);

        return value;
    }
}
