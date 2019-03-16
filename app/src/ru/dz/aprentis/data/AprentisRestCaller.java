package ru.dz.aprentis.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.dz.aprentis.ui.AprentisEntityListWindow;
import ru.dz.vita2d.data.net.HttpCaller;


/**
 * Here we access all the server data.
 * @author dz
 *
 */
public class AprentisRestCaller extends HttpCaller //implements IRestCaller
{

	private String loggedInUser;


	public AprentisRestCaller(String baseUrl) {
		super(baseUrl);

	}


	/* (non-Javadoc)
	 * @see ru.dz.vita2d.data.IRestCaller#login(java.lang.String, java.lang.String)
	 */
	//@Override
	public void login( String login, String password ) throws IOException 
	{
		JSONObject data = postAuth("authenticate/json/v2", login, password );

		loggedInUser = login;

		System.out.println("Logged in\n");

	}




	private JSONObject getData(String key) throws IOException {
		return getJSON(key+"/json/v2");
	}








	/* (non-Javadoc)
	 * @see ru.dz.vita2d.data.IRestCaller#getDataModel(ru.dz.vita2d.data.ServerUnitType)
	 * /
	@Override
	public JSONObject getDataModel(IEntityType unitType) throws IOException
	{
		return getDataModel(unitType.getPluralTypeName());
		//return getDataModel(unitType.toString());
	}

	// TODO -list version

	/* (non-Javadoc)
	 * @see ru.dz.vita2d.data.IRestCaller#getDataModel(java.lang.String)
	 * /
	@Override
	public JSONObject getDataModel(String entityName) throws IOException {
		String data = getString( String.format( "resources/models/%s-form.js", entityName ) );
		if( data == null)
			return null;
		//jsEval(data);

		// This page gives out not a clean JSON but JavaScript assignment 
		//data = data.replaceAll("^\\$v\\.models\\[\\'means-form\\'\\]=", "" );

		int eqpos = data.indexOf("=");
		if( eqpos < 0)
		{
			System.out.println("no '=' sign in means data model "+data); // TODO logging
			return null;
		}

		data = data.substring(eqpos+1); // skip all up to and incl '=' sign
		JSONObject out = null;
		try {
			out = new JSONObject(data);
		} catch(JSONException e)
		{
			System.out.println("wrong data model "+data); // TODO logging
			throw e;
		}
		return out;
	}

	/*
	private ScriptEngineManager engineManager = new ScriptEngineManager();

	private String jsEval(String jsCode)
	{
		ScriptEngine engine = engineManager.getEngineByName("nashorn");

		try {
			System.out.println(jsCode);

			String ret = "";

			engine.put("ret", ret);


			engine.eval("var $v; $v = new Object(); $v.models = new Object();");
			engine.eval(jsCode+";");
			engine.eval("ret = \"\" + $v.models['means-form'] ;");
			//String ret = (String) engine.getContext().getAttribute("$v.models['means-form']");

			ret = (String) engine.get("ret");

			System.out.println(ret);
			return ret;
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	 * /	





	private final Object serverMutex = new Object();
	private String serverVersion = null;

	/* (non-Javadoc)
	 * @see ru.dz.vita2d.data.IRestCaller#getServerVersion()
	 * /
	@Override
	public String getServerVersion() {
		synchronized (serverMutex) {
			if( serverVersion == null )
				loadServerVersion();
			if( serverVersion == null )
				serverVersion = "?.?";
			return serverVersion;
		}
	}


	private void loadServerVersion() {
		try {
			String indexPage = getString("index/");

			int pos = indexPage.indexOf("Версия:");
			if(pos < 0) return;

			pos += 7; // skip word

			String rest = indexPage.substring(pos);

			int endPos = rest.indexOf("<");
			if(endPos <= 0) return;
			if(endPos > 10) endPos = 10;

			serverVersion = rest.substring(0, endPos).trim();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see ru.dz.vita2d.data.IRestCaller#getLoggedInUser()
	 * /
	@Override
	public String getLoggedInUser() {
		return loggedInUser;
	} /* */




	static public void dumpJson( JSONObject jo )
	{
		for( String key : jo.keySet() )
		{
			System.out.println(key);
		}
	}


	/* (non-Javadoc)
	 * @see ru.dz.vita2d.data.IRestCaller#getServerURL()
	 */
	//@Override
	public String getServerURL() {
		return baseUrl;
	}



	public static void saveToFile( String name, String data )
	{
		try {
			FileOutputStream os = new FileOutputStream("c:/tmp/"+name);
			os.write(data.getBytes());
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


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
			//rc.getIcon("248");

			//JSONObject emp_dm = rc.getDataModel(EntityType.EMPLOYEES);
			//saveToFile( "employees_model", emp_dm.toString() );


			//rc.getServerVersion();

			//JSONObject filesList = rc.loadUnitList(ServerUnitType.DOCUMENTS);
			//dumpJson(objList);
			//System.out.println("List = "+objList.toString());
			//saveToFile( "files_list", filesList.toString() );

			JSONObject jo = rc.getData("c564a1259t2r");
			dumpJson(jo);

			//AprentisRecord ar = new AprentisRecord(jo);
			AprentisCategory ac = new AprentisCategory();
			ac.loadData(jo);
			
			AprentisCategory ac1 = rc.loadCategory("c564a1259t2r");

			//AprentisCategory ac2 = rc.loadCategory("c564a1259t1r");

			AprentisCategory ac3 = rc.loadCategory("c564a1259t4r");

			//AprentisEntityListWindow alw = new AprentisEntityListWindow(ac3);

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

		//AprentisRecord ar = new AprentisRecord(jo);
		AprentisCategory ac = new AprentisCategory();
		ac.loadData(jo);
		return ac;
	}



}
