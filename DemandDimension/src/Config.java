import java.io.OutputStream;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;

public class Config {
	public static void main(String args[]) 
	{
		Properties prop = new Properties();
		OutputStream output=null;

		try {

	 		output = new FileOutputStream("config.properties");
	
	 		// set the properties value
	 		prop.setProperty("timeBackToDepot","0");
	 		prop.setProperty("speed", "40");
	 		prop.setProperty("timeFactor","1.3");
	 		prop.setProperty("horizon","27000");
	 		prop.setProperty("startLocationFlag","true");
	 		prop.setProperty("searchTimeLimit","600000");
	 		prop.setProperty("numberOfVehicles","15");
	 		prop.setProperty("jobDuration","27000");
	 		prop.setProperty("serviceTime","600");
	 		prop.setProperty("numberOfLoadingBay","0");
	 		prop.setProperty("loadingTimeAtBay","0");
	 		prop.setProperty("maxJobsPerVehicle","0");
	 		prop.setProperty("maxValidDistanceBtwnTwoPoints","0");
	 		prop.setProperty("isStartAnywhere","false");
	 		prop.setProperty("synchronusFlag","true");
	 		prop.setProperty("vehicleCost","10000");
	 		prop.setProperty("depot","0");
	 		// save properties to project root folder
	 		prop.store(output, null);

		} 
		catch (IOException io) {
			io.printStackTrace();
		} 
		finally 
		{
	 		if (output != null) 
	 		{
	 			try {
	 				output.close();
	 			} catch (IOException e) {
	 				e.printStackTrace();
	 			}
	 		}

		}
     
	}
}
