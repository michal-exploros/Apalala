package apalala.exploros.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DynamicCombinedConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Startup
@Singleton
public class ExplorosConfig {
	
    private final Logger logger = LoggerFactory.getLogger(ExplorosConfig.class);


    final String ErrorMsg = "File %s does not exist or unreadable.";  
	private Configuration config;
    
	public void initTest() throws ConfigurationException, IOException {
		init();
	}
	
	@PostConstruct
    private void init() throws IOException, ConfigurationException {
        //matches the property name as defined in the system-properties element in WildFly
        String configurationFile = System.getProperty("apalala.config");
        logger.debug(configurationFile);
        setConfigurationFromFile(configurationFile);
    }

	
	private void setConfigurationFromFile(String configurationFile) throws IOException, ConfigurationException
	{
		if (configurationFile == null || configurationFile.length() == 0) {
        	String message = String.format("No File name %s", configurationFile);
        	logger.error(message);
			throw new IOException(message);
		}
        File configFile = new File(configurationFile);
        if (!(configFile.exists() && configFile.canRead())) {
        	String message = String.format(ErrorMsg, configurationFile);
        	logger.error(message);
			throw new IOException(message);
		}
        if (configurationFile.endsWith(".json")) 
        {
        	Map<String, Object> map = getMapFromJSon(configFile);
        	config = new DynamicCombinedConfiguration();
        	((DynamicCombinedConfiguration)config).addConfiguration(setMap(map, "apalala"));
        	logger.debug(config.subset("storage.mysql").toString());
        	logger.debug(config.subset("storage.mysql").getString("host"));
		}
        else if (configurationFile.endsWith(".properties")) {
				config = new PropertiesConfiguration(configFile);
		}
        else if (configurationFile.endsWith(".xml")) {
			config = new XMLConfiguration(configFile);
        }
	}

	private AbstractConfiguration setMap(Map map, String confName)
	{
		AbstractConfiguration aconfig = new DynamicCombinedConfiguration();
    	Set<String> keys = map.keySet();
    	for (String key : keys) 
    	{
    		Object current = map.get(key);
    		if (current instanceof Map) {
        		((DynamicCombinedConfiguration)aconfig).addConfiguration((AbstractConfiguration) setMap((Map) current, key),confName+"."+key,confName);
			} else {
				((DynamicCombinedConfiguration)aconfig).getConfiguration(confName).setProperty(key, current);
			}
		}
		return aconfig;
	
	}
	
    private Map<String, Object> getMapFromJSon(File configFile) {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> mapObject = null;

		try {

			mapObject = mapper.readValue(configFile,
					new TypeReference<Map<String, Object>>() {}
			);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapObject;
    }
    
    public Configuration getConfig(String name) {
    	if (name != null && name.length()>0 ) {
        	return config.subset(name);
		}
    	return config;
    }
}
