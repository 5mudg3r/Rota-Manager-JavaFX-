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

/**
 * This class handles button click events from {@link me.smudja.RotaManager#primaryStage primaryStage}.
 * It is instantiated in {@link me.smudja.RotaManager#init() init()}.
 * 
 * @author smithl
 * 
 * @see    me.smudja.RotaManager
 */
public class ButtonHandler implements EventHandler<ActionEvent> {
	
	/**
	 *  A reference to {@link me.smudja.ShiftManager}
	 *  This will never instantiate the {@code ShiftManager}.
	 */
	ShiftManager sm = ShiftManager.INSTANCE;

	/** 
	 * This method overrides the {@code handle()} method from {@code EventHandler}.
	 * It creates the new shift stage along with a confirm button.
	 * Once the confirm button is clicked, it adds the new shift to the primary stage and the shift manager.
	 * It then removes any clashing shifts from the shift manager.
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 * @see	me.smudja.RotaManager#addShift(Shift, Button)
	 * @see me.smudja.ShiftManager#add(Shift)
	 */
	@Override
	public void handle(ActionEvent event) {
		
		Button btnTarget = (Button) event.getTarget();
		
		Stage shiftStage = new Stage();
		shiftStage.setTitle("New Shift");
		
		FlowPane rootNode = new FlowPane(10,30);
		
		rootNode.setAlignment(Pos.CENTER);
		
		shiftStage.setScene(new Scene(rootNode, 450, 300));
		
		Label dayLbl = new Label(btnTarget.getId());
		
		Separator separator1 = new Separator();
		separator1.setPrefWidth(400);
		
		rootNode.getChildren().addAll(dayLbl, separator1);
		
		ObservableList<String> people = FXCollections.observableArrayList(DataManager.INSTANCE.getPeople());
		
		ComboBox<String> cbPeople = new ComboBox<String>(people);
		
		if(!people.isEmpty()) {
		cbPeople.setValue(people.get(0));
		}
		
		cbPeople.setPrefWidth(250);
		cbPeople.setMaxWidth(375);
		
		Label lblPeople = new Label("Person: ");
		lblPeople.setPrefWidth(100);
		lblPeople.setAlignment(Pos.CENTER);
		
		rootNode.getChildren().addAll(lblPeople, cbPeople);
		
		ObservableList<String> meals = FXCollections.observableArrayList(DataManager.INSTANCE.getMeals());
		
		ComboBox<String> cbMeals = new ComboBox<String>(meals);
		
		if(!meals.isEmpty()) {
			cbMeals.setValue(meals.get(0));
		}
		
		cbMeals.setPrefWidth(250);
		cbMeals.setMaxWidth(375);
		
		Label lblMeals = new Label("Meal: ");
		lblMeals.setPrefWidth(100);
		lblMeals.setAlignment(Pos.CENTER);
		
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
