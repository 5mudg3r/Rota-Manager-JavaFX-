package me.smudja;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class MenuHandler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		String name = ((MenuItem)event.getTarget()).getText();
		
		switch(name) {
			case "Exit": Platform.exit();
						 break;
			case "New":	 RotaManager.rootNode.setTop(RotaManager.createMenuBar());
						 RotaManager.rootNode.setCenter(RotaManager.createGridPane());
						 RotaManager.primaryStage.show();
			default:	 break;
		}
	}

}
