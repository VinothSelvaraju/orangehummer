/**
 * 
 */


import java.io.File;
import java.util.Collection;
import java.util.Properties;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class Parser {

	private final Properties props;
	
	
	public Parser(Properties idxProps) {
		props = idxProps;
	}
	
	public void parse(String filename, Collection<WikipediaDocument> docs) {
		XMLContentHandler cd=new XMLContentHandler();
		try{
			//System.out.println("input filename"+filename);
			if(filename==null||filename.isEmpty()){
				return;
			}
			else if(filename!=null){
				File fileTest=new File(filename);
					if(fileTest.exists()){
						XMLReader reader;
						reader = XMLReaderFactory.createXMLReader();
						reader.setContentHandler(cd);
						reader.parse(filename);
						docs.addAll(cd.XMLPageCollection);
					}
					else{
						return;
					}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
