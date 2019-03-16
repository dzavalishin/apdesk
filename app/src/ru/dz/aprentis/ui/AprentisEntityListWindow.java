package ru.dz.aprentis.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.vita2d.data.net.HttpCaller;
import ru.dz.vita2d.data.net.IRestCaller;
import ru.dz.vita2d.data.net.ServerCache;
import ru.dz.vita2d.data.type.ServerUnitType;

public class AprentisEntityListWindow extends AbstractFormWindow {

	private AprentisEntityListView view; // TODO make interface for views? Generalize?

	public AprentisEntityListWindow(AprentisCategory ac) 
	{		
		view = new AprentisEntityListView(ac);
		scene = new Scene(view.create());
		makeStage(view.getTitle());
	}
	
	
}
