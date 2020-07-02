package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Support_Methods {
 static String User_home = System.getProperty("user.home").toString();
 static File file = new File(User_home+"/AppData/Local/TextReader_app/settings.txt");
 public static int Max_line_size = 65;
 public static String parse_line(String input) {
	 String output="";
	 int temp_length=0;
	 if(input.length() > Max_line_size) {
		 StringTokenizer tokens = new StringTokenizer(input," ");
		 while(tokens.hasMoreTokens()) {
			 String token = tokens.nextToken();
			 if(temp_length+token.length() > Max_line_size) {
				 output = output +" "+ token + "\n";
				 temp_length = 0;
			 }else {
				 if(temp_length == 0) {
					 output = output + token;
					 temp_length = token.length();
				 }else {
					 output = output + " " + token;
					 temp_length = temp_length + token.length()+1;			 
				 }
			 }
		 }
	 }else {
		 output = input;
	 }
	 return output;
 }
public static void CreateDirectory() {
 
 Path path = Paths.get(User_home+"/AppData/Local/TextReader_app");
 if(!Files.exists(path)) {
	 try {
		 Files.createDirectories(path);
		 
		 file.createNewFile();
		 FileWriter writer = new FileWriter(User_home+"/AppData/Local/TextReader_app/settings.txt");
		 writer.write("Documents,application.css");
		 writer.close();
	 }catch (IOException e) {
		 e.printStackTrace();
	 }
	 
 }
}
public static void set_initaldirectory(String filepath) {
	Scanner reader;
	try {
		reader = new Scanner(file);
		String data = reader.nextLine();
		reader.close();
		String arr[]=data.split(",");
		String defaultcss = arr[1];
		File newsettings = new File(User_home+"/AppData/Local/TextReader_app/settings.txt");
		try {
			FileWriter writer = new FileWriter(newsettings,false);
			writer.write(filepath+","+defaultcss);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}

}
public static void updateCSS(String cssfile) {
	Scanner reader;
	try {
		reader = new Scanner(file);
		String data = reader.nextLine();
		reader.close();
		String arr[]=data.split(",");
		String filepath = arr[0];
		File newcss = new File(User_home+"/AppData/Local/TextReader_app/settings.txt");
		try {
			FileWriter writer = new FileWriter(newcss,false);
			writer.write(filepath+","+cssfile);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}

}
}
