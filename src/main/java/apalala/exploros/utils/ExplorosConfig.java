package apalala.exploros.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


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
		    File xmlFile = new File(configFile.getParentFile(),configFile.getName()+".xml");
        	createXMLFromJSon(configFile, xmlFile);
			config = new XMLConfiguration(xmlFile);
        	
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

	
    private void createXMLFromJSon(File configFile, File xmlFile) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> mapObject = null;

		try {

			mapObject = mapper.readValue(configFile,
					new TypeReference<Map<String, Object>>() {}
			);
			XmlMapper xmlMapper = new XmlMapper();
		    xmlMapper.writeValue(xmlFile, mapObject);
		} catch (Exception e) {
   			throw new IOException(e);
		}
    }
    
    public Configuration getConfig(String name) {
    	if (name != null && name.length()>0 ) {
        	return config.subset(name);
		}
    	return config;
    }
}
