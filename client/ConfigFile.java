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
	/**
	 * Vérifie que le fichier de configuration existe
	 * @author Amine Boudraa
	 */
	public static boolean configFileExists() {
		return configFile.isFile();
	}
	
	/**
	 * Initialise le fichier de configuration
	 * @author Amine Boudraa
	 */
	public static void initConfigFile() {
		try{
			if(!configFileExists())
				return;
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, true));
			writer.write(emptyConfigFile);
			writer.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static Resolution getConfigResolution() {
		Scanner scanner = new Scanner(configFile).useDelimiter("{resolution}")
	}*/
}
