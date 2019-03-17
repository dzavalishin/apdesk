package ru.dz.aprentis.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.aprentis.data.AprentisRecord;
import ru.dz.aprentis.data.AprentisRecordReference;
import ru.dz.aprentis.data.type.AprentisReference;

/**
 * </p>Display list entity fields.</p>
 * 
 * @author dz
 *
 */

public class AprentisEntityFormView 
{

	private Label shortNameLabel = new Label("");

	private String title = "";
	private AprentisCategory ac;
	private AprentisRecordReference arr;

	private AprentisRecord record;


	public AprentisEntityFormView(AprentisCategory ac, AprentisRecordReference arr) throws IOException 
	{
		super();
		this.ac = ac;
		this.arr = arr;

		record = ac.getRecord( arr );

		//this.type = ref.getType();
		//this.tc = ref.getPerTypeCache(sc);
		//this.entityId = ref.getId();

	}







	public Pane create()	
	{
		shortNameLabel.setFont(new Font("Arial", 20));
		shortNameLabel.setTooltip(new Tooltip("id: "+arr.getAsString()) );

		TableView<Map<String,String>> table = new TableView<>(generateDataInMap());

		table.setEditable(true);
		table.getSelectionModel().setCellSelectionEnabled(true);
		table.setMinWidth(470);

		TableColumn<Map<String,String>, String> col1 = new TableColumn<>("Поле");
		col1.setCellValueFactory(new MapValueFactory("fn"));
		table.getColumns().add(col1);

		TableColumn<Map<String,String>, String> col2 = new TableColumn<>("Значение");
		col2.setCellValueFactory(new MapValueFactory("fv"));
		table.getColumns().add(col2);

		TableColumn<Map<String,String>, String> col3 = new TableColumn<>("Ссылка");
		col3.setCellValueFactory(new MapValueFactory("li"));
		table.getColumns().add(col3);

		setOnClick(table);


		final VBox vbox = new VBox();

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(shortNameLabel, table);

		return vbox;
	}


	private void setOnClick(TableView<Map<String, String>> table) {
		table.setRowFactory( tv -> {
			TableRow<Map<String,String>> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					onDoubleClick(row);
				}
			});
			return row ;
		});
	}

	// TODO implement
	private void onDoubleClick(TableRow<Map<String, String>> row) 
	{

		Map<String, String> rowData = row.getItem();

		if(rowData == null)
			return;

		String ref = rowData.get("ref");
		if(ref == null)
			return;

		// TODO open view
		System.out.println("go to "+ref);
		/*
		IRef iref = IRef.deserialize(ref);
		try {
			//IEntityDataSource instance = iref.instantiate(tc.getServerCache());
			//System.out.println(instance.getJson());

			new EntityFormWindow(iref, tc.getServerCache());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}


	private ObservableList<Map<String,String>> generateDataInMap() {

		ObservableList<Map<String,String>> allData = FXCollections.observableArrayList();

		/*
		for( String key : jo.keySet() )
		{
			Object object = jo.get(key);

			if( "shortName".equalsIgnoreCase(key))
			{
				//dialog.setTitle("Средство '"+object+"'");
				title = type.getDisplayName()+": "+object.toString();
				shortNameLabel.setText(title);
			}

			DataConvertor.parseAnything(key, object, (fName,fieldVal) -> {
				String fieldName = tc.getFieldName(key);
				String fieldType = tc.getFieldType(fName);
				ModelFieldDefinition fieldModel = tc.getFieldModel(fName);
				if( fieldName != null )
				{
					String val = DataConvertor.readableValue( fieldType, fieldVal ); 

					Map<String, String> dataRow = new HashMap<>();

					dataRow.put("fn", fieldName );
					dataRow.put("fv", val );

					processReference(object, fieldModel, dataRow);

					allData.add(dataRow);
				}
			});

		}	    
		 */

		record.forEachField( rf -> {
			Map<String, String> dataRow = new HashMap<>();

			dataRow.put("fn", rf.getName() );
			dataRow.put("fv", rf.getValue().toString() );

			//processReference(object, fieldModel, dataRow);

			allData.add(dataRow);

			if (rf.getValue() instanceof AprentisReference) {
				AprentisReference rv = (AprentisReference) rf.getValue();

				dataRow.put("ref", rv.getReference() );
				dataRow.put("li", "ссылка" );
			}
			else
				dataRow.put("li", "" );


		});

		String title = record.getTtile();
		shortNameLabel.setText(title);

		return allData;
	}

	/*
	private void processReference(Object jsonSrc, ModelFieldDefinition fieldModel, Map<String, String> dataRow) {
		if( (fieldModel != null) && fieldModel.isReference())
		{
			String entity = fieldModel.getEntity();
			int id = -1;
			if (jsonSrc instanceof JSONObject) {
				JSONObject j = (JSONObject) jsonSrc;

				if( j.has("id") )
					id = j.getInt("id");
			}

			//System.out.println("ref ent="+entity+" id="+id);
			IRef ref = new EntityRef( entity, id );

			dataRow.put("ref", ref.serialize() );
			dataRow.put("li", "ссылка" );
		}
		else
			dataRow.put("li", "" );
	}
	 */

	public String getTitle() {
		return title;
	}


}
