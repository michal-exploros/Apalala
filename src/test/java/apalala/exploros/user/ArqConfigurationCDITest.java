package apalala.exploros.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import apalala.exploros.utils.ExplorosConfig;

@RunWith(Arquillian.class)
public class ArqConfigurationCDITest {

    @Deployment
    public static Archive<?> getDeployment() {
    	
    	File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();
    	
        return ShrinkWrap.create(WebArchive.class, "apalala-test.war")
                .addPackages(true, GetConfiguration.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
    }

    @EJB
    GetConfiguration single;
    
	@Test
	public void testSingleConfiguration() {
    	Configuration theConfig = single.getConfiguration();
        assertFalse(theConfig.isEmpty());
        assertEquals( "localhost", theConfig.getString("host"));
	}
}
