/*
 * Pour plus tard, ignorez
 */

package client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class ConfigFile {
	
	private static final String configFilePath = "config.sbcfg";
	private static File configFile;
	
	private final static String emptyConfigFile = "{resolution}{/resolution} \n {soundVolume}{/soundVolume}";
	
	public ConfigFile() {
		configFile = new File(configFilePath);
	}
	
	public static boolean configFileExists() {
		return configFile.isFile();
	}
	
	public static void initConfigFile() {
		try{
			if(!configFileExists())
				return;
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, true));
			writer.write(emptyConfigFile);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static Resolution getConfigResolution() {
		Scanner scanner = new Scanner(configFile).useDelimiter("{resolution}")
	}*/
}
