import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


public class Runner {
	public static void main(String [] args){
		Properties prop = new Properties();
		File file = new File("D://properties.config");
		FileReader reader;
		try {
			reader = new FileReader(file);
			prop.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collection<WikipediaDocument> docs = new ArrayList<WikipediaDocument>();
		
		Parser WikidumpParser = new Parser(prop);
		//System.out.println(prop.getProperty("dump.filename"));
		WikidumpParser.parse(prop.getProperty("dump.filename"), docs); 
	}
}
