package me.smudja;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** 
 * This class handles menu click events from {@link me.smudja.RotaManager RotaManager}.
 * It is instantiated in {@link me.smudja.RotaManager#init() init()}.
 * 
 * @author smithl
 * 
 * @see    me.smudja.RotaManager
 */
public class MenuHandler implements EventHandler<ActionEvent> {
	
	/**
	 *  A reference to {@link me.smudja.ShiftManager}
	 *  This will never instantiate the {@code ShiftManager}.
	 */
	ShiftManager sm = ShiftManager.INSTANCE;
	
	/**
	 * FileChooser is what we use to display open/save file dialogues
	 */
	final FileChooser fileChooser = new FileChooser();
	
	/** 
	 * This method overrides the {@code handle()} method from {@code EventHandler}.
	 * It determines which menu item was clicked and handles it accordingly.
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {
		String name = ((MenuItem)event.getTarget()).getText();
		String targetMenu = ((MenuItem) event.getTarget()).getParentMenu().getText();
		
		switch(name) {
			case "Exit": 	Platform.exit();
						 	break;
			case "New":	 	showNewStage();
						 	break;
			case "View": 	if(targetMenu.compareTo("People") == 0) {
						 		showViewStage("People", DataManager.INSTANCE.getPeople());
						 	}
						 	else {
						 		showViewStage("Meals", DataManager.INSTANCE.getMeals());
						 	}
						 	break;
			case "Add":  	if(targetMenu.compareTo("People") == 0) {
			 			 		showAddStage("Person");
			 			 	}
			 			 	else {
			 			 		showAddStage("Meal");
			 			 	}
			 			 	break;
			case "Remove":	if(targetMenu.compareTo("People") == 0) {
		 						showRemoveStage("People", DataManager.INSTANCE.getPeople());
		 					}
		 					else {
		 						showRemoveStage("Meals", DataManager.INSTANCE.getMeals());
		 					}
		 					break;
			case "Open":	showFileStage("Open");
							break;
			case "Save":	showFileStage("Save");
							break;
			case "Edit":	if(targetMenu.compareTo("People") == 0) {
								showEditStage("Person", DataManager.INSTANCE.getPeople());
							}
							else {
								showEditStage("Meal", DataManager.INSTANCE.getMeals());
							}
							break;
			case "Print":	if(targetMenu.compareTo("Shopping List") == 0) {
								new PrintListHandler(ShiftManager.INSTANCE.getWeeksIngredients());
							}
							else {
								new PrintRotaHandler();
							}
							break;
			case "Preview": showPreviewStage();
							break;
			default:	 	System.out.println("Menu Item Not Recognised");
							break;
		}
	}

	/**
	 * This method shows the new stage, requesting the date of the Saturday of the week, before displaying a blank rota.
	 * @param b 
	 */
	private void showNewStage() {
		Stage newStage = new Stage();
		
		newStage.setTitle("New Rota");
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		newStage.setScene(new Scene(rootNode, 350, 250));
		
		Label dateLbl = new Label("Date of Saturday");
		dateLbl.setPrefWidth(330);
		dateLbl.setAlignment(Pos.CENTER);
		Label infoLbl = new Label("(Leave blank for default)");
		infoLbl.setPrefWidth(330);
		infoLbl.setAlignment(Pos.CENTER);
		
		Separator separator = new Separator();
		separator.setPrefWidth(330);
		
		rootNode.getChildren().addAll(dateLbl, infoLbl, separator);
		
		TextField tfDate = new TextField();
		tfDate.setTooltip(new Tooltip("YYYY-MM-DD"));
		tfDate.setPrefWidth(300);
		
		Button btnOk = new Button("Ok");
		
		btnOk.setPrefWidth(85);
		btnOk.setOnAction( (ae) -> {
			if(tfDate.getText().compareTo("") == 0) {
				RotaManager.rootNode.setTop(RotaManager.createMenuBar());
				RotaManager.rootNode.setCenter(RotaManager.createGridPane());
				RotaManager.primaryStage.show();
				sm.purge();
				newStage.close();
			}
			else {
				try {
					LocalDate satDate =  LocalDate.parse(tfDate.getText());
					sm.purge();
					RotaManager.rootNode.setTop(RotaManager.createMenuBar());
					RotaManager.rootNode.setCenter(RotaManager.createGridPane(satDate));
					RotaManager.primaryStage.show();
					newStage.close();
				}
				catch(DateTimeParseException exc) {
					Stage errStage = new Stage();

					errStage.setTitle("Date Format Error");

					FlowPane errRootNode = new FlowPane(20,20);

					errRootNode.setAlignment(Pos.CENTER);

					errStage.setScene(new Scene(errRootNode, 350, 150));

					Label errLbl = new Label("ERROR: Incorrect Date Format (YYYY-MM-DD)");
					errLbl.setPrefWidth(330);
					errLbl.setAlignment(Pos.CENTER);

					Button btnErrOk = new Button("Ok");

					btnErrOk.setOnAction( (errae) -> {
						errStage.close();
						newStage.close();
						showNewStage();
					});

					errRootNode.getChildren().addAll(errLbl, btnErrOk);
					
					errStage.show();
				}
			}
		});
		
		rootNode.getChildren().addAll(tfDate, btnOk);
		
		newStage.show();
	}
	
//	private void showOpenStage(String strName) {
//		Stage newStage = new Stage();
//		
//		newStage.setTitle("New Rota");
//		
//		FlowPane rootNode = new FlowPane(20,20);
//		
//		rootNode.setAlignment(Pos.CENTER);
//		
//		newStage.setScene(new Scene(rootNode, 350, 250));
//		
//		Label dateLbl = new Label("Date of Saturday");
//		dateLbl.setPrefWidth(330);
//		dateLbl.setAlignment(Pos.CENTER);
//		Label infoLbl = new Label("(Leave blank for default)");
//		infoLbl.setPrefWidth(330);
//		infoLbl.setAlignment(Pos.CENTER);
//		
//		Separator separator = new Separator();
//		separator.setPrefWidth(330);
//		
//		rootNode.getChildren().addAll(dateLbl, infoLbl, separator);
//		
//		TextField tfDate = new TextField();
//		tfDate.setTooltip(new Tooltip("YYYY-MM-DD"));
//		tfDate.setPrefWidth(300);
//		
//		Button btnOk = new Button("Ok");
//		
//		btnOk.setPrefWidth(85);
//		btnOk.setOnAction( (ae) -> {
//			if(tfDate.getText().compareTo("") == 0) {
//				RotaManager.rootNode.setTop(RotaManager.createMenuBar());
//				RotaManager.rootNode.setCenter(RotaManager.createGridPane());
//				RotaManager.primaryStage.show();
//				sm.purge();
//				sm.load(strName);
//				for(Shift s : sm.getShifts()) {
//					RotaManager.addShift(s, null);
//				}
//				newStage.close();
//			}
//			else {
//				try {
//					LocalDate satDate =  LocalDate.parse(tfDate.getText());
//					RotaManager.rootNode.setTop(RotaManager.createMenuBar());
//					RotaManager.rootNode.setCenter(RotaManager.createGridPane(satDate));
//					RotaManager.primaryStage.show();
//					sm.purge();
//					sm.load(strName);
//					for(Shift s : sm.getShifts()) {
//						RotaManager.addShift(s, null);
//					}
//					newStage.close();
//				}
//				catch(DateTimeParseException exc) {
//					Stage errStage = new Stage();
//
//					errStage.setTitle("Date Format Error");
//
//					FlowPane errRootNode = new FlowPane(20,20);
//
//					errRootNode.setAlignment(Pos.CENTER);
//
//					errStage.setScene(new Scene(errRootNode, 350, 150));
//
//					Label errLbl = new Label("ERROR: Incorrect Date Format (YYYY-MM-DD)");
//					errLbl.setPrefWidth(330);
//					errLbl.setAlignment(Pos.CENTER);
//
//					Button btnErrOk = new Button("Ok");
//
//					btnErrOk.setOnAction( (errae) -> {
//						errStage.close();
//						newStage.close();
//						showOpenStage(strName);
//					});
//
//					errRootNode.getChildren().addAll(errLbl, btnErrOk);
//					
//					errStage.show();
//				}
//			}
//		});
//		
//		rootNode.getChildren().addAll(tfDate, btnOk);
//		
//		newStage.show();
//	}

	/**
	 * This method displays and handles a stage for editing of the current week's ingredients before printing
	 * The print button will instantiate a new {@link me.smudja.PrintListHandler PrintListHandler} with the list of
	 * ingredients currently displayed on the stage.
	 */
	private void showPreviewStage() {
		Stage previewStage = new Stage();
		
		previewStage.setTitle("Print Preview");
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		previewStage.setScene(new Scene(rootNode, 350, 650));
		
		Label ingredsLbl = new Label("Ingredients");
		
		Separator separator = new Separator();
		separator.setPrefWidth(330);
		
		rootNode.getChildren().addAll(ingredsLbl, separator);
		
		String[] strIngreds = ShiftManager.INSTANCE.getWeeksIngredients();
		ObservableList<String> ingredsList = FXCollections.observableArrayList(strIngreds);
		
		ListView<String> lvIngreds = new ListView<String>(ingredsList);
		
		lvIngreds.setPrefWidth(300);
		lvIngreds.setPrefHeight(425);
		
		lvIngreds.setEditable(true);
		lvIngreds.setCellFactory(TextFieldListCell.forListView());
		
		lvIngreds.setTooltip(new Tooltip("To edit an item, double-click it and then press enter when done"));
		
		rootNode.getChildren().add(lvIngreds);
		
		Button btnAdd = new Button("Add");
		Button btnRemove = new Button("Remove");
		Button btnReset = new Button("Reset");
		
		btnAdd.setPrefWidth(85);
		btnAdd.setOnAction( (ae) -> {
			lvIngreds.getItems().add("NEW ITEM");
			int index = lvIngreds.getItems().size() - 1;
			lvIngreds.scrollTo(index);
			lvIngreds.getFocusModel().focus(index);
			lvIngreds.getSelectionModel().clearAndSelect(index);
		});
		
		btnRemove.setPrefWidth(85);
		btnRemove.setOnAction( (ae) -> {
			int selected = lvIngreds.getSelectionModel().getSelectedIndex();
			lvIngreds.getItems().remove(selected);
		});
		
		btnReset.setPrefWidth(85);
		btnReset.setOnAction( (ae) -> {
			lvIngreds.setItems(FXCollections.observableArrayList(strIngreds));
			lvIngreds.refresh();
		});
		
		rootNode.getChildren().addAll(btnAdd, btnRemove, btnReset);
		
		Button btnPrint = new Button("Print");
		btnPrint.setPrefWidth(85);
		btnPrint.setOnAction( (ae) -> {
			previewStage.close();
			String[] printList = new String[lvIngreds.getItems().size()];
			new PrintListHandler(lvIngreds.getItems().toArray(printList));
		});
		
		rootNode.getChildren().add(btnPrint);
		
		previewStage.show();
	}

	/**
	 * This method displays and handles a stage for editing either a person or a meal.
	 * It displays a list of the current items of type specified and gives an edit button to choose an item to edit.
	 * The edit button will call {@link me.smudja.MenuHandler#showEditSubStage(String, String) showEditSubStage()} which
	 * handles the actual editing of the item.
	 * 
	 * @param type	The type of item to edit ("Person" or "Meal")
	 * @param items	An array containing the currently active items of type above.
	 */
	private void showEditStage(String type, String[] items) {
		Stage editStage = new Stage();
		
		editStage.setTitle("Edit " + type);
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		editStage.setScene(new Scene(rootNode, 300, 550));
		
		Label lblType = new Label(type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(280);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(items);
		
		ListView<String> lvItems = new ListView<String>(itemsList);
		
		lvItems.setPrefWidth(250);
		lvItems.setPrefHeight(375);
		
		Button btnEdit = new Button("Edit");
		btnEdit.setOnAction( (ae) -> {
			String strRemove = lvItems.getSelectionModel().getSelectedItem();
			
			editStage.close();
			
			showEditSubStage(type, strRemove);
		});
		
		rootNode.getChildren().addAll(lvItems, btnEdit);
		
		editStage.show();
	}

	/**
	 * This method is only ever called by {@link me.smudja.MenuHandler#showEditStage(String, String[]) showEditStage()}.
	 * It handles editing of an item supplied as a parameter.
	 * 
	 * @param type			The type of item we are editing ("Person" or "Meal")
	 * @param strRemove		The item to be edited
	 */
	private void showEditSubStage(String type, String strRemove) {
		Stage editSubStage = new Stage();
		
		editSubStage.setTitle("Edit " + type);
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		editSubStage.setScene(new Scene(rootNode, 400, 250));
		
		Label lblType = new Label("Edit " + type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(375);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		Label lblName = new Label("Name");
		
		TextField tfName = new TextField();
		tfName.setPrefWidth(250);
		tfName.setText(strRemove);
		
		Separator fieldSep = new Separator();
		fieldSep.setPrefWidth(30);
		fieldSep.setVisible(false);
		
		Separator btnSep = new Separator();
		btnSep.setPrefWidth(15);
		btnSep.setVisible(false);
		
		Label lblValue;
		
		TextField tfValue = new TextField();
		tfValue.setPrefWidth(250);
		
		if(type.compareTo("Person") == 0) {
			lblValue = new Label("Phone");
			tfValue.setText(DataManager.INSTANCE.getPhone(strRemove));
		}
		else {
			lblValue = new Label("Ingredients");
			tfValue.setTooltip(new Tooltip("Separate ingredients with a comma (,)"));
			tfValue.setPromptText("Separate ingredients by a comma");
			tfValue.setText(String.join(", ", DataManager.INSTANCE.getIngredients(strRemove)));
		}	
		
		Button btnEdit = new Button("Edit " + type);
		
		btnEdit.setOnAction( (ae) -> {
			String strName = tfName.getText();
			String strValue = tfValue.getText();
			
			String[] response;
			
			if(type.compareTo("Person") == 0) {
				response = new String[]{strName, strValue};
			}
			else {
				String[] ingreds = strValue.split(",");
				response = new String[ingreds.length + 1];
				response[0] = strName;
				for (int j = 0; j < ingreds.length; j++) {
				    response[j+1] = ingreds[j].trim();
				}
			}
			if(type.compareTo("Person") == 0) {
				DataManager.INSTANCE.removePerson(strRemove);
				DataManager.INSTANCE.addPerson(response[0], response[1]);
			}
			else {
				DataManager.INSTANCE.removeMeal(strRemove);
				DataManager.INSTANCE.addMeal(response[0], Arrays.copyOfRange(response, 1, response.length));
			}
			editSubStage.close();
		});
		
		rootNode.getChildren().addAll(lblName, tfName, fieldSep, lblValue, tfValue, btnSep, btnEdit);
		
		editSubStage.show();
		
	}

	/**
	 * This method handles the stage for file operations.
	 * 
	 * @param type	The type of file operation ("Open" or "Save")
	 */
	private void showFileStage(String type) {
				
		if(type.compareTo("Open") == 0) {
			File openFile = fileChooser.showOpenDialog(RotaManager.primaryStage);
			if(openFile == null) {
				return;
			}
			sm.purge();
			sm.load(openFile);		
			try {
				LocalDate satDate =  LocalDate.parse(sm.getHeaders()[5]);
				RotaManager.rootNode.setCenter(RotaManager.createGridPane(satDate));
			} 
			catch(DateTimeParseException exc) {
				RotaManager.rootNode.setCenter(RotaManager.createGridPane());
			}
			for(Shift s : sm.getShifts()) {
				RotaManager.addShift(s, null);
			}
		}
		else {
			File saveFile = fileChooser.showSaveDialog(RotaManager.primaryStage);
			if(saveFile == null) {
				return;
			}
			sm.save(saveFile);
		}		
	}

	/**
	 * This method handles the stage for removing items.
	 * 
	 * @param type	The type of item to remove ("People" or "Meals")
	 * @param items	An array of the currently active items
	 */
	private void showRemoveStage(String type, String[] items) {
		Stage removeStage = new Stage();
		
		removeStage.setTitle("Remove " + type);
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		removeStage.setScene(new Scene(rootNode, 300, 550));
		
		Label lblType = new Label(type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(280);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(items);
		
		ListView<String> lvItems = new ListView<String>(itemsList);
		
		lvItems.setPrefWidth(250);
		lvItems.setPrefHeight(375);
		
		Button btnRemove = new Button("Remove");
		btnRemove.setOnAction( (ae) -> {
			String strRemove = lvItems.getSelectionModel().getSelectedItem();
			if(type.compareTo("People") == 0) {
				DataManager.INSTANCE.removePerson(strRemove);
			}
			else {
				DataManager.INSTANCE.removeMeal(strRemove);
			}
			removeStage.close();
		});
		
		rootNode.getChildren().addAll(lvItems, btnRemove);
		
		removeStage.show();
	}
	
	/**
	 * This method handles the stage for adding items
	 * 
	 * @param type	The type of item to add ("Person" or "Meal")
	 */
	private void showAddStage(String type) {
		Stage addStage = new Stage();
		
		addStage.setTitle("New " + type);
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		addStage.setScene(new Scene(rootNode, 400, 250));
		
		Label lblType = new Label("New " + type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(375);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		Label lblName = new Label("Name");
		
		TextField tfName = new TextField();
		tfName.setPrefWidth(250);
		
		Separator fieldSep = new Separator();
		fieldSep.setPrefWidth(30);
		fieldSep.setVisible(false);
		
		Separator btnSep = new Separator();
		btnSep.setPrefWidth(15);
		btnSep.setVisible(false);
		
		Label lblValue;
		
		TextField tfValue = new TextField();
		tfValue.setPrefWidth(250);
		
		if(type.compareTo("Person") == 0) {
			lblValue = new Label("Phone");
		}
		else {
			lblValue = new Label("Ingredients");
			tfValue.setTooltip(new Tooltip("Separate ingredients with a comma (,)"));
			tfValue.setPromptText("Separate ingredients by a comma");
		}	
		
		Button btnAdd = new Button("Add " + type);
		
		btnAdd.setOnAction( (ae) -> {
			String strName = tfName.getText();
			String strValue = tfValue.getText();
			if(type.compareTo("Person") == 0) {
				DataManager.INSTANCE.addPerson(strName, strValue);
			}
			else {
				String[] ingreds = strValue.split(",");
				for (int i = 0; i < ingreds.length; i++) {
				    ingreds[i] = ingreds[i].trim();
				}
				DataManager.INSTANCE.addMeal(strName, ingreds);
			}		
			addStage.close();
		});
		
		rootNode.getChildren().addAll(lblName, tfName, fieldSep, lblValue, tfValue, btnSep, btnAdd);
		
		addStage.show();
	}

	/**
	 * This method handles the stage for viewing currently active items.
	 * 
	 * @param type	The type of item ("People" or "Meals")
	 * @param items	An array of the currently active items
	 */
	private void showViewStage(String type, String[] items) {
		Stage viewStage = new Stage();
		
		viewStage.setTitle("View " + type);
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		viewStage.setScene(new Scene(rootNode, 300, 550));
		
		Label lblType = new Label(type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(280);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(items);
		
		ListView<String> lvItems = new ListView<String>(itemsList);
		
		lvItems.setPrefWidth(250);
		lvItems.setPrefHeight(375);
		
		lvItems.setFocusTraversable( false );
		
		Button btnOk = new Button("Ok");
		btnOk.setOnAction( (ae) -> {
			viewStage.close();
		});
		
		rootNode.getChildren().addAll(lvItems, btnOk);
		
		viewStage.show();
	}

}
