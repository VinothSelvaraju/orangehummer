package settlementparser;
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
				Boolean officialNameFlag = false;
				Boolean emptyFlag = false;
				
				//Flag if the key is any of the following inside the hash map
				while (itr1.hasNext()) {
					Map.Entry pairs = (Map.Entry) itr1.next();
					String inputKey = pairs.getKey().toString().toLowerCase().trim();
					inputKey = inputKey.replaceAll("_","");
					if(inputKey.contentEquals("name")){
						nameFlag = true;
					
					}
					else if(inputKey.contentEquals("officialname")){
						officialNameFlag = true;
					}
					else if(inputKey.contains("establisheddate")){
						if(pairs.getValue().toString().trim().isEmpty()){
							emptyFlag = true;
						}
					}
				}
				if((nameFlag == true || officialNameFlag == true) && emptyFlag == false){
					Element document = doc.createElement("doc");
					rootElement.appendChild(document);
					Element field1 = doc.createElement("field");
					field1.appendChild(doc.createTextNode(Integer.toString(page.getId())));
					field1.setAttribute("name", "id");
					document.appendChild(field1);
					Element field2 = doc.createElement("field");
					field2.appendChild(doc.createTextNode("places"));
					field2.setAttribute("name", "type");
					document.appendChild(field2);
					
					System.out.println("-----------------Page start---------------------");
					Iterator<Entry<String, String>> itr2 = page.infobox.entrySet().iterator();
					while (itr2.hasNext()) {
						Map.Entry pairs = (Map.Entry) itr2.next();
						/*if(pairs.getKey().toString().trim().equals("occupation")){
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
						}*/
		
							Boolean flag = false;
							String newKey = pairs.getKey().toString().replaceAll("_", "");
							newKey = newKey.trim().replaceAll(" *", "");
							String[] matchTypes = { "imageseal", "blank1name","governmentfootnotes", "elevationft","establisheddate1", "elevationfootnotes","postalcodetype", "elevationfootnotes",
									"populationmetro", "areafootnotes","totaltype", "settlementtype", "blankname", "imageseal","mapcaption", "caption", "imagemap","governingbody", "popestasof",
									"populationdemonym", "utcoffset1dst","timezone1dst", "elevationm", "imageflag",	"arealandsqmi", "areametrosqmi","areacode", "subdivisiontype1",
									"subdivisiontype2", "subdivisiontype","utcoffsetdst", "populationasof",	"leadertitle", "subdivisiontype4","subdivisiontype3", "accessdate",
									"blank1info", "pushpinlabel", "blankinfo","establishedtitle", "imageskyline","utcoffset1", "populationblank1title","governmenttype", "county",
									"subdivisionname4", "subdivisionname3","pushpinmapcaption", "unitpref",	"populationrank", "imagesize","coordinatesfootnotes",
									"establishedtitle1", "areawatersqmi","pushpinmap", "coordinatestype","coordinatesdisplay", "leadertitle1","areatotalkm2", "areawaterpercent",
									"timezonedst", "populationurban","populationdensitykm2","coordinatesregion", "arealandkm2", "utcoffset", "mapsize",
									"areawaterkm2", "areamagnitude","mapcaption1", "establishedtitle3","establishedtitle2", "populationfootnotes",	"latitude", "leadertitle2", "leadername1",
									"leadername3", "leadername4","leadername2", "establisheddate2","pushpinlabelposition", "leadertitle4",	"leadertitle3", "founder",
									"populationnote", "mottoeng", "twin3","twin2", "twin2country", "twin1","twin3country", "blankinfosec1","blanknamesec1", "twin1country",
									"blank1namesec1", "blank1infosec1",	"longitude", "populationblank2title","imagemap1", "establisheddate3", "demonym",	"twin4", "sister-cities", "zipcode",
									"elevationminft", "leaderparty","namedfor", "elevationmaxft","populationest", "populationblank1","coordinatesformat", "areaurbansqmi",
									"areaurbankm2", "populationblank2","mapsize1","pushpinrelief","pushpinmapalt","areacodetype","imagealt","leadername6","leadertitle6",
									"majorlanguages","founded","blankemblemsize","imageblankemblem","nativenamelang","governmentbody","arealandmi2","areatotalmi2",
									"areawatermi2",	"blank3info","blank2info","blank3name","populationinternational","blank2name","areametrokm2","bridges",
									"imageshield","populationdensitymetrosqmi",	"populationdensitymetrokm2","blankemblemtype","p5","p4","p3","p2","p1",	"partstype",
									"blank4name","blank4info","blank3name","urbanpopulation","urbanlandsqmi","partsstyle","airportcode","mayor","imagedotmap","dotmapcaption"};
							String tagName = newKey.toLowerCase();
							for(int k = 0; k<matchTypes.length;k++){
								if(matchTypes[k].equals(tagName)){
									flag = true;
									System.out.println("MATCHED: "+ tagName);
								}
							}
							
							//Matches all key fields if there is a name field
							if(flag!=true && nameFlag == true){
								String inputKey = pairs.getKey().toString().toLowerCase().trim().replaceAll("_", "");
								inputKey = inputKey.replaceAll(" *", "");
								inputKey = synonymlookup(inputKey);
								Element field = doc.createElement("field");
								field.appendChild(doc.createTextNode(pairs.getValue().toString().trim()));
								field.setAttribute("name", inputKey);
								document.appendChild(field);
								System.out.println(inputKey+"===="+pairs.getValue().toString().trim());
							}
							
							//Matches all key fields if there is an official name field but not name field
							else if(flag!=true && nameFlag == false && officialNameFlag == true){
								String inputKey = pairs.getKey().toString().toLowerCase().trim().replaceAll("_", "");
								inputKey = inputKey.replaceAll(" *", "");
								inputKey = synonymlookup(inputKey);
								if(inputKey.contains("officialname")){
									Element field = doc.createElement("field");
									field.appendChild(doc.createTextNode(pairs.getValue().toString().trim()));
									field.setAttribute("name", "name");
									document.appendChild(field);
									System.out.println(inputKey+"===="+pairs.getValue().toString().trim());
								}
								else{
									Element field = doc.createElement("field");
									field.appendChild(doc.createTextNode(pairs.getValue().toString().trim()));
									field.setAttribute("name", inputKey);
									document.appendChild(field);
									System.out.println(inputKey+"===="+pairs.getValue().toString().trim());

								}
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
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
	}
}
