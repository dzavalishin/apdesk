package ru.dz.vita2d.maps.def;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import application.Defs;
import application.IMapScene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import ru.dz.vita2d.maps.IMapData;

/**
 * List of all maps - loaded from JSON file
 * @author dz
 *
 */
public class MapList 
{
	//List<MapDefinition> mapDefs = new LinkedList<MapDefinition>();
	Map<String,MapDefinition> mapDefs = new HashMap<String,MapDefinition>();
	private String mainMapId;
	private MapDefinition rootMap;


	public MapList() {

		//URL mapsFile = new URL("") 

		//URL mapsFile = getClass().getResource("/maps.json");

		//File file = new File("maps.json");


		try {

			//InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("/maps.json");
			InputStream inputStream  = getClass().getResourceAsStream(Defs.MAP_JSON_URL);

			InputStreamReader rdr = new InputStreamReader(inputStream,"UTF-8");
			JSONTokener loader = new JSONTokener(rdr);
			//JSONTokener loader = new JSONTokener(inputStream);

			//JSONTokener loader = new JSONTokener(new FileInputStream( mapsFile.getFile() ));
			//JSONTokener loader = new JSONTokener(new FileInputStream( file));
			JSONObject top = new JSONObject(loader);

			//RestCaller.dumpJson(top);

			mainMapId = top.getString("main-id");

			JSONArray list = top.getJSONArray("maps");
			list.forEach( map -> loadMap(map) );

		} catch ( Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		for( MapDefinition md : mapDefs.values() )
		{
			md.resolveLinks( this );
		}

		for( MapDefinition md : mapDefs.values() )
		{
			md.generateInternalData();
		}

		rootMap = findMapById(mainMapId);
		if(rootMap == null)
			System.out.println("main-id refers to undefinded map id "+mainMapId);

	}

	public MapDefinition findMapById(String linkId) {
		return mapDefs.get(linkId);
	}

	private Object loadMap(Object omap) {
		if (omap instanceof JSONObject) 
		{
			try {
				JSONObject map = (JSONObject) omap;

				// TODO attrs/type=outdoor/indoor/device
				String name = map.getString("name");
				String id = map.getString("id");
				String file = map.getString("file");

				MapDefinition md = new MapDefinition(id, name, file);

				try {
					if( map.has("tiles") )
					{
						JSONArray tiles = map.getJSONArray("tiles");
						//loadTiles( md, tiles );
						tiles.forEach( tile -> loadTile((JSONObject)tile, md) );
					}
				}
				catch(JSONException je)
				{
					// TODO log
					System.out.println("(ignored) JSON exception loading map tiles: "+je);
				}

				try {
					if( map.has("paths") )
					{
						JSONArray paths = map.getJSONArray("paths");
						paths.forEach( path -> loadPath((JSONObject)path, md) );
					}
				}
				catch(JSONException je)
				{
					// TODO log
					System.out.println("(ignored) JSON exception loading map tiles: "+je);
				}

				mapDefs.put(id,md);
			}
			catch(Throwable e)
			{
				// TODO log
				System.out.println("(ignored) JSON exception loading map: "+omap+"\nException: "+e);
			}
			return null;
		}
		// TODO log
		System.out.println("unknown type "+omap.getClass());
		return null;
	}



	private void loadTile(JSONObject tile, MapDefinition md) 
	{

		MapTileDefinition mtd = new MapTileDefinition(tile);
		md.addTileDefinition( mtd );
	}

	private void loadPath(JSONObject path, MapDefinition md) 
	{

		MapPathDefinition mpd = new MapPathDefinition(path);
		md.addPathDefinition( mpd );
	}

	private void dump() {
		for( MapDefinition md : mapDefs.values() )
		{
			md.dump();			
		}
	}

	public IMapData getRootMap() {
		return rootMap.getMapData();
	}

	public void fillMapsMenu(Menu navMaps, IMapScene mapScene) 
	{
		for( MapDefinition md : mapDefs.values() )
		{
			IMapData mdata = md.getMapData();
			String name = md.getName();

			MenuItem mi = new MenuItem(name);
			mi.setOnAction(actionEvent -> mapScene.setMapData(mdata));

			navMaps.getItems().add(mi);
		}
	}



	public static void main(String[] args) 
	{
		MapList ml = new MapList();
		ml.dump();
	}


}
