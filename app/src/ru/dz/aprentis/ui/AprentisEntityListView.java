package ru.dz.aprentis.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ru.dz.aprentis.data.AprentisCategory;
import ru.dz.aprentis.data.AprentisRecord;
import ru.dz.aprentis.data.ref.AprentisRecordReference;
import ru.dz.vita2d.data.filter.FilterSet;
/**
 * </p>Display list of entities.</p>
 * 
 * @author dz
 *
 */
public class AprentisEntityListView 
{
	private AprentisCategory ac;

	//private ServerUnitType type;
	//private RestCaller rc;
	//private ITypeCache tc;

	private Set<String> fieldNames = new HashSet<>();
	private String title = "";
	private FilterSet fs = new FilterSet();

	private ObservableList<Map> allData = FXCollections.observableArrayList();
	private TableView<Map> table;


	//private JSONObject objList;

	public AprentisEntityListView(AprentisCategory ac) 
	{
		super();
		this.ac = ac;

		title = "Список: "+ac.getVisibleNamePlural();
	}



	public String getTitle() {		return title; }



	public Pane create()	
	{
		generateDataInMap(allData);
		table = new TableView<>(allData);

		table.setEditable(true);
		table.getSelectionModel().setCellSelectionEnabled(true);
		table.setMinWidth(600);

		placeFirst(table, "ref");
		//placeFirst(table, "shortName");
		//placeFirst(table, "unitName"); // no shortName in 

		fieldNames.forEach(fName -> {
			addColumn(table, fName);
		});

		table.setRowFactory( tv -> {
			TableRow<Map> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					Map<String,String> rowData = row.getItem();
					//System.out.println(rowData);
					String sid = rowData.get("ref");
					if( sid == null )
					{
						// TODO log error
						return;
					}


					try {
						AprentisRecordReference ref = new AprentisRecordReference(sid);
						// TODO fix back
						AprentisEntityFormWindow fw = new AprentisEntityFormWindow( ac, ref );

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			
			return row;
		});


		final VBox vbox = new VBox();

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(getMenu(), /*label,*/ table);

		return vbox;
	}





	private void placeFirst(TableView<Map> table, String exception) {
		if(fieldNames.contains(exception))
		{
			fieldNames.remove(exception);
			addColumn(table, exception);
		}
	}



	private void addColumn(TableView<Map> table, String fName) {
		// TODO fix me
		//String readableName = tc.getFieldName(fName);
		String readableName = fName;
		
		// TODO hack
		if( readableName.startsWith("custom_") )
			readableName = readableName.substring( 7 );
		
		if( readableName == null )
			return;

		TableColumn<Map, String> col = new TableColumn<>(readableName);
		col.setCellValueFactory(new MapValueFactory(fName));

		table.getColumns().add(col);
	}

	//private ObservableList<Map> generateDataInMap(ObservableList<Map> allData) 
	private void generateDataInMap(ObservableList<Map> allData) 
	{
		if(ac == null)
			return;

		ac.forEachRecord(apRec -> {
			transferEntity( allData, apRec );
		});

	}



	private void transferEntity(ObservableList<Map> allData, AprentisRecord ar) {
		Map<String, String> dataRow = new HashMap<>();

		BoolPtr ok = new BoolPtr();
		loadEntity(ar, dataRow, ok);

		if( ok.ok && ok.search ) //filter(dataRow ) )					
			allData.add(dataRow);
	}







	private void loadEntity(AprentisRecord ar, Map<String, String> dataRow, BoolPtr ok ) {

		dataRow.put("ref", ar.getKey()); // used by doubleclick to open form

		ar.forEachField( af -> {
			
			String fieldName = af.getName();
			String readableValue = af.toString();
			
			//String fieldType = tc.getFieldType(rawName);
			filter(ok, fieldName, readableValue);
			dataRow.put(fieldName, readableValue); 
			fieldNames.add(fieldName); 
			// update set of possible values in per type cache
			//tc.updateFieldValuesStats(fieldName, readableValue);

		});

		
		/*
		odata.keySet().forEach(fName -> 
		{ 
			Object data = odata.get(fName);

			DataConvertor.parseAnything(fName, data, (fieldName,fieldVal) -> {
				String fieldType = tc.getFieldType(fName);
				String readableValue = DataConvertor.readableValue( fieldType, fieldVal );

				filter(ok, fName, readableValue);

				dataRow.put(fieldName, readableValue); 
				fieldNames.add(fieldName); 
				// update set of possible values in per type cache
				tc.updateFieldValuesStats(fieldName, readableValue);
			});


		} );
		 */
	}





	private void filter(BoolPtr ok, String fieldId, String readableValue) 
	{

		boolean filter = fs.filter( fieldId, readableValue );
		//System.out.println(fieldId+"="+readableValue+" = "+(filter?"1":"0") );
		if( !filter ) ok.ok = false;

		if( fs.checkSearchFilter(readableValue) ) 
			ok.search = true;

	}


	private class BoolPtr 
	{ 
		public boolean search = false; 
		boolean ok = true; 
	} 

	private Node getMenu() {
		/*
		MenuBar mb = new MenuBar();

		Menu filterMenu = new Menu("Фильтр");

		MenuItem d1 = new MenuItem("Фильтровать");
		d1.setOnAction(actionEvent -> showFilter() );		
		filterMenu.getItems().add(d1);

		//filterMenu.setOnAction(actionEvent -> showFilter() );

		mb.getMenus().addAll(filterMenu );
		 */
		Button clearFlterButton = new Button("X");
		clearFlterButton.setVisible(false);
		clearFlterButton.setOnAction(a -> clearFilters(clearFlterButton) );
		clearFlterButton.setTooltip(new Tooltip("Очистить фильтры"));

		Button filterButton = new Button("Фильтр");
		filterButton.setOnAction( a -> showFilter(clearFlterButton) );

		//ImageView clearIv = new ImageView(new Image("icons/color/close.png"));
		//Button clearButton = new Button("",clearIv);
		Button clearButton = new Button("X");
		clearButton.setVisible(false); // search field is empty bu default
		clearButton.setTooltip(new Tooltip("Очистить поисковый запрос"));


		TextField searchField = new TextField();
		//searchField.setOnAction(action -> searchModified(searchField.getText()));
		Tooltip tip = new Tooltip("Поиск по любому полю");
		searchField.setTooltip(tip);
		searchField.setPromptText("Поиск");
		searchField.setFocusTraversable(false); // or else it gets focus and does not show prompt text
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			searchModified(newValue,clearButton);
		});

		clearButton.setOnAction(a -> { searchField.setText(""); } );		

		/*
		//searchField.setMinHeight(clearButton.getHeight());
		//clearButton.setMaxHeight(searchField.getHeight());
		clearIv.setPreserveRatio(true);
		//clearIv.setScaleY(value);
		clearIv.setFitWidth(searchField.getHeight());
		clearIv.setFitHeight(searchField.getHeight());
		clearButton.setGraphic(clearIv);
		 */

		// Auto-sizing spacer
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		//HBox mhbox = new HBox( mb, spacer, new Label("Поиск: "), searchField );
		//HBox mhbox = new HBox( mb, spacer, searchField, clearButton );
		HBox mhbox = new HBox( filterButton, clearFlterButton, spacer, searchField, clearButton );
		//mhbox.setPadding(new Insets(10));
		mhbox.setPadding(new Insets(0,10,0,0)); // 10 px on right side


		return mhbox; //mb;
	}





	private void searchModified(String text, Button clearButton) {
		//System.out.println("search: "+text);
		fs.setSearchFilter(text);
		clearButton.setVisible(fs.isSearchActive());
		updateListData();
	}





	private void showFilter(Button clearFlterButton) {

		//fieldNames.forEach(fn -> {	fieldFilter(fn); } );
		//fs.clear();
		
		/* TODO redo
		FilterDialog fd = new FilterDialog(tc);
		fd.show( fs );
		*/
		
		clearFlterButton.setVisible(fs.isFilterActive());

		//table.refresh();
		//table = new TableView<>(generateDataInMap());
		//table.data
		updateListData();
	}

	private void clearFilters(Button clearFlterButton) {
		fs.clear(); 
		clearFlterButton.setVisible(false); 
		updateListData();
	}




	private void updateListData() {
		allData.clear();
		//table.getda
		generateDataInMap(allData);
		table.refresh();
	}


}
