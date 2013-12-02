package filmparser;

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
	public ArrayList<MyXMLPage> XMLPageCollection = new ArrayList<MyXMLPage>();
	Properties props;


	public XMLContentHandler(Properties props) {
		this.props = props;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("PARSING STARTS");
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,	Attributes arg3) throws SAXException {

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
			//System.out.println("AFTER SAX PARSING: "+xmlPage.getText());
			
			String regex = "\\{\\{Infobox film(.*)\\}\\}";
			Pattern p1 = Pattern.compile(regex, Pattern.DOTALL);
			Matcher m1 = p1.matcher(xmlPage.getText());
			String workgroup = "";
			while (m1.find()) {
				workgroup = m1.group();
			}
			
			//System.out.println("AFTER 1st infobox: "+workgroup);
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
			
			Pattern p2 = Pattern.compile("(?i)\\{\\{Infobox film(.*)\\}X", Pattern.DOTALL);
			Matcher m2 = p2.matcher(afterExtract);
			String workgroup1 = "";
			while (m2.find()) {
				workgroup1 = m2.group(1);
			}
			afterExtract = workgroup1;
			
			System.out.println("AFTER PARSING: "+afterExtract);
			
			MarkupRemover mk = new MarkupRemover();
			String formattedText = mk.markupRemover(afterExtract);
			//System.out.println("AFTER FORMATTING MARKUP REMOVAL: "+formattedText);
			formattedText = mk.unwantedTextRemoval(formattedText);
			//System.out.println("AFTER FORMATTING UNWANTED TEXT: "+formattedText);
			formattedText = mk.parseBirthdate(formattedText);
			//System.out.println("AFTER FORMATTING BIRTHDATE: "+formattedText);
			formattedText = mk.parseDeathdate(formattedText);
			//System.out.println("AFTER FORMATTING DEATHDATE: "+formattedText);
			formattedText = mk.parseAwards(formattedText);
			//System.out.println("BEFORE PARSING BRACKETS: "+formattedText);
			
			formattedText =mk.parsePartner(formattedText);
			//System.out.println("AFTER FORMATTING PARTNER: "+formattedText);
			formattedText = mk.parseNationality(formattedText);
			System.out.println("AFTER FORMATTING PARTNER: "+formattedText);
			formattedText = mk.parseBrackets(formattedText);
			System.out.println("AFTER FORMATTING TEXT: "+formattedText);
			
			String[] entry = formattedText.split(" *\\| *");
			
			for(int k = 0; k<entry.length;k++){
				System.out.println(entry[k]);
			}
			
			
			HashMap<String, String> entryMap = new HashMap<String, String>();
			int itr = 0;
			while (itr < entry.length) {
				//bw.append(entry[itr]);
				String[] splitKeyValue = entry[itr].split("=");
				if (splitKeyValue.length == 2) {
					//System.out.println("PRINTING KEY VALUE BEFORE ADDING TO MAP: "+splitKeyValue[0].trim() +"====="+splitKeyValue[1].trim());
					if (splitKeyValue[0].trim().length() != 0 && splitKeyValue[1].trim().length() != 0) {
	
						String cleanedValue = splitKeyValue[1].trim();
						int size = cleanedValue.length();
						if(cleanedValue.charAt(0) == ','){
							cleanedValue = cleanedValue.substring(1);
						}
						else if(cleanedValue.charAt(size-1) == ','){
							cleanedValue = cleanedValue.substring(0, size-2);
						}
						entryMap.put(splitKeyValue[0].trim(),cleanedValue);
					}
				}
				//bw.append("\n");
				itr++;
			}
			System.out.println("----------------------------------------------------------------------------------------");
			xmlPage.setInfobox(entryMap);
			XMLWriter xmlWrite = new XMLWriter(props);
			xmlWrite.xmlWriter(xmlPage);
			
			XMLPageCollection.add(xmlPage);
			
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