package apalala.exploros.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;

import apalala.exploros.utils.ApplicationConfiguration;

@Stateless
public class TestConfiguration {

    @Inject
    @ApplicationConfiguration(name = "storage.mysql")
    private Configuration myProperty;

    public Configuration getConfiguration() {
        return myProperty;
    }
}
