package ru.dz.aprentis.data;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;

import ru.dz.aprentis.data.ref.AprentisCategoryReference;
import ru.dz.vita2d.data.net.HttpCaller;


/**
 * Here we access all the server data.
 * @author dz
 *
 */
public class AprentisRestCaller extends HttpCaller //implements IRestCaller
{
	//private String loggedInUser;

	public AprentisRestCaller(String baseUrl) {
		super(baseUrl);
	}


	public JSONObject login( String login, String password ) throws IOException 
	{
		JSONObject data = postAuth("authenticate/json/v2", login, password );

		//loggedInUser = login;
		//System.out.println("Logged in: "+data);
		return data;
	}

	// TODO add start and size parameters to be able to read in chunks

	/**
	 * Get table data
	 * @param key table key
	 * @return JSON
	 * @throws IOException
	 */
	 
	private JSONObject getData(String key) throws IOException {
		return getJSON(key+"/json/v2");
	}


	/**
	 * Get table data with metadata. Attempt to get minimal possible data size (1 record?).
	 * @param key table key
	 * @return json with metadata and data
	 * @throws IOException if disconnect
	 */
	private JSONObject getMetaData(String key) throws IOException {
		return getJSONWithMeta(key+"/json/v2");
	}

	













	static public void dumpJson( JSONObject jo )
	{
		for( String key : jo.keySet() )
		{
			System.out.println(key);
			//System.out.println("\t"+jo.getJSONObject(key));
		}
	}


	public String getServerURL() {
		return baseUrl;
	}


	/*
	public static void saveToFile( String name, String data )
	{
		try {
			FileOutputStream os = new FileOutputStream("c:/tmp/"+name);
			os.write(data.getBytes());
			os.close();
		} catch (IOException e) {
			// TO DO Auto-generated catch block
			e.printStackTrace();
		}
	}*/


	/*
	public static String makeFileUrl(JSONObject fileInfo)
	{
		// /rest/<files[0].linkEntity>/files/<files[0].id>/<files[0].uri>

		String entity = fileInfo.getString("linkEntity");
		int id = fileInfo.getInt("id");
		String uri = fileInfo.getString("uri");

		return String.format("/rest/%s/files/%d/%s", entity, id, URLEncoder.encode(uri) );		
	}*/



	public static void main(String[] args) 
	{	
		//RestCaller rc = new RestCaller("http://sv-web-15.vtsft.ru/orvd-test");
		AprentisRestCaller rc = new AprentisRestCaller("https://app.aprentis.ru");

		System.out.println("Start\n");

		try {
			rc.login("restapi@facex.com","facex");

			//JSONObject jo = rc.getData("c564a1259t2r");
			//dumpJson(jo);
			//AprentisRecord ar = new AprentisRecord(jo);
			
			//AprentisCategory ac = new AprentisCategory();
			//ac.loadData(jo);
			
			//AprentisCategory ac1 = rc.loadCategory("c564a1259t2r");

			//AprentisCategory ac2 = rc.loadCategory("c564a1259t1r");

			AprentisCategory ac3 = rc.loadCategory("c564a1259t4r");

			//AprentisEntityListWindow alw = new AprentisEntityListWindow(ac3);
			//new AprentisEntityListWindow(ac3);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}		

		System.out.println("Done\n");

	}


	public AprentisCategory loadCategory(String categoryKey) throws IOException {
		JSONObject jo = getData(categoryKey);
		//dumpJson(jo);
		System.out.println(jo);

		//AprentisRecord ar = new AprentisRecord(jo);
		AprentisCategory ac = new AprentisCategory();
		ac.loadData(jo);
		
		JSONObject mjo = getMetaData(categoryKey);
		ac.loadMetaData(mjo);
		
		return ac;
	}


	public AprentisCategory loadCategory(AprentisCategoryReference ref) throws IOException 
	{		
		return loadCategory(ref.getAsString());
	}



}
