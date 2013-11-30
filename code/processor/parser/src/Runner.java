
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Runner {
	public static void main(String [] args){
		System.out.println("PROGRAM START - TIME: "+Long.toString(System.currentTimeMillis()));
		if(args[0].isEmpty()){
			try {
				throw new Exception("PROPERTIES CONFIG FILE PATH MISSING!!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Properties prop = new Properties();
		File file = new File(args[0]);
		FileReader reader;
		try {
			reader = new FileReader(file);
			prop.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File xmlFile = new File(prop.getProperty("output_file"));
		if (xmlFile.exists()){
			xmlFile.delete();
		}
		Collection<MyXMLPage> docs = new ArrayList<MyXMLPage>();
		
		Parser WikidumpParser = new Parser(prop);
		WikidumpParser.parse(prop.getProperty("input_file"), docs); 
	}
}