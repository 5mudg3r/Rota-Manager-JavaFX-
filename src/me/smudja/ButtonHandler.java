package me.smudja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ButtonHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Button btnTarget = (Button) event.getTarget();
		
		Stage shiftStage = new Stage();
		shiftStage.setTitle("New Shift");
		
		FlowPane rootNode = new FlowPane(10,30);
		
		rootNode.setAlignment(Pos.CENTER);
		
		shiftStage.setScene(new Scene(rootNode, 450, 250));
		
		Label dayLbl = new Label(btnTarget.getId());
		
		Separator separator1 = new Separator();
		separator1.setPrefWidth(400);
		
		rootNode.getChildren().addAll(dayLbl, separator1);
		
		ObservableList<String> people = FXCollections.observableArrayList("Richard", "Annick", "Louis");
		
		ComboBox<String> cbPeople = new ComboBox<String>(people);
		
		cbPeople.setValue("Richard");
		
		Label lblPeople = new Label("Person: ");
		
		rootNode.getChildren().addAll(lblPeople, cbPeople);
		
		Separator choiceSep = new Separator();
		choiceSep.setPrefWidth(25);
		choiceSep.setVisible(false);
		rootNode.getChildren().add(choiceSep);
		
		ObservableList<String> meals = FXCollections.observableArrayList("Beef Stew", "Burgers", "Tomato Soup");
		
		ComboBox<String> cbMeals = new ComboBox<String>(meals);
		
		cbMeals.setValue("Beef Stew");
		
		Label lblMeals = new Label("Meal: ");
		
		rootNode.getChildren().addAll(lblMeals, cbMeals);
		
		Separator separator2 = new Separator();
		separator2.setPrefWidth(400);
		
		rootNode.getChildren().add(separator2);
		
		Button btnConfirm = new Button("Confirm");
		
		btnConfirm.setOnAction( (ae) -> {
			
			FlowPane cellNode = new FlowPane(5,5);
			GridPane.setHalignment(cellNode, HPos.CENTER);
			GridPane.setValignment(cellNode, VPos.CENTER);
			cellNode.setAlignment(Pos.CENTER);
			
			Label personLbl = new Label(cbPeople.getValue());
			Label mealLbl = new Label(cbMeals.getValue());
			Separator lblSep = new Separator();
			lblSep.setPrefWidth(150);
			lblSep.setVisible(false);
			Separator btnSep = new Separator();
			btnSep.setPrefWidth(150);
			Button btnEdit = new Button("Edit");
			
			btnEdit.setId(btnTarget.getId());
			btnEdit.setOnAction(RotaManager.btnHandler);
			
			cellNode.getChildren().addAll(personLbl, lblSep, mealLbl, btnSep, btnEdit);
			
			for(Node n:RotaManager.rootNode.getChildren()) {
				if(n instanceof GridPane) {				
					if(btnTarget.getParent() instanceof FlowPane) {
						((GridPane) n).add(cellNode, GridPane.getColumnIndex(btnTarget.getParent()), GridPane.getRowIndex(btnTarget.getParent()));
						((GridPane) n).getChildren().remove(btnTarget.getParent());
					}
					else {
						((GridPane) n).add(cellNode, GridPane.getColumnIndex(btnTarget), GridPane.getRowIndex(btnTarget));
						((GridPane) n).getChildren().remove(btnTarget);
					}
				}
			}
			
			shiftStage.close();
		});
		
		rootNode.getChildren().add(btnConfirm);
		
		shiftStage.show();
	}

}
