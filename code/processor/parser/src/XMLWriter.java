
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class XMLWriter {
	Properties props;
	public XMLWriter(Properties props) {
		this.props = props;
	}
	public String synonymlookup(String input){
		String str = "";
		String modInput = input.replace(" ","_");
		Properties prop = new Properties();
		File file = new File(props.getProperty("lookupconfig_file"));
		FileReader reader;
		try {
			reader = new FileReader(file);
			prop.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(prop.containsKey(modInput)){
			//System.out.println("INPUT VALUE TO LOOKUP"+ modInput);
			str = prop.getProperty(modInput);
			//System.out.println("RETURN VALUE AFTER LOOKUP:"+ str);
		}
		else{
			str = input;
			//System.out.println("FALSE");
		}
		return str;
	}
	public void xmlWriter(MyXMLPage page) {
		if (!page.infobox.isEmpty()) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				File xmlFile = new File(props.getProperty("output_file"));
				Document doc = null;
				Element rootElement = null;
				if (xmlFile.exists()) {
					try {
						doc = docBuilder.parse(xmlFile);
						rootElement = doc.getDocumentElement(); 
						rootElement.normalize();
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					doc = docBuilder.newDocument(); 
					rootElement = doc.createElement("add");
					doc.appendChild(rootElement);
				}
				
				//Check whether the info box contains name tag
				Iterator<Entry<String, String>> itr1 = page.infobox.entrySet().iterator();
				Boolean nameFlag = false;
				while (itr1.hasNext()) {
					Map.Entry pairs = (Map.Entry) itr1.next();
					if(pairs.getKey().toString().toLowerCase().trim().contentEquals("name")){
						nameFlag = true;
						break;
					}
				}
				
				if(nameFlag == true){
					Element document = doc.createElement("doc");
					rootElement.appendChild(document);
					Element field1 = doc.createElement("field");
					field1.appendChild(doc.createTextNode(Integer.toString(page.getId())));
					field1.setAttribute("name", "id");
					document.appendChild(field1);
					Element field2 = doc.createElement("field");
					field2.appendChild(doc.createTextNode("person"));
					field2.setAttribute("name", "type");
					document.appendChild(field2);
					
					System.out.println("-----------------Page start---------------------");
					Iterator<Entry<String, String>> itr2 = page.infobox.entrySet().iterator();
					while (itr2.hasNext()) {
						Map.Entry pairs = (Map.Entry) itr2.next();
						if(pairs.getKey().toString().trim().equals("occupation")){
							String input = pairs.getValue().toString().replaceAll(";|/", ",");
							if(input.contains(",")){
								String [] str= input.split(",");
								for(int i = 0;i<str.length;i++){
									String newKey = pairs.getKey().toString().trim().replaceAll("_", "");
									Element field = doc.createElement("field");
									field.appendChild(doc.createTextNode(str[i].trim()));
									field.setAttribute("name", newKey);
									document.appendChild(field);
									System.out.println(newKey+"===="+str[i].trim());
								}
							}
							else{
								Element field = doc.createElement("field");
								String newKey = pairs.getKey().toString().replaceAll("_", "");
								newKey = newKey.trim().replaceAll(" ", "");
								field.appendChild(doc.createTextNode(pairs.getValue().toString()));
								field.setAttribute("name",newKey);
								document.appendChild(field);
								System.out.println(newKey+"===="+pairs.getValue().toString());
							}
						}
						else{
							Boolean flag = false;
							String newKey = pairs.getKey().toString().replaceAll("_", "");
							newKey = newKey.trim().replaceAll(" *", "");
							String[] matchTypes = { "imagesize", "alt",
									"bgcolour", "term", "honorificsuffix",
									"honorificprefix", "hometown", "signature",
									"wrestlingweight", "abbr", "hangul",
									"module", "mr", "rrborn", "hangulborn",
									"color", "rr", "mrborn", "child", "size",
									"embed", "filename", "description", "work",
									"accessdate", "date", "agent", "origin",
									"eyecolor", "haircolor", "naturalbust",
									"df", "issue", "m", "othernameslang",
									"precision", "criminalstatus",
									"criminalpenalty", "criminalcharge",
									"publisher", "imagesize", "dead", "age",
									"alive", "type", "ft", "in",
									"nativenamelang", "died", "restingplace",
									"deathcause", "signaturealt", "genre(s)",
									"genre", "label", "instrument",
									"associatedacts", "background",
									"tradchinesename", "simpchinesename",
									"pinyinchinesename", "boxwidth",
									"signaturesize", "sexualorientation",
									"measurements", "grandchildren", "label(s)","labels","medium","instruments","death","style","divorced" };
							String tagName = newKey.toLowerCase();
							//System.out.println("TAG NAME: "+tagName);
							for(int k = 0; k<matchTypes.length;k++){
								if(matchTypes[k].equals(tagName)){
									flag = true;
									System.out.println("MATCHED: "+ tagName);
								}
							}
							if(flag!=true){
								String inputKey = pairs.getKey().toString().toLowerCase().trim().replaceAll("_", "");
								inputKey = inputKey.replaceAll(" *", "");
								inputKey = synonymlookup(inputKey);
								Element field = doc.createElement("field");
								field.appendChild(doc.createTextNode(pairs.getValue().toString().trim()));
								field.setAttribute("name", inputKey);
								document.appendChild(field);
								System.out.println(inputKey+"===="+pairs.getValue().toString().trim());
							}	
						}
					}
					System.out.println("-------------------Page stop------------------------");
					
					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File(props.getProperty("output_file")));
					
					// Output to console for testing
					//StreamResult result1 = new StreamResult(System.out);
					transformer.transform(source, result);
					System.out.println("File saved!");
				}
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
	}
}
