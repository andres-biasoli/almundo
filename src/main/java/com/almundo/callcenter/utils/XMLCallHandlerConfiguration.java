package com.almundo.callcenter.utils;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.log4j.Logger;

import com.almundo.callcenter.domain.concurrency.CallHandler;
import com.almundo.callcenter.domain.model.EmployeeTypeEnum;

/**
 * @author Andres Biasoli
 * This class looks for and populates all available call handlers configurated in handlers.xml file. 
 */
public class XMLCallHandlerConfiguration {

	/**
	 * Logger for @{XMLCallHandlerConfiguration} class
	 */
	private final static Logger logger = Logger.getLogger(XMLCallHandlerConfiguration.class);
	private static final String XML_FILE = "handlers.xml";
	
	/**
	 * @param handlers
	 * This method initiates the call handlers list from the XML file.
	 */
	@SuppressWarnings("unchecked")
	public static void populateHandlers(List<CallHandler> handlers){
		try {
	        XMLConfiguration config = new XMLConfiguration(XML_FILE);
	        ConfigurationNode root = config.getRootNode();
	        List<ConfigurationNode> nodes = root.getChildren();
	        for (ConfigurationNode node : nodes) {
	        	String name = node.getAttribute(0).getValue().toString();
	        	String type = node.getAttribute(1).getValue().toString();
	        	EmployeeTypeEnum handlerType = EmployeeTypeEnum.valueOf(type.toUpperCase());
	        	handlers.add(handlerType.get(name));
			}
	    } catch (ConfigurationException e) {
	    	logger.error("There was an error with handlers.xml file");
	    }
	}

}
