package apalala.exploros.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;

import apalala.exploros.utils.ApplicationConfiguration;;

@Stateless
public class GetConfiguration {

    @Inject
    @ApplicationConfiguration(name = "storage.mysql")
    private Configuration myConf;

    public Configuration getConfiguration() {
        return myConf;
    }
}
