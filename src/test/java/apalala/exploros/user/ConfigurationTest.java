package apalala.exploros.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import apalala.exploros.utils.ExplorosConfig;

// local test
public class ConfigurationTest {

	    ExplorosConfig config;
//	    private final Logger logger = LoggerFactory.getLogger(ConfigurationTest.class);

	    @BeforeClass
	    public static void setUp(){
	    	System.setProperty("apalala.config", "/Users/michal/Documents/projects/Apalala/target/test-classes/configuration.json");
	    }
	    
	    @Test
	    public void getConfig() throws ConfigurationException, IOException {
	    	config  = new ExplorosConfig();
	    	config.initTest();
	        
	        Configuration actual = config.getConfig("storage");
	        assertNotNull(actual);
	        actual = actual.subset("mysql");
	        assertNotNull(actual);
	        String value = actual.getString("host");
	        assertEquals( "localhost", value );
	    }
}
