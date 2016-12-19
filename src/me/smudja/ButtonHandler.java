package me.smudja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ButtonHandler implements EventHandler<ActionEvent> {
	
	ShiftManager sm = ShiftManager.INSTANCE;

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
		
		ObservableList<String> people = FXCollections.observableArrayList(DataManager.INSTANCE.getPeople());
		
		ComboBox<String> cbPeople = new ComboBox<String>(people);
		
		cbPeople.setValue(people.get(0));
		
		Label lblPeople = new Label("Person: ");
		
		rootNode.getChildren().addAll(lblPeople, cbPeople);
		
		Separator choiceSep = new Separator();
		choiceSep.setPrefWidth(25);
		choiceSep.setVisible(false);
		rootNode.getChildren().add(choiceSep);
		
		ObservableList<String> meals = FXCollections.observableArrayList(DataManager.INSTANCE.getMeals());
		
		ComboBox<String> cbMeals = new ComboBox<String>(meals);
		
		cbMeals.setValue(meals.get(0));
		
		Label lblMeals = new Label("Meal: ");
		
		rootNode.getChildren().addAll(lblMeals, cbMeals);
		
		Separator separator2 = new Separator();
		separator2.setPrefWidth(400);
		
		rootNode.getChildren().add(separator2);
		
		Button btnConfirm = new Button("Confirm");
		
		btnConfirm.setOnAction( (ae) -> {
			
			String[] shiftTime = btnTarget.getId().split(" ");
			Shift newShift = new Shift(Day.getDay(shiftTime[0]), Period.getPeriod(shiftTime[1]),
					cbPeople.getValue(), DataManager.INSTANCE.getPhone(cbPeople.getValue()), 
					cbMeals.getValue(), DataManager.INSTANCE.getIngredients(cbMeals.getValue()));
			
			RotaManager.addShift(newShift, btnTarget);
			
			sm.remove(sm.clashes(newShift));
			sm.add(newShift);
			
			shiftStage.close();
		});
		
		rootNode.getChildren().add(btnConfirm);
		
		shiftStage.show();
	}

}
