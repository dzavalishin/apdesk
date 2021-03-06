package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ru.dz.aprentis.Engine;
import ru.dz.aprentis.ui.AprentisEntityFormWindow;
import ru.dz.vita2d.data.net.IRestCaller;
import ru.dz.vita2d.data.net.RestCaller;
import ru.dz.vita2d.data.net.ServerCache;
import ru.dz.vita2d.data.store.LocalFileStorage;
import ru.dz.vita2d.maps.def.MapList;
import ru.dz.vita2d.media.Player;
import ru.dz.vita2d.ui.LoginFormWindow;


public class Main extends Application 
{

	private Stage primaryStage;

	public IRestCaller rc;
	public ServerCache sc;
	public MapList ml;

	public Properties property = new Properties();

	public LocalFileStorage store = new LocalFileStorage(); 

	@Override
	public void init() throws Exception {
		super.init();

		InputStream propStream  = getClass().getResourceAsStream(Defs.PROPERTIES_URL);
		InputStreamReader psr = new InputStreamReader(propStream,"UTF-8");
		if(propStream != null) property.load(psr);

		rc = new RestCaller(property.getProperty(Defs.PROP_HOST, Defs.HOST_NAME) );
		//rc = new CacheRestCaller( new RestCaller(property.getProperty(Defs.PROP_HOST, Defs.HOST_NAME) ) );
		sc = new ServerCache(rc);

		// Load data from local file with list of maps
		ml = new MapList();

	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		Engine.start("https://app.aprentis.ru");
		try {
			Engine.login("restapi@facex.com","facex");
			
			//new AprentisEntityFormWindow( Engine.get().getUserKey() );
			//new AprentisEntityFormWindow( Engine.get().getProfileKey() );
			//new AprentisEntityFormWindow( Engine.get().getRoleKey() );
			
			//new AprentisEntityFormWindow( Engine.get().getAreaKey() );
			new AprentisEntityFormWindow( Engine.get().getCompanyKey() );
			//new AprentisEntityFormWindow( Engine.get().getUserAKey() );
			//new AprentisEntityFormWindow( Engine.get().getUserCKey() );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( Defs.FULL_SCREEN)
		{
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setMaximized(true);		
			primaryStage.setResizable(false);
		}

		//primaryStage.initStyle(StageStyle.DECORATED);
		logout();

		/*
		try {

			AprentisRestCaller rc = new AprentisRestCaller("https://app.aprentis.ru");

			rc.login("restapi@facex.com","facex");

			AprentisCategory ac3 = rc.loadCategory("c564a1259t4r");
			AprentisEntityListWindow alw = new AprentisEntityListWindow(ac3);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}    

	LoginFormWindow lw;

	public void logout() {
		//LoginScene ls = 
		//new LoginScene(rc, primaryStage, this );
		new BackgroundScene( primaryStage );

		//do {
		lw = new LoginFormWindow(rc, login -> afterLogin() );
		//} while(!lw.isLoggedIn());


	}

	public void afterLogin() {
		// Resets Scene
		lw.close();
		AbstractMapScene ms = new MapScene( primaryStage, this );
		ms.setMapData( ml.getRootMap() );

		if( !Defs.FULL_SCREEN )
			primaryStage.centerOnScreen();

		//primaryStage.setMaximized(true);
		//primaryStage.setMaximized(true);
		Player.bell();
	}




	public static void main(String[] args) {
		launch(args);
	}

	public void requestShutdown() {
		//System.

		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Выключение системы");
			alert.setHeaderText("ОРВД Планшет Инженера");

			String s ="Действитено выключить планшет?";

			alert.setContentText(s);
			Optional<ButtonType> bt = alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				Runtime.getRuntime().exec("shutdown -s -f");
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

		Platform.exit();
	}


}