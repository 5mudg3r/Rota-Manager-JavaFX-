package me.smudja;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RotaManager extends Application {
	
	static BorderPane rootNode;
	static GridPane gridNode;
	static Stage primaryStage;
	private static MenuHandler MEHandler;
	static ButtonHandler btnHandler;
	private DataManager dataManager;
	private ShiftManager shiftManager;
	
	@Override
	public void init() {
		dataManager = DataManager.INSTANCE;
		shiftManager = ShiftManager.INSTANCE;
		MEHandler = new MenuHandler();
		btnHandler = new ButtonHandler();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		RotaManager.primaryStage = primaryStage;
		
		primaryStage.setTitle("Rota Manager v1.0a");
		
		rootNode = new BorderPane();
		
		primaryStage.setScene(new Scene(rootNode, 1200, 600));		
		
		rootNode.setTop(createMenuBar());
		rootNode.setCenter(createGridPane());
		
		primaryStage.show();		
	}

	public static Node createGridPane() {
		
		GridPane gridNode = new GridPane();
		
		gridNode.setVgap(10);
		gridNode.setHgap(10);
		gridNode.setPadding(new Insets(0, 10, 0, 10));
		
		Text monday = new Text("Monday");
		gridNode.add(monday, 1, 0);
		
		Text tuesday = new Text("Tuesday");
		gridNode.add(tuesday, 2, 0);
		
		Text wednesday = new Text("Wednesday");
		gridNode.add(wednesday, 3, 0);
		
		Text thursday = new Text("Thursday");
		gridNode.add(thursday, 4, 0);
		
		Text friday = new Text("Friday");
		gridNode.add(friday, 5, 0);
		
		Text saturday = new Text("Saturday");
		gridNode.add(saturday, 6, 0);
		
		Text sunday = new Text("Sunday");
		gridNode.add(sunday, 7, 0);
		
		Text morning = new Text("Morning");
		gridNode.add(morning, 0, 1);
		
		Text lunch = new Text("Afternoon");
		gridNode.add(lunch, 0, 2);
		
		Text dinner = new Text("Evening");
		gridNode.add(dinner, 0, 3);
		
		Button btnShift11 = new Button("New Shift");
		btnShift11.setId("MONDAY MORNING");
		gridNode.add(btnShift11, 1, 1);
		Button btnShift21 = new Button("New Shift");
		btnShift21.setId("TUESDAY MORNING");
		gridNode.add(btnShift21, 2, 1);
		Button btnShift31 = new Button("New Shift");
		btnShift31.setId("WEDNESDAY MORNING");
		gridNode.add(btnShift31, 3, 1);
		Button btnShift41 = new Button("New Shift");
		btnShift41.setId("THURSDAY MORNING");
		gridNode.add(btnShift41, 4, 1);
		Button btnShift51 = new Button("New Shift");
		btnShift51.setId("FRIDAY MORNING");
		gridNode.add(btnShift51, 5, 1);
		Button btnShift61 = new Button("New Shift");
		btnShift61.setId("SATURDAY MORNING");
		gridNode.add(btnShift61, 6, 1);
		Button btnShift71 = new Button("New Shift");
		btnShift71.setId("SUNDAY MORNING");
		gridNode.add(btnShift71, 7, 1);
		Button btnShift12 = new Button("New Shift");
		btnShift12.setId("MONDAY AFTERNOON");
		gridNode.add(btnShift12, 1, 2);
		Button btnShift22 = new Button("New Shift");
		btnShift22.setId("TUESDAY AFTERNOON");
		gridNode.add(btnShift22, 2, 2);
		Button btnShift32 = new Button("New Shift");
		btnShift32.setId("WEDNESDAY AFTERNOON");
		gridNode.add(btnShift32, 3, 2);
		Button btnShift42 = new Button("New Shift");
		btnShift42.setId("THURSDAY AFTERNOON");
		gridNode.add(btnShift42, 4, 2);
		Button btnShift52 = new Button("New Shift");
		btnShift52.setId("FRIDAY AFTERNOON");
		gridNode.add(btnShift52, 5, 2);
		Button btnShift62 = new Button("New Shift");
		btnShift62.setId("SATURDAY AFTERNOON");
		gridNode.add(btnShift62, 6, 2);
		Button btnShift72 = new Button("New Shift");
		btnShift72.setId("SUNDAY AFTERNOON");
		gridNode.add(btnShift72, 7, 2);
		Button btnShift13 = new Button("New Shift");
		btnShift13.setId("MONDAY EVENING");
		gridNode.add(btnShift13, 1, 3);
		Button btnShift23 = new Button("New Shift");
		btnShift23.setId("TUESDAY EVENING");
		gridNode.add(btnShift23, 2, 3);
		Button btnShift33 = new Button("New Shift");
		btnShift33.setId("WEDNESDAY EVENING");
		gridNode.add(btnShift33, 3, 3);
		Button btnShift43 = new Button("New Shift");
		btnShift43.setId("THURSDAY EVENING");
		gridNode.add(btnShift43, 4, 3);
		Button btnShift53 = new Button("New Shift");
		btnShift53.setId("FRIDAY EVENING");
		gridNode.add(btnShift53, 5, 3);
		Button btnShift63 = new Button("New Shift");
		btnShift63.setId("SATURDAY EVENING");
		gridNode.add(btnShift63, 6, 3);
		Button btnShift73 = new Button("New Shift");
		btnShift73.setId("SUNDAY EVENING");
		gridNode.add(btnShift73, 7, 3);
		
		btnShift11.setOnAction(btnHandler);
		btnShift21.setOnAction(btnHandler);
		btnShift31.setOnAction(btnHandler);
		btnShift41.setOnAction(btnHandler);
		btnShift51.setOnAction(btnHandler);
		btnShift61.setOnAction(btnHandler);
		btnShift71.setOnAction(btnHandler);
		btnShift12.setOnAction(btnHandler);
		btnShift22.setOnAction(btnHandler);
		btnShift32.setOnAction(btnHandler);
		btnShift42.setOnAction(btnHandler);
		btnShift52.setOnAction(btnHandler);
		btnShift62.setOnAction(btnHandler);
		btnShift72.setOnAction(btnHandler);
		btnShift13.setOnAction(btnHandler);
		btnShift23.setOnAction(btnHandler);
		btnShift33.setOnAction(btnHandler);
		btnShift43.setOnAction(btnHandler);
		btnShift53.setOnAction(btnHandler);
		btnShift63.setOnAction(btnHandler);
		btnShift73.setOnAction(btnHandler);
		
		gridNode.setAlignment(Pos.CENTER);
		
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(100);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(100);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(100);
	    ColumnConstraints column5 = new ColumnConstraints();
	    column5.setPercentWidth(100);
	    ColumnConstraints column6 = new ColumnConstraints();
	    column6.setPercentWidth(100);
	    ColumnConstraints column7 = new ColumnConstraints();
	    column7.setPercentWidth(100);
	    ColumnConstraints column8 = new ColumnConstraints();
	    column8.setPercentWidth(100);
		
	    gridNode.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, column7, column8);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(50);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(100);
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(100);
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(100);
	    
	    for(Node node:gridNode.getChildren()) {
	    	GridPane.setValignment(node, VPos.CENTER);
	    	GridPane.setHalignment(node, HPos.CENTER);
	    }
	    
	    gridNode.getRowConstraints().addAll(row1, row2, row3, row4);
	    
		return gridNode;
	}

	public static Node createMenuBar() {
		MenuBar mbar = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		MenuItem newMI = new MenuItem("New");
		MenuItem openMI = new MenuItem("Open");
		MenuItem saveMI = new MenuItem("Save");
		MenuItem printMI = new MenuItem("Print");
		MenuItem exitMI = new MenuItem("Exit");
		fileMenu.getItems().addAll(newMI, openMI, saveMI, printMI, new SeparatorMenuItem(), exitMI);
		
		mbar.getMenus().add(fileMenu);
		
		Menu mealsMenu = new Menu("Meals");
		MenuItem viewRMI = new MenuItem("View");
		MenuItem addRMI = new MenuItem("Add");
		MenuItem removeRMI = new MenuItem("Remove");
		MenuItem editRMI = new MenuItem("Edit");
		mealsMenu.getItems().addAll(viewRMI, new SeparatorMenuItem(), addRMI, removeRMI, editRMI);
		
		mbar.getMenus().add(mealsMenu);
		
		Menu peopleMenu = new Menu("People");
		MenuItem viewPMI = new MenuItem("View");
		MenuItem addPMI = new MenuItem("Add");
		MenuItem removePMI = new MenuItem("Remove");
		MenuItem editPMI = new MenuItem("Edit");
		peopleMenu.getItems().addAll(viewPMI, new SeparatorMenuItem(), addPMI, removePMI, editPMI);
		
		mbar.getMenus().add(peopleMenu);
		
		Menu shopMenu = new Menu("Shopping List");
		MenuItem printSMI = new MenuItem("Print");
		shopMenu.getItems().add(printSMI);
		
		mbar.getMenus().add(shopMenu);

		newMI.setOnAction(MEHandler);
		openMI.setOnAction(MEHandler);
		saveMI.setOnAction(MEHandler);
		printMI.setOnAction(MEHandler);
		exitMI.setOnAction(MEHandler);
		viewRMI.setOnAction(MEHandler);
		addRMI.setOnAction(MEHandler);
		removeRMI.setOnAction(MEHandler);
		editRMI.setOnAction(MEHandler);
		viewPMI.setOnAction(MEHandler);
		addPMI.setOnAction(MEHandler);
		removePMI.setOnAction(MEHandler);
		editPMI.setOnAction(MEHandler);
		printSMI.setOnAction(MEHandler);
		
		return mbar;
	}
	
	public static void addShift(Shift shift, Button rmButton) {
		
		FlowPane cellNode = new FlowPane(5,5);
		GridPane.setHalignment(cellNode, HPos.CENTER);
		GridPane.setValignment(cellNode, VPos.CENTER);
		cellNode.setAlignment(Pos.CENTER);
		
		Label personLbl = new Label(shift.getPerson());
		Label mealLbl = new Label(shift.getMeal());
		Separator lblSep = new Separator();
		lblSep.setPrefWidth(150);
		lblSep.setVisible(false);
		Separator btnSep = new Separator();
		btnSep.setPrefWidth(150);
		Button btnEdit = new Button("Edit");
		
		btnEdit.setId(shift.getDay().toString() + " " + shift.getPeriod().toString());
		btnEdit.setOnAction(RotaManager.btnHandler);
		
		cellNode.getChildren().addAll(personLbl, lblSep, mealLbl, btnSep, btnEdit);
		
		for(Node n:RotaManager.rootNode.getChildren()) {
			if(n instanceof GridPane) {
				if(rmButton == null) {
					((GridPane) n).add(cellNode, shift.getDay().getLoc(), shift.getPeriod().getLoc());
					for(Node rmNode : ((GridPane) n).getChildren()) {
						if(rmNode instanceof Button && GridPane.getColumnIndex(rmNode) == shift.getDay().getLoc()
								&& GridPane.getRowIndex(rmNode) == shift.getPeriod().getLoc()) {
							((GridPane) n).getChildren().remove(rmNode);
							break;
						}
								
					}
				}
				else if(rmButton.getParent() instanceof FlowPane) {
					((GridPane) n).add(cellNode, shift.getDay().getLoc(), shift.getPeriod().getLoc());
					((GridPane) n).getChildren().remove(rmButton.getParent());
				}
				else {
					((GridPane) n).add(cellNode, shift.getDay().getLoc(), shift.getPeriod().getLoc());
					((GridPane) n).getChildren().remove(rmButton);
				}
			}
		}
	}
	
	

}
