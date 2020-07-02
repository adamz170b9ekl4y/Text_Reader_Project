package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SearchController {
@FXML TextField label;
@FXML ToggleGroup Direction_group;
@FXML RadioButton Up_button;
@FXML RadioButton Down_button;
@FXML CheckBox MatchCase;
int direction=0;
int Matchcase=0;
private ReaderController readercontroller;
public void setReaderController(ReaderController readercontroller) {
	this.readercontroller = readercontroller;
}

public void search() {
	if (Up_button.isSelected() == true){
		direction = 1;
	}else if (Down_button.isSelected()==true) {
		direction = 0;
	}
	if (MatchCase.isSelected() == true) {
		Matchcase = 1;
	}else if(MatchCase.isSelected()==false) {
		Matchcase = 0;
	}
	readercontroller.search(label.getText(),direction,Matchcase);
	
	
}
}
