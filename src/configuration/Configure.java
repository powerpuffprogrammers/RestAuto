package configuration;

import java.io.BufferedReader;
import java.io.FileReader;

public class Configure {

	private static final String configFile = "../configuration/portNumbers.txt";
	
	/**
	 * Reads the port number for the given server in the config file
	 * @param serverName = name of server (databaseacontroller) this is not case sensitive
	 * @returns the port number or -1 on error
	 */
	public static int getPortNumber(String serverName){
		
		try (BufferedReader br = new BufferedReader(new FileReader(configFile))){

			String currLine;

			while ((currLine = br.readLine()) != null) {
				
				String[] arr = currLine.split("=");
				if(arr[0]==serverName.toLowerCase()){
					return Integer.parseInt(arr[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return -1;
	}
		
}
