package ru.dz.aprentis.ui;

import java.io.IOException;

import javafx.scene.Scene;
import ru.dz.aprentis.Engine;
import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.aprentis.data.AprentisCategoryReference;
import ru.dz.aprentis.data.AprentisRecordReference;

public class AprentisEntityFormWindow extends AbstractFormWindow 
{
	private AprentisEntityFormView view;

	public AprentisEntityFormWindow(AprentisCategory ac, AprentisRecordReference arr) throws IOException 
	{	
		view = new AprentisEntityFormView(ac, arr);		
		scene = new Scene(view.create());
		makeStage(view.getTitle());
	}

	public AprentisEntityFormWindow(AprentisRecordReference arr) throws IOException 
	{	
		AprentisCategoryReference cr = arr.getCategoryReference();
		AprentisCategory ac = Engine.getCategory(cr);
		
		view = new AprentisEntityFormView(ac, arr);		
		scene = new Scene(view.create());
		makeStage(view.getTitle());
	}


}
