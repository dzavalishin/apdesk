package ru.dz.aprentis.ui;

import java.io.IOException;

import javafx.scene.Scene;
import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.aprentis.data.AprentisRecordReference;
import ru.dz.vita2d.data.ITypeCache;
import ru.dz.vita2d.data.net.RestCaller;
import ru.dz.vita2d.data.net.ServerCache;
import ru.dz.vita2d.data.ref.IRef;
import ru.dz.vita2d.data.type.ServerUnitType;

public class AprentisEntityFormWindow extends AbstractFormWindow {
	private AprentisEntityFormView view;

	/*public AprentisEntityFormWindow(ServerUnitType type, ITypeCache tc, int id) throws IOException 
	{	
		view = new AprentisEntityFormView(type, tc, id);		
		scene = new Scene(view.create());
		makeStage(view.getTitle());
	}*/

	public AprentisEntityFormWindow(AprentisCategory ac, AprentisRecordReference arr) throws IOException 
	{	
		view = new AprentisEntityFormView(ac, arr);		
		scene = new Scene(view.create());
		makeStage(view.getTitle());
	}


}
