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

/**
 * This is the main class for the Java Application.
 * It handles launching of the Application.
 * It also creates and populates the menu bar and blank rota configuration screen.
 * This class also adds new shifts to the display via {@link me.smudja.RotaManager#addShift(Shift, Button) addShift()}.
 * 
 * @author smithl
 */
public class RotaManager extends Application {
	
	/**
	 * Stores the current version of the program.
	 */
	static final String VERSION = "1.1b";
	
	/**
	 * Stores a static reference to the root node of the primary stage.
	 */
	static BorderPane rootNode;
	/**
	 * Stores a static reference to the root node of the grid layout.
	 * This is a child node of {@link me.smudja.RotaManager#rootNode rootNode}.
	 */
	static GridPane gridNode;
	/**
	 * Stores a static reference to the primary stage of the application.
	 * This is the stage with scene with root node {@link me.smudja.RotaManager#rootNode rootNode}.
	 */
	static Stage primaryStage;
	/**
	 * This stores a static reference to {@link me.smudja.MenuHandler} for use only by methods within this class.
	 * It is instantiated in {@link me.smudja.RotaManager#init() init()}
	 * 
	 * @see me.smudja.MenuHandler
	 */
	private static MenuHandler MEHandler;
	/**
	 * This stores a static reference to {@link me.smudja.ButtonHandler}.
	 * It is instantiated in {@link me.smudja.RotaManager#init() init()}
	 * 
	 * @see me.smudja.ButtonHandler
	 */
	static ButtonHandler btnHandler;
	/**
	 * This stores a reference to the enum {@link me.smudja.DataManager DataManager}.
	 * This reference is never used in the class, it is only created to allow the enum to 
	 * be instantiated in the {@link me.smudja.RotaManager#init() init()} method.
	 * 
	 * @see me.smudja.DataManager
	 */
	@SuppressWarnings("unused")
	private DataManager dataManager;
	/**
	 * This stores a reference to the enum {@link me.smudja.ShiftManager ShiftManager}.
	 * This reference is never used in the class, it is only created to allow the enum to 
	 * be instantiated in the {@link me.smudja.RotaManager#init() init()} method.
	 * 
	 * @see me.smudja.ShiftManager
	 */
	@SuppressWarnings("unused")
	private ShiftManager shiftManager;
	
	/**
	 * This method overrides the {@code init()} method in the {@code JavaFX Application} class.
	 * It instantiates the {@code MenuHandler}, {@code ButtonHandler}, {@code DataManager} and {@code ShiftManager} classes.
	 * 
	 * @see javafx.application.Application#init()
	 * @see me.smudja.MenuHandler
	 * @see me.smudja.ButtonHandler
	 * @see me.smudja.DataManager
	 * @see me.smudja.ShiftManager
	 */
	@Override
	public void init() {
		dataManager = DataManager.INSTANCE;
		shiftManager = ShiftManager.INSTANCE;
		MEHandler = new MenuHandler();
		btnHandler = new ButtonHandler();
		DateManager.INSTANCE.openFile();
		DateManager.INSTANCE.readFile();
		DateManager.INSTANCE.closeFile();
	}
	
	/**
	 * This is the main method of the application.
	 * It calls launch to launch the application.
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method overrides the {@code start(Stage stage)} method in the {@code JavaFX Application} class.
	 * It creates, configures and displays the primary stage via calls to {@link me.smudja.RotaManager#createGridPane() createGridPane()} and
	 * {@link me.smudja.RotaManager#createMenuBar() createMenuBar()}.
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		
		RotaManager.primaryStage = primaryStage;
		
		primaryStage.setTitle("Rota Manager v" + VERSION);
		
		rootNode = new BorderPane();
		
		primaryStage.setScene(new Scene(rootNode, 1200, 600));		
		
		rootNode.setTop(createMenuBar());
		rootNode.setCenter(createGridPane());
		
		primaryStage.show();		
	}

	/**
	 * This method creates and populates a {@link javafx.scene.layout.GridPane GridPane} node
	 * with an empty shift configuration. This method is called by {@link me.smudja.RotaManager#start(Stage) start()}.
	 * 
	 * @return Node with empty shift configuration
	 */
	public static Node createGridPane() {
		
		GridPane gridNode = new GridPane();

		
		gridNode.setVgap(10);
		gridNode.setHgap(10);
		gridNode.setPadding(new Insets(0, 10, 0, 10));
		
		Text rmversion = new Text("RM v" + VERSION);
		gridNode.add(rmversion, 0, 0);
		
		String[] dates = DateManager.INSTANCE.getDates();
		
		Text monday = new Text(dates[0]);
		gridNode.add(monday, 1, 0);
		
		Text tuesday = new Text(dates[1]);
		gridNode.add(tuesday, 2, 0);
		
		Text wednesday = new Text(dates[2]);
		gridNode.add(wednesday, 3, 0);
		
		Text thursday = new Text(dates[3]);
		gridNode.add(thursday, 4, 0);
		
		Text friday = new Text(dates[4]);
		gridNode.add(friday, 5, 0);
		
		Text saturday = new Text(dates[5]);
		gridNode.add(saturday, 6, 0);
		
		Text sunday = new Text(dates[6]);
		gridNode.add(sunday, 7, 0);
		
		Text morning = new Text("Morning");
		gridNode.add(morning, 0, 1);
		
		Text afternoon = new Text("Afternoon");
		gridNode.add(afternoon, 0, 2);
		
		Text evening = new Text("Evening");
		gridNode.add(evening, 0, 3);
		
		Button[][] buttons = new Button[7][3];
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 3; j++) {
				buttons[i][j] = new Button("New Shift");
				buttons[i][j].setId(Day.getDayString(i + 1) + " " + Period.getPeriodString(j + 1));
				gridNode.add(buttons[i][j], i + 1, j + 1);
				buttons[i][j].setOnAction(btnHandler);
			}
		}
		
		gridNode.setAlignment(Pos.CENTER);
		
		ColumnConstraints[] colConstraints = new ColumnConstraints[8];
		
		for(int i = 0; i < 8; i++) {
			colConstraints[i] = new ColumnConstraints();
			if(i == 0) {
				colConstraints[i].setPercentWidth(50);
			}
			else {
				colConstraints[i].setPercentWidth(100);
			}
			gridNode.getColumnConstraints().add(colConstraints[i]);
		}
		
		RowConstraints[] rowConstraints = new RowConstraints[4];
		
		for(int i = 0; i < 4; i++) {
			rowConstraints[i] = new RowConstraints();
			if(i == 0) {
				rowConstraints[i].setPercentHeight(50);
			}
			else {
				rowConstraints[i].setPercentHeight(100);
			}
			gridNode.getRowConstraints().add(rowConstraints[i]);
		}
			    
	    for(Node node:gridNode.getChildren()) {
	    	GridPane.setValignment(node, VPos.CENTER);
	    	GridPane.setHalignment(node, HPos.CENTER);
	    }
	    
		return gridNode;
	}
	
	/**
	 * This method creates and populates a {@link javafx.scene.control.MenuBar MenuBar} node
	 * with the required menu items. This method is called by {@link me.smudja.RotaManager#start(Stage) start()}.
	 * 
	 * @return Node with menu items
	 */
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
		MenuItem previewSMI = new MenuItem("Preview");
		shopMenu.getItems().addAll(printSMI, previewSMI);
		
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
		previewSMI.setOnAction(MEHandler);
		
		return mbar;
	}
	
	/**
	 * This method adds the shift defined in the parameters to the location of the button in the parameters.
	 * It handles layout and adding an edit button.
	 * It does not add the shift to the shift manager.
	 * 
	 * @param shift 	The shift to add the configuration
	 * @param rmButton	The button whose location you want to add the shift to
	 */
	public static void addShift(Shift shift, Button rmButton) {
		
		FlowPane cellNode = new FlowPane(5,5);
		GridPane.setHalignment(cellNode, HPos.CENTER);
		GridPane.setValignment(cellNode, VPos.CENTER);
		cellNode.setAlignment(Pos.CENTER);
		
		Label personLbl = new Label(shift.getPerson());
		Label mealLbl = new Label(shift.getMeal());
		personLbl.setPrefWidth(150);
		mealLbl.setPrefWidth(150);
		personLbl.setMaxWidth(200);
		mealLbl.setMaxWidth(200);
		personLbl.setAlignment(Pos.CENTER);
		mealLbl.setAlignment(Pos.CENTER);
		Separator btnSep = new Separator();
		btnSep.setPrefWidth(150);
		Button btnEdit = new Button("Edit");
		
		btnEdit.setId(shift.getDay().toString() + " " + shift.getPeriod().toString());
		btnEdit.setOnAction(RotaManager.btnHandler);
		
		cellNode.getChildren().addAll(personLbl, mealLbl, btnSep, btnEdit);
		
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
