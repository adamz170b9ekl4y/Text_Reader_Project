package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JFileChooser;

import application.Main;
import application.model.Support_Methods;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ReaderController implements Initializable {
@SuppressWarnings("rawtypes")
@FXML ListView bigText;
@FXML MenuBar menubar;
@FXML Menu menu1;
@FXML TextField input;
@FXML MenuItem open;
@FXML MenuItem Default_css;
@FXML MenuItem Search;
@FXML MenuItem Dark_css;
@FXML FileChooser fileChooser;
@FXML AnchorPane pane;

Scanner s;
File file;
int entry_counter = 0;
int current_position=0;
int matchcase = 0;
String search_input;
static String User_home = System.getProperty("user.home").toString();
public void initialize(URL location,ResourceBundle resources) {
	
}

@SuppressWarnings({ "unchecked" })
public void search(String input,int direction, int Matchcase) {
	if(!input.equals(search_input) || Matchcase != matchcase) {
		search_input = input;
		entry_counter = 0;
		current_position=0;
	}
	if(Matchcase ==0) {
		input = input.toUpperCase();
		matchcase =0;
	}else if(Matchcase == 1) {
		matchcase = 1;
	}
	
	if(entry_counter == 0) {
	for(Object entry: bigText.getItems()) {
		if(Matchcase == 0) {
			String entryText = (String)entry;
			if(entryText.toUpperCase().contains(input)) {
				entry_counter++;
			}	
		}else if(Matchcase == 1) {
			String entryText = (String)entry;
			if(entryText.contains(input)) {
				entry_counter++;
			}	
		}

	}
	if(direction == 1) {
		current_position = entry_counter;
		}
	}
	int count=0;
	if(direction == 0) {
		for(Object entry: bigText.getItems()) {
			String entryText = (String)entry;
			if(Matchcase == 0) {
				if(entryText.toUpperCase().contains(input)) {
					count++;
					if(current_position >= entry_counter) {
						current_position = 0;
					}
					if(count == current_position+1) {
						bigText.scrollTo(entry);
						current_position = count;
						break;
					}
				}
			}else if(Matchcase == 1) {
				if(entryText.contains(input)) {
					count++;
					if(current_position >= entry_counter) {
						current_position = 0;
					}
					if(count == current_position+1) {
						bigText.scrollTo(entry);
						current_position = count;
						break;
					}
				}
			}
		}
	}else if(direction == 1) {
		for(Object entry: bigText.getItems()) {
			String entryText = (String)entry;
			if(Matchcase == 0) {
				if(entryText.toUpperCase().contains(input)) {
					count++;
					if(count == current_position) {
						bigText.scrollTo(entry);
						current_position = count-1;
						break;
					}
				   if(current_position == 0) {
					   current_position = entry_counter;
				   }
				}
		}else if(Matchcase == 1) {
			if(entryText.contains(input)) {
			count++;
			if(count == current_position) {
				bigText.scrollTo(entry);
				current_position = count-1;
				break;
			}
		   if(current_position == 0) {
			   current_position = entry_counter;
		   			}
				}
			}
		}
	}
}

public void callSearch() {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Search.fxml"));
	try {
		Parent parent = loader.load();
		SearchController searchController = loader.getController();
		searchController.setReaderController(this);
		Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Search");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
	} catch (IOException e) {
		e.printStackTrace();
	}	
}
public void SetAppDirectory() {
	JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showOpenDialog(fileChooser);
    if(option == JFileChooser.APPROVE_OPTION){
       File file = fileChooser.getSelectedFile();
       String filepath = file.getAbsolutePath().toString();
       Support_Methods.set_initaldirectory(filepath);   
}
}
public void openfile() {
	File file = new File(User_home+"/AppData/Local/TextReader_app/settings.txt");
	Scanner reader;
	try {
		reader = new Scanner(file);
		String data = reader.nextLine();
		reader.close();
		String arr[]=data.split(",");
		String defaultbook = arr[0];
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(defaultbook));
		Window primaryStage = null;
		file  = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			readfile(file);
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}

public void DarkMode() {
	Main.stage.getScene().getStylesheets().clear();
	Main.stage.getScene().setUserAgentStylesheet(null);
	Main.stage.getScene().getStylesheets().add(getClass().getResource("/application/dark.css").toExternalForm());
	Support_Methods.updateCSS("dark.css");
}

public void DefaultTheme() {
	Main.stage.getScene().getStylesheets().clear();
	Main.stage.getScene().setUserAgentStylesheet(null);
	Main.stage.getScene().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
	Support_Methods.updateCSS("application.css");
}

@SuppressWarnings("unchecked")
public void readfile(File file_input) {
	bigText.getItems().clear();
	FileInputStream fis;
	try {
		fis = new FileInputStream(file_input);
	
	InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
	BufferedReader br = new BufferedReader(isr);

	String line;
	while((line = br.readLine()) != null){
		bigText.getItems().add(Support_Methods.parse_line(line));
	}
	br.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}