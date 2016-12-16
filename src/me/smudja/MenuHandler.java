package me.smudja;

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
	
	@Override
	public void handle(ActionEvent event) {
		String name = ((MenuItem)event.getTarget()).getText();
		
		switch(name) {
			case "Exit": 	Platform.exit();
						 	break;
			case "New":	 	RotaManager.rootNode.setTop(RotaManager.createMenuBar());
						 	RotaManager.rootNode.setCenter(RotaManager.createGridPane());
						 	RotaManager.primaryStage.show();
						 	break;
			case "View": 	String targetMenuView = ((MenuItem) event.getTarget()).getParentMenu().getText();
						 	if(targetMenuView.compareTo("People") == 0) {
						 		showViewStage("People", DataManager.INSTANCE.getPeople());
						 	}
						 	else {
						 		showViewStage("Meals", DataManager.INSTANCE.getMeals());
						 	}
						 	break;
			case "Add":  	String targetMenuAdd = ((MenuItem) event.getTarget()).getParentMenu().getText();
			 			 	if(targetMenuAdd.compareTo("People") == 0) {
			 			 		showAddStage("Person");
			 			 	}
			 			 	else {
			 			 		showAddStage("Meal");
			 			 	}
			 			 	break;
			case "Remove":	String targetMenuRemove = ((MenuItem) event.getTarget()).getParentMenu().getText();
		 					if(targetMenuRemove.compareTo("People") == 0) {
		 						showRemoveStage("People", DataManager.INSTANCE.getPeople());
		 					}
		 					else {
		 						showRemoveStage("Meals", DataManager.INSTANCE.getMeals());
		 					}
		 					break;
			default:	 	break;
		}
	}

	private void showRemoveStage(String type, String[] items) {
		Stage removeStage = new Stage();
		
		removeStage.setTitle("Remove " + type);
		
		FlowPane rootNode = new FlowPane(20,20);
		
		rootNode.setAlignment(Pos.CENTER);
		
		removeStage.setScene(new Scene(rootNode, 250, 550));
		
		Label lblType = new Label(type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(230);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(items);
		
		ListView<String> lvItems = new ListView<String>(itemsList);
		
		lvItems.setPrefWidth(200);
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
		
		viewStage.setScene(new Scene(rootNode, 250, 550));
		
		Label lblType = new Label(type);
		
		Separator separator = new Separator();
		separator.setPrefWidth(230);
		
		rootNode.getChildren().addAll(lblType, separator);
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(items);
		
		ListView<String> lvItems = new ListView<String>(itemsList);
		
		lvItems.setPrefWidth(200);
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
