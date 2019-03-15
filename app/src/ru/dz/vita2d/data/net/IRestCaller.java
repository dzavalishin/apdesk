package ru.dz.vita2d.data.net;

import java.io.IOException;

import org.json.JSONObject;

import ru.dz.vita2d.data.ref.IRef;
import ru.dz.vita2d.data.ref.UnitRef;
import ru.dz.vita2d.data.type.IEntityType;
import ru.dz.vita2d.data.type.ServerUnitType;

public interface IRestCaller {

	/**
	 * Logs us in. Must be called first.
	 * @param login
	 * @param password
	 * @throws IOException
	 */
	void login(String login, String password) throws IOException;

	/**
	 * Get list of objects of given type. 
	 * @param type See ServerUnitType constants for types.
	 * @return JSON with data
	 * @throws IOException
	 */

	JSONObject loadUnitList(ServerUnitType type) throws IOException;

	/** God knows what differs units from other entities */
	JSONObject loadOtherList(String entityType) throws IOException;

	/** God knows what differs units from other entities */
	JSONObject loadDictList(String entityType) throws IOException;

	/**
	 * <p>Get object of given type.<p> 
	 * @param type entity type?
	 * @param id object id
	 * @return JSON with data
	 * @throws IOException
	 */

	JSONObject getDataRecord(IRef ref) throws IOException;

	/**
	 * <p>Get object of given type.<p> 
	 * @param type See ServerUnitType constants for types.
	 * @param id object id
	 * @return JSON with data
	 * @throws IOException
	 */

	JSONObject getDataRecord(ServerUnitType type, int id) throws IOException;

	/**
	 * <p>Get object of given type.<p> 
	 * @param ref object reference (type+id)
	 * @return JSON with data
	 * @throws IOException
	 */

	JSONObject getDataRecord(UnitRef ref) throws IOException;

	/**
	 * Get data model (field names, types, etc) for given type.
	 * 
	 * TODO list/form kinds
	 * 
	 * @param unitType See ServerUnitType/EntityType constants for types.
	 * @return JSON with model
	 * @throws IOException
	 */
	public JSONObject getDataModel(IEntityType unitType) throws IOException;
	//JSONObject getDataModel(ServerUnitType unitType) throws IOException;

	JSONObject getDataModel(String entityName) throws IOException;

	/**
	 * <p>Get server version by parsing web site index page.</p>
	 * <p>TODO: make REST API call for that!</p>
	 * @return Server version string.
	 */
	String getServerVersion();

	String getLoggedInUser();

	String getServerURL();

	/**
	 * Download server file to local FS and return its full path name.
	 * 
	 * @param filePath Local file path - last part of
	 * @param fileUrl Server file URL suffix
	 * @param server file's modification time
	 * @return Full local file name
	 * @throws IOException 
	 */
	void downloadFile(String filePath, String fileUrl, long modified) throws IOException;

}