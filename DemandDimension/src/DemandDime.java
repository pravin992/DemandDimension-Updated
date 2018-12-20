/* https://www.mkyong.com/java/java-properties-file-examples/ */

import java.util.*;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class DemandDime {

    public static void main(String[] args) {

    	BufferedReader br = null;
    	
        String csvFile = "12thNovDataForSimulation.csv";
        String line = "";
        String cvsSplitBy = ",";
        
        List<Double[]> locationArr = new ArrayList<Double[]>(); 	//lat_lang
        List<String> jobID = new ArrayList<String>();				//Row_label
        List<Integer> weight = new ArrayList<Integer>();			//Sum_of_net
        List<Integer> lines = new ArrayList<Integer>();				//Count of SKU7_CODE
        List<Integer> dimensionDemand = new ArrayList<Integer>();
        List<Integer> bills = new ArrayList<Integer>();
        List<Integer> DetDF = new ArrayList<Integer>();
        List<Integer> FNB = new ArrayList<Integer>();
        List<Integer> HUL = new ArrayList<Integer>();
        List<Integer> PP = new ArrayList<Integer>();

        //Read csv file and Initialize respective list
         try {
        		br = new BufferedReader(new FileReader(csvFile));
        		br.readLine();
        		
	            while ((line = br.readLine()) != null) 
	            {
	            	// use comma as separator
	                String[] latlang = line.split(cvsSplitBy);
	                
	                //Add Data into respective variable
	                jobID.add(new String(latlang[1]));
	                lines.add(new Integer(latlang[2]));
	                
	                weight.add(new Integer(Math.round(Float.parseFloat(latlang[3]))));
	                locationArr.add(new Double[] {Double.valueOf(latlang[5]),Double.valueOf(latlang[6])});
	                DetDF.add(new Integer(Integer.parseInt(latlang[8])));
	                FNB.add(new Integer(Integer.parseInt(latlang[9])));
	                HUL.add(new Integer(Integer.parseInt(latlang[10])));
	                PP.add(new Integer(Integer.parseInt(latlang[11])));
	                bills.add(new Integer(Integer.parseInt(latlang[12])));
	             }
         	} 
         	catch (FileNotFoundException e) {
         		e.printStackTrace();
         	} 
         	catch (IOException e) {
         		e.printStackTrace();
         	} 
         	finally 
         	{
         		if (br != null) {
         			try {
         				br.close();
         			} catch (IOException e) {
         				e.printStackTrace();
         			}
            }
        }
        
        // Load Property File 
        
        Properties prop = new Properties();
      	InputStream input = null;
 		
      	try {
      		input = new FileInputStream("config.properties");

     		// load a properties file
     		prop.load(input);
      	} 
      	catch (IOException ex) {
     		ex.printStackTrace();
     	}

     // Insert into JSON File
      	
 	 JSONObject obj = new JSONObject();				//root JSON Object
     JSONObject input_json = new JSONObject();		//First object 
     
     JSONArray typeOfJob = new JSONArray();
     input_json.put("typeOfJob",typeOfJob);
     
     JSONArray typeOfVehicles = new JSONArray();
     input_json.put("typeOfVehicles",typeOfVehicles);
     
     JSONArray jobsBySameRider = new JSONArray();
     input_json.put("jobsBySameRider",jobsBySameRider);
     
     JSONArray priority = new JSONArray();
     input_json.put("priority",priority);
     
     JSONArray startLocations = new JSONArray();
     input_json.put("startLocations",startLocations);
     
     JSONArray endLocations = new JSONArray();
     input_json.put("endLocations",endLocations);
     
     JSONArray partialFixedPlan = new JSONArray();
     input_json.put("partialFixedPlan",partialFixedPlan);
     
     JSONArray demandDimensions = new JSONArray(); // Demand Dimensions
     
     JSONObject ll1 = new JSONObject(); // demandDimensions : lines
     ll1.put("dimensionName","lines");
     demandDimensions.add(ll1);
     ll1.put("dimensionDemand",lines);
     input_json.put("demandDimensions",demandDimensions);
     
     JSONObject ll2 = new JSONObject(); // demandDimensions : weight
     ll2.put("dimensionName","weight");
     demandDimensions.add(ll2);
     ll2.put("dimensionDemand",weight);
     
     JSONObject ll3 = new JSONObject(); // demandDimensions :bills
     ll3.put("dimensionName","bills");
     demandDimensions.add(ll3);
     ll3.put("dimensionDemand",bills);
     
     JSONObject ll4 = new JSONObject(); // demandDimensions :Det D+F 
     ll4.put("dimensionName","Det and D+F");
     demandDimensions.add(ll4);
     ll4.put("dimensionDemand",DetDF);
     
     JSONObject ll5 = new JSONObject(); //demandDimensions :HUL
     ll5.put("dimensionName","HUL");
     demandDimensions.add(ll5);
     ll5.put("dimensionDemand",HUL);
     
     JSONObject ll6 = new JSONObject(); // demandDimensions :PP 
     ll6.put("dimensionName","PP");
     demandDimensions.add(ll6);
     ll6.put("dimensionDemand",PP);
     
     JSONObject ll7 = new JSONObject(); // demandDimensions :PP 
     ll7.put("dimensionName","FNB");
     demandDimensions.add(ll7);
     ll7.put("dimensionDemand",FNB);
          
     JSONArray depot = new JSONArray(); // demandDimensions :depot[]
     depot.add(Integer.parseInt(prop.getProperty("depot")));
     input_json.put("depot",depot);
     
     JSONArray vehicleCost = new JSONArray(); // demandDimensions :vehicleCost[]
     vehicleCost.add(Integer.parseInt(prop.getProperty("vehicleCost")));
     input_json.put("vehicleCost",vehicleCost);
     
     
     JSONArray location = new JSONArray();
     for( int i = 0; i < locationArr.size(); i++ ) 
     {
    	 JSONArray innerArr = new JSONArray();
         for( int j = 0; j < locationArr.get(i).length; j++ ) 
         {
        	 innerArr.add(locationArr.get(i)[j]); 
         }
         location.add(innerArr);
     }
     input_json.put("locations",location);
     input_json.put("demandDimensions",demandDimensions);

 	 input_json.put("timeBackToDepot",Integer.parseInt(prop.getProperty("timeBackToDepot")));
     input_json.put("speed",Integer.parseInt(prop.getProperty("speed")));
     input_json.put("timeFactor",Float.parseFloat(prop.getProperty("timeFactor")));
     input_json.put("horizon",Integer.parseInt(prop.getProperty("horizon")));
     input_json.put("startLocationFlag",Boolean.parseBoolean(prop.getProperty("startLocationFlag")));
     input_json.put("searchTimeLimit",Integer.parseInt(prop.getProperty("searchTimeLimit")));
     input_json.put("numberOfVehicles",Integer.parseInt(prop.getProperty("numberOfVehicles")));
     input_json.put("jobDuration",Integer.parseInt(prop.getProperty("jobDuration")));
     input_json.put("serviceTime",Integer.parseInt(prop.getProperty("serviceTime")));
     input_json.put("numberOfLoadingBay",Integer.parseInt(prop.getProperty("numberOfLoadingBay")));
     input_json.put("loadingTimeAtBay",Integer.parseInt(prop.getProperty("loadingTimeAtBay")));
     input_json.put("maxJobsPerVehicle",Integer.parseInt(prop.getProperty("maxJobsPerVehicle")));
     input_json.put("maxValidDistanceBtwnTwoPoints",Integer.parseInt(prop.getProperty("maxValidDistanceBtwnTwoPoints")));
     input_json.put("isStartAnywhere",Boolean.parseBoolean(prop.getProperty("isStartAnywhere")));
     input_json.put("synchronusFlag",Boolean.parseBoolean(prop.getProperty("synchronusFlag")));
     input_json.put("jobIds",jobID);
     
     obj.put("inputs",input_json);
     
     try (FileWriter file = new FileWriter("f:\\test.json")) {

         file.write(obj.toJSONString());
         file.flush();

     } catch (IOException e) {
         e.printStackTrace();
     }

     System.out.print(obj);

    }

}