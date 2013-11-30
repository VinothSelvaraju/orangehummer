import java.io.BufferedWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLContentHandler extends DefaultHandler {
	boolean title = false;
	boolean id = false;
	boolean text = false;
	boolean timeStamp = false;
	boolean conFlag = false;
	boolean page = false;
	int temp;
	StringBuffer sb = new StringBuffer();
	StringBuffer sbInfoBox = new StringBuffer();
	MyXMLPage xmlPage;
	public ArrayList<WikipediaDocument> XMLPageCollection = new ArrayList<WikipediaDocument>();

	File file = new File("D:\\infobox.xml");
	FileWriter fw;
	BufferedWriter bw;

	public String markupRemover(String input) {

		StringWriter writer = new StringWriter();
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
		builder.setEmitAsDocument(false);
		MarkupParser parser = new MarkupParser(new MediaWikiDialect());
		parser.setBuilder(builder);
		parser.parse(input);

		final String html = writer.toString();
		final StringBuilder cleaned = new StringBuilder();

		HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
			public void handleText(char[] data, int pos) {
				cleaned.append(new String(data)).append(' ');
			}
		};
		try {
			new ParserDelegator()
					.parse(new StringReader(html), callback, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cleaned.toString();
	}
	
	
	public String synonymlookup(String input){
		String str = "";
		String modInput = input.replace(" ","_");
		Properties prop = new Properties();
		File file = new File("D://lookupProperties.config");
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
			System.out.println("INPUT VALUE TO LOOKUP"+ modInput);
			str = prop.getProperty(modInput);
			System.out.println("RETURN VALUE AFTER LOOKUP:"+ str);
		}
		else{
			str = input;
			System.out.println("FALSE");
		}
		return str;
	}

	public String parseBirthdate(String modifiedText){
		String regex1 = "\\{\\{B(.*?)\\}\\}";
		Pattern p2 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
		Matcher m2 = p2.matcher(modifiedText);
		String workgroup1 = "";
		while (m2.find()) {
			workgroup1 = m2.group(1);
			System.out.println(workgroup1);
			String s[] = workgroup1.split("\\|");
			int k = 0;
			int j = 0;
			String day = "";
			String month = "";
			String year = "";
			String date = "";
			String[] monthss = {"jan", "feb", "mar","apr", "may", "jun", "jul", "aug", "sep", "oct", "nov","dec"};
			Boolean flag = false;
			for (k = s.length-1; k >= 0; k--) {
				for(int m =0; m < monthss.length;m++){
					if(s[k].toLowerCase().contains(monthss[m])){
						flag = true;
					}	
				}
				if ((s[k].matches("[0-9]+") || flag ==true) && j <= 2) {
					flag = false;
					if (j == 0) {
						day = s[k];	
					} else if (j == 1) {
						month = s[k];		
					} else if (j == 2) {
							year = s[k];
					}
					j++;
				}
			}
			if (!year.equalsIgnoreCase("") && !month.equalsIgnoreCase("") && !day.equalsIgnoreCase(""))
				date = year + "-" + month + "-" + day;
			else
				date = day;
			System.out.println("date "+date);
			date = toUtcDate(date);
			modifiedText = modifiedText.replaceAll("\\{\\{B(.*?)\\}\\}", date);
			modifiedText = modifiedText.replaceAll("\\{\\{b(.*?)\\}\\}", date);	
		}
		Pattern p3 = Pattern.compile("\\| *birth_date *=(.*?)\\|", Pattern.CASE_INSENSITIVE);
		Matcher m3 = p3.matcher(modifiedText);
		String workgroup2 = "";
		while (m3.find()) {
			workgroup2 = m3.group(1);
			Pattern p5 = Pattern.compile("(\\(.*\\))", Pattern.CASE_INSENSITIVE);
			Matcher m5 = p5.matcher(workgroup2);
			String workgroup3 = "";
			
			while (m5.find()) {
				workgroup3 = m5.group(1);
				modifiedText = modifiedText.replace(workgroup3,"");	
				String rem = workgroup2.replace(workgroup3, "").trim();
				modifiedText = modifiedText.replace(rem,toUtcDate(rem));
			}
			Pattern p6 = Pattern.compile("([0-9]*-[0-9]*-[0-9]*T[0-9]*:[0-9]*:[0-9]*Z)", Pattern.CASE_INSENSITIVE);
			Matcher m6 = p6.matcher(workgroup2);
			String workgroup4 = "";
			while (m6.find()) {
				workgroup4 = m6.group();
				modifiedText = modifiedText.replace(workgroup2,workgroup4);
				workgroup2 = workgroup2.replace(workgroup2, workgroup4);
			}
			modifiedText = modifiedText.replace(workgroup2,toUtcDate(workgroup2.trim()));
		}
		return modifiedText;
	}
	
	public String parseDeathdate(String modifiedText){
		String regex1 = "\\{\\{D(.*?)\\}\\}";
		Pattern p2 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
		Matcher m2 = p2.matcher(modifiedText);
		String workgroup1 = "";
		while (m2.find()) {
			workgroup1 = m2.group(1);
			System.out.println(workgroup1);
			String s[] = workgroup1.split("\\|");
			int k = 0;
			int j = 0;
			String day = "";
			String month = "";
			String year = "";
			String date = "";
			String[] monthss = {"jan", "feb", "mar","apr", "may", "jun", "jul", "aug", "sep", "oct", "nov","dec"};
			Boolean flag = false;
			for (k = s.length-1; k >= 0; k--) {
				for(int m =0; m < monthss.length;m++){
					if(s[k].toLowerCase().contains(monthss[m])){
						flag = true;
					}	
				}
				if ((s[k].matches("[0-9]+") || flag ==true) && j <= 2) {
					flag = false;
					if (j == 0) {
						day = s[k];	
					} else if (j == 1) {
						month = s[k];		
					} else if (j == 2) {
							year = s[k];
					}
					j++;
				}
			}
			if (!year.equalsIgnoreCase("") && !month.equalsIgnoreCase("") && !day.equalsIgnoreCase(""))
				date = year + "-" + month + "-" + day;
			else
				date = day;
			System.out.println("date "+date);
			date = toUtcDate(date);
			modifiedText = modifiedText.replaceAll("\\{\\{D(.*?)\\}\\}", date);
			modifiedText = modifiedText.replaceAll("\\{\\{d(.*?)\\}\\}", date);	
		}
		Pattern p3 = Pattern.compile("\\| *death_date *=(.*?)\\|", Pattern.CASE_INSENSITIVE);
		Matcher m3 = p3.matcher(modifiedText);
		String workgroup2 = "";
		while (m3.find()) {
			workgroup2 = m3.group(1);
			Pattern p5 = Pattern.compile("(\\(.*\\))", Pattern.CASE_INSENSITIVE);
			Matcher m5 = p5.matcher(workgroup2);
			String workgroup3 = "";
			
			while (m5.find()) {
				workgroup3 = m5.group(1);
				modifiedText = modifiedText.replace(workgroup3,"");	
				String rem = workgroup2.replace(workgroup3, "").trim();
				modifiedText = modifiedText.replace(rem,toUtcDate(rem));
			}
			Pattern p6 = Pattern.compile("([0-9]*-[0-9]*-[0-9]*T[0-9]*:[0-9]*:[0-9]*Z)", Pattern.CASE_INSENSITIVE);
			Matcher m6 = p6.matcher(workgroup2);
			String workgroup4 = "";
			while (m6.find()) {
				workgroup4 = m6.group();
				modifiedText = modifiedText.replace(workgroup2,workgroup4);
				workgroup2 = workgroup2.replace(workgroup2, workgroup4);
			}
			modifiedText = modifiedText.replace(workgroup2,toUtcDate(workgroup2.trim()));
		}
		return modifiedText;
	}
	
	public String unwantedTextRemoval(String text) {
		String modifiedText = "";
		
		//parsing unwanted text
		modifiedText = text.replaceAll("<ref.*</ref>", "");
		modifiedText = modifiedText.replaceAll("<ref.*?>", "");
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{citation(.*?)\\}\\}","");
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{fact(.*?)\\}\\}","");
		modifiedText = modifiedText.replaceAll("<br />",",");
		modifiedText = modifiedText.replaceAll("<br/>",",");
		modifiedText = modifiedText.replaceAll("<br/ >",",");
		modifiedText = modifiedText.replaceAll("<br>",",");
		modifiedText = modifiedText.replaceAll("</ref>","");
		
		// parsing awards
		//modifiedText = modifiedText.replaceAll("\\{\\{awd\\|(.*?)\\|\\}\\}","XXXXXXXXXXXXX");

		// parsing URL
		modifiedText = modifiedText.replaceAll("\\{\\{URL\\|(.*?)\\}\\}", "$1");
		modifiedText = modifiedText.replaceAll("\\{\\{url\\|(.*?)\\}\\}", "$1");

		// parsing marriage
		System.out.println("ORIGINAL TEXT: "+modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)\\|*\\{\\{Marriage\\|(.*?)\\|(.*?)\\}\\}\\|*", "   $1");
		//System.out.println("AFTER Marriage parsing: "+ modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{nowrap\\|*(.*?)\\}\\}","$1");
		//System.out.println("AFTER nowrap parsing: "+modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{ubl\\|*(.*?)\\}\\}","$1");
		//System.out.println("AFTER ubl parsing: "+modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{Plainlist\\|(.*?)\\}\\}","$1");
	
		// System.out.println(rem1);
		/*
		 * parsing awards!!! Pattern p3 =
		 * Pattern.compile("\\{\\{awd\\|(.*?)\\}\\}", Pattern.CASE_INSENSITIVE);
		 * Matcher m3 = p3.matcher(rem1); String newString1 = "";
		 * ArrayList<HashMap> coll = new ArrayList<HashMap>(); while(m3.find()){
		 * newString1 = m3.group(1); System.out.println("AWARDS: "+newString1);
		 * String [] mapEntryArray = newString1.split(" *\\| *"); int k = 0;
		 * while(k<mapEntryArray.length){ HashMap<String,String> awardMap = new
		 * HashMap<String,String>(); String [] subEntryArray =
		 * mapEntryArray[k].split(" *= *");
		 * awardMap.put(subEntryArray[0].trim(), subEntryArray[1].trim());
		 * System.out.println(subEntryArray[0].trim()+"===="+
		 * subEntryArray[1].trim()); coll.add(awardMap); k++; } }
		 */

		//parsing Birth date
		modifiedText = parseBirthdate(modifiedText);
		
		//parsing Death date
		modifiedText = parseDeathdate(modifiedText);
		
		return modifiedText;
	}

	public static String toUtcDate(String dateStr) {
		if (dateStr.isEmpty()) {
			return dateStr;
		} else {
			SimpleDateFormat out = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");
			String[] dateFormats = {"yyyy-MMM-dd", "yyyy-MM-dd", "yyyy hh:mm:ss Z","dd MMM yyyy","MMMM dd, yyyy","MMMM yyyy", "MMM dd",  "yyyy"
					 };
			for (String dateFormat : dateFormats) {
				try {
					//System.out.println("naen :: "+dateFormat);
					return out.format(new SimpleDateFormat(dateFormat)
							.parse(dateStr));
				} catch (ParseException ignore) {
				}
			}
			return "";
			//throw new IllegalArgumentException("Invalid date: " + dateStr);
		}
	}

	public void xmlWriter(MyXMLPage page) {
		if (!page.infobox.isEmpty()) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				File xmlFile = new File("D:\\Infoboxinxml.xml");
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
									Element field = doc.createElement("field");
									field.appendChild(doc.createTextNode(str[i].trim()));
									field.setAttribute("name", pairs.getKey().toString().trim().replaceAll("_", ""));
									document.appendChild(field);
									System.out.println(pairs.getKey().toString().trim().replaceAll("_", "")+"===="+str[i].trim());
								}
							}
							else{
								Element field = doc.createElement("field");
								field.appendChild(doc.createTextNode(pairs.getValue().toString()));
								field.setAttribute("name", pairs.getKey().toString().replaceAll("_", ""));
								document.appendChild(field);
								System.out.println(pairs.getKey().toString().replaceAll("_", "")+"===="+pairs.getValue().toString());
							}
						}
						else{
							Boolean flag = false;
							String [] matchTypes = {"imagesize",	"alt",	"bgcolour",	"term",	"honorific suffix",	"honorific prefix",	"hometown",	"signature",	"wrestling weight",	"abbr",	"hangul",	"module",	"mr",	"rrborn",	"hangulborn",	"color",	"rr",	"mrborn",	"child",	"size",	"embed",	"filename",	"description",	"work",	"accessdate",	"date",	"agent",	"origin",	"eye color",	"hair color",	"natural bust",	"df",	"issue",	"m",	"othernameslang",	"precision",	"criminal status",	"criminal penalty",	"criminal charge",	"publisher",	"image size",	"dead",	"age",	"alive","type"};
							String tagName = pairs.getKey().toString().toLowerCase().trim().replaceAll("_"," ");
							System.out.println("TAG NAME: "+tagName);
							for(int k = 0; k<matchTypes.length;k++){
								if(matchTypes[k].equals(tagName)){
									flag = true;
									System.out.println("MATCHED: "+ tagName);
								}
							}
							if(flag!=true){
								String inputKey = pairs.getKey().toString().toLowerCase().trim().replaceAll("_", "");
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
					StreamResult result = new StreamResult(new File("D:\\Infoboxinxml.xml"));
					// Output to console for testing
					StreamResult result1 = new StreamResult(System.out);
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

	@Override
	public void startDocument() throws SAXException {
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);

	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {

		if (arg1.equals("page")) {
			xmlPage = new MyXMLPage();
			page = true;
		}
		if (arg1.equals("title")) {

			title = true;
		}
		if (arg1.equals("id")) {

			temp = temp + 1;
			id = true;
		}
		if (arg1.equals("username") || arg1.equals("ip")) {
			conFlag = true;
		}
		if (arg1.equals("timestamp")) {
			timeStamp = true;
		}
		if (arg1.equals("text")) {
			text = true;
		}
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		if (page) {
			page = false;
		}
		if (title) {
			xmlPage.setTitle(new String(arg0, arg1, arg2));
			title = false;
		}
		if (id && temp == 1) {
			xmlPage.setiD(new String(arg0, arg1, arg2));
			id = false;
		}
		if (conFlag) {
			xmlPage.setAuthor(new String(arg0, arg1, arg2));
			conFlag = false;
		}
		if (text) {
			sb.append(arg0, arg1, arg2);
			xmlPage.setText(sb.toString());
		}
		if (timeStamp) {
			xmlPage.setPublishDate(new String(arg0, arg1, arg2));
			timeStamp = false;
		}
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		if (arg1 == "text") {
			text = false;
		}
		if (arg1 == "page") {
			temp = 0;
			xmlPage.setText(sb.toString());
			String regex = "\\{\\{Infobox person(.*)\\}\\}";
			Pattern p1 = Pattern.compile(regex, Pattern.DOTALL);
			Matcher m1 = p1.matcher(xmlPage.getText());
			String workgroup = "";
			while (m1.find()) {
				workgroup = m1.group();
			}
			
			//System.out.println("Before stack"+workgroup);
			
			// read char by char
			char[] ch = workgroup.toCharArray();
			int count = 0;
			int j = 0;
			boolean flag = false;
			StringBuffer newStringBuf = new StringBuffer();
			while (j < ch.length) {
				if (ch[j] == '{' && flag == false) {
					count++;
				}
				else if (ch[j] == '}'&& flag == false) {
					count--;
					if (count == 0) {
						flag = true;
						newStringBuf.append("X");
						break;
					}
				}
				if (count > 0) {
					newStringBuf.append(ch[j]);
				}
				j++;
			}
			String afterExtract = newStringBuf.toString();
			
			Pattern p2 = Pattern.compile("(?i)\\{\\{Infobox person(.*)\\}X", Pattern.DOTALL);
			Matcher m2 = p2.matcher(afterExtract);
			String workgroup1 = "";
			while (m2.find()) {
				workgroup1 = m2.group(1);
			}
			afterExtract = workgroup1;
			
			String formattedText = this.markupRemover(afterExtract);
			formattedText = unwantedTextRemoval(formattedText);
			String[] entry = formattedText.split(" *\\| *");
			HashMap<String, String> entryMap = new HashMap<String, String>();
			int itr = 0;
			try {
				while (itr < entry.length) {
					bw.append(entry[itr]);
					String[] splitKeyValue = entry[itr].split("=");
					if (splitKeyValue.length == 2) {
						// System.out.println(splitKeyValue[0].trim() +"====="+
						// splitKeyValue[1].trim());
						if (splitKeyValue[0].trim().length() != 0
								&& splitKeyValue[1].trim().length() != 0) {
							System.out.println(splitKeyValue[0]+"===="+splitKeyValue[1]);
							entryMap.put(splitKeyValue[0].trim(),splitKeyValue[1].trim());
						}
					}
					bw.append("\n");
					itr++;
				}
				System.out
						.println("----------------------------------------------------------------------------------------");
				xmlPage.setInfobox(entryMap);

			} catch (IOException e) {
				e.printStackTrace();
			}
			xmlWriter(xmlPage);
			/* Iterate over the map to print the key value pair
			System.out.println("PAGE STARTS");
			Iterator<Entry<String, String>> itr1 = xmlPage.getInfobox().entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pairs = (Map.Entry) itr1.next();
				// System.out.println(pairs.getKey() + " ===== " +
				// pairs.getValue());
				// itr1.remove();
			}
			System.out.println("PAGE ENDS");*/

			WikipediaDocument wikiDoc = WikipediaParser.wikipediaDocumentGenerator(xmlPage);
			XMLPageCollection.add(wikiDoc);

			sb = new StringBuffer("");
			page = false;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("PROGRAM ENDS - Time: "
				+ Long.toString(System.currentTimeMillis()));
	}
}