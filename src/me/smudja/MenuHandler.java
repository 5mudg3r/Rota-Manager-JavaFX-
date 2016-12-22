package me.smudja;

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
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MenuHandler implements EventHandler<ActionEvent> {
	
	ShiftManager shiftManager;
	
	public MenuHandler() {
		shiftManager = ShiftManager.INSTANCE;
	}
	
	@Override
	public void handle(ActionEvent event) {
		String name = ((MenuItem)event.getTarget()).getText();
		String targetMenu = ((MenuItem) event.getTarget()).getParentMenu().getText();
		
		switch(name) {
			case "Exit": 	Platform.exit();
						 	break;
			case "New":	 	RotaManager.rootNode.setTop(RotaManager.createMenuBar());
						 	RotaManager.rootNode.setCenter(RotaManager.createGridPane());
						 	RotaManager.primaryStage.show();
						 	shiftManager.purge();
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
			default:	 	System.out.println("Menu Item Not Recognised");
							break;
		}
	}

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

	private void showFileStage(String type) {
		Stage fileStage = new Stage();
		
		fileStage.setTitle(type + " Shift Configuration");
		
		FlowPane rootNode = new FlowPane(25,25);
		
		rootNode.setAlignment(Pos.CENTER);
		
		fileStage.setScene(new Scene(rootNode, 450, 200));
		
		Label lblType = new Label(type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(400);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		Label lblName = new Label("Shift Name");
		
		TextField tfName = new TextField();
		tfName.setPrefWidth(300);
		
		Separator separator2 = new Separator();
		separator2.setPrefWidth(400);
		separator2.setVisible(false);
		
		Button btnType = new Button(type);
		
		btnType.setOnAction( (ae) -> {
			String strName = tfName.getText();
			
			if(type.compareTo("Open") == 0) {
				RotaManager.rootNode.setCenter(RotaManager.createGridPane());
				shiftManager.purge();
				shiftManager.load(strName);
				for(Shift s : shiftManager.getShifts()) {
					RotaManager.addShift(s, null);
				}
			}
			else {
				shiftManager.save(strName);
			}		
			fileStage.close();
		});
		
		rootNode.getChildren().addAll(lblName, tfName, separator2, btnType);
		
		fileStage.show();
	}

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
