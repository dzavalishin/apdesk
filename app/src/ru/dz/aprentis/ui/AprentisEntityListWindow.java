package ru.dz.aprentis.ui;

import javafx.scene.Scene;
import ru.dz.aprentis.data.AprentisCategory;

public class AprentisEntityListWindow extends AbstractFormWindow {

	private AprentisEntityListView view; // TODO make interface for views? Generalize?

	public AprentisEntityListWindow(AprentisCategory ac) 
	{		
		view = new AprentisEntityListView(ac);
		scene = new Scene(view.create());
		makeStage(view.getTitle());
	}
	
	
}
