package apalala.exploros.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;

import javax.ejb.EJB;

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
public class ArqConfigurationTest {

    @Deployment
    public static Archive<?> getDeployment() {
    	
    	File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();
    	
        return ShrinkWrap.create(WebArchive.class, "apalala-test.war")
                .addPackages(true, apalala.exploros.utils.ExplorosConfig.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
//                .addAsResource("creatures.xml")
//                .addAsServiceProvider(Extension.class, CreatureExtension.class);
    }

    @EJB
    ExplorosConfig config;

    @Test
    public void testFullConfiguration() {
    	Configuration theConfig = config.getConfig(null);
        assertFalse(theConfig.isEmpty());
        assertEquals("{mysql={host=localhost, database=exploros, username=exploros, password=exploros}, redis={host=localhost}, ldap={host=localhost, rootdn=cn=Manager,dc=exploros,dc=com, rootpw=openldap, users_path=ou=users,ou=app,dc=exploros,dc=com}}",theConfig.subset("storage"));
    }
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
