
import java.io.BufferedWriter;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
	boolean page=false;
	int temp;
	StringBuffer sb=new StringBuffer();
	StringBuffer sbInfoBox=new StringBuffer();
	MyXMLPage xmlPage;
	public ArrayList<WikipediaDocument> XMLPageCollection = new ArrayList<WikipediaDocument>();
	
	File file = new File("D:\\infobox.xml");
	FileWriter fw;
	BufferedWriter bw;
	

	public String markupRemover(String input){
    
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
			new ParserDelegator().parse(new StringReader(html), callback, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

        /*System.out.println(input);
        System.out.println("---------------------------");
        System.out.println(html);
        System.out.println("---------------------------");
        System.out.println(cleaned);*/
        return cleaned.toString();
    }
	
	
	
	public void xmlWriter(HashMap<String,String> hMap){
		 
			  try {	
		 
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				File xmlFile = new File("D:\\Infoboxinxml.xml");
				Document doc = null;
				Element rootElement = null;
				
				if (xmlFile.exists())
				{
					try {
						doc = docBuilder.parse(xmlFile);
						rootElement = doc.getDocumentElement(); //gets the root element
						rootElement.normalize(); 
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 										//creates the document based on an existing XML file
															//and normalizes it
				}
				else
				{
					doc = docBuilder.newDocument(); 		//creates an empty document
					rootElement = doc.createElement("add"); //create the root element
					doc.appendChild(rootElement); 			//and append it to the newly created document
				}
				
				// root elements
				//Document doc = docBuilder.newDocument();
				//Element rootElement = doc.createElement("add");
				//doc.appendChild(rootElement);
		 
				// document elements
				Element document = doc.createElement("doc");
				rootElement.appendChild(document);		
				
				Iterator<Entry<String, String>> itr1 = hMap.entrySet().iterator();
				System.out.println("-----------------Page start---------------------");
			    while (itr1.hasNext()) {
			        Map.Entry pairs = (Map.Entry)itr1.next();
			        System.out.println(pairs.getKey() + "=== " + pairs.getValue());
			        //itr1.remove(); // avoids a ConcurrentModificationException
			        //String s = pairs.getKey().toString();
			        Element field = doc.createElement("field");
			        field.appendChild(doc.createTextNode(pairs.getValue().toString()));
			        field.setAttribute("name", pairs.getKey().toString());
					document.appendChild(field);
			        
			    }
				
			    System.out.println("-------------------Page stop------------------------");
	    
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("D:\\Infoboxinxml.xml"));
		 
				// Output to console for testing
				//StreamResult result = new StreamResult(System.out);
		 
				transformer.transform(source, result);
		 
				System.out.println("File saved!");
		 
			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
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
		
		if (arg1.equals("page"))
		{
			xmlPage = new MyXMLPage();
			page=true;
		}
		if (arg1.equals("title")) {
			
			title = true;
		}
		if (arg1.equals("id")) {
			
			temp=temp+1;
			id = true;
		}
		if (arg1.equals("username") || arg1.equals("ip") ) {
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
		if(page){
			page=false;
		}
		if (title) {
			xmlPage.setTitle(new String(arg0, arg1, arg2));
			title = false;
		}
		if (id && temp==1) {	
			xmlPage.setiD(new String(arg0, arg1, arg2));
			id = false;
		}
		if (conFlag) {
			xmlPage.setAuthor(new String(arg0, arg1, arg2));
			conFlag = false;
		}
		if (text) {
			sb.append(arg0,arg1,arg2);
			xmlPage.setText(sb.toString());
			
			int count = 0;
			String str = new String(arg0,arg1,arg2);
			if (str.equals("{{Infobox musical artist")){
				count++;
				System.out.println("Foundd");
			}
			//System.out.println(str.trim() + "*********");
			//System.out.println("////////////");
			
			
		}
		if (timeStamp) {
			xmlPage.setPublishDate(new String(arg0, arg1, arg2));
			timeStamp = false;
		}
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		if(arg1=="text")
		{
			text=false;
		}
		if(arg1=="page")
		{
			temp=0;
			xmlPage.setText(sb.toString());
			
			
			
			String regex = "\\{\\{Infobox person(.*)\\}\\}";
			Pattern p1 = Pattern.compile(regex,Pattern.DOTALL);
			Matcher m1 = p1.matcher(xmlPage.getText());
			String workgroup = "";
			while(m1.find()){
				workgroup = m1.group();
			}
			//System.out.println(workgroup);
			
			
			StringBuffer sbTemp = new StringBuffer();
			// read char by char
			char[] ch=workgroup.toCharArray();
			String[] str=workgroup.split("}}");
			int i =0;
		
			int count = 0;
			int j = 0;
			boolean flag = false;
			StringBuffer newStringBuf = new StringBuffer();
			while(j <= (ch.length-1)){
				if(ch[j]=='{' && ch[j+1] == '{' && flag == false){
					count++;
					if(count ==0){
						flag = true;
					}
				}
				if((j+1) < ch.length){
					if(ch[j]=='}' && ch[j+1] == '}'){
						count--;
						if(count == 0){
							flag = true;
						}
					}
				}
				//System.out.println("COUNT: "+Integer.toString(count));
				if(count > 0){
					newStringBuf.append(ch[j]);
					
				}
				j++;
			}
			String rem1 = this.markupRemover(newStringBuf.toString());
			//String markedupText = rem1.replace("|", "");
			String [] entry = rem1.split(" \\| ");
			HashMap<String,String> entryMap = new HashMap<String,String>();
			
			int itr = 0;
			try {
				while(itr<entry.length){
					bw.append(entry[itr]);
					entry[itr] = entry[itr].replaceAll("\\{\\{Infobox person", "");
					//System.out.println(entry[itr] + "*************");
					
					String[] splitKeyValue = entry[itr].split("=");
					//System.out.println(splitKeyValue[0]);
					if(splitKeyValue.length == 2){
						entryMap.put(splitKeyValue[0], splitKeyValue[1]);
						//System.out.println(splitKeyValue[0]+":"+splitKeyValue[1]);
					}
					bw.append("\n");
					//System.out.println(entry[itr]);
					itr++;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			xmlWriter(entryMap);
			//Iterate over the map to print the key value pair
			 Iterator<Entry<String, String>> itr1 = entryMap.entrySet().iterator();
			    while (itr1.hasNext()) {
			        Map.Entry pairs = (Map.Entry)itr1.next();
			        //System.out.println(pairs.getKey() + " = " + pairs.getValue());
			        //itr1.remove(); // avoids a ConcurrentModificationException
			    }
			
		
			//xmlPage.categories=parseCatogory(listFormating);
			//xmlPage.links.addAll(collectionLinks);
			WikipediaDocument wikiDoc=WikipediaParser.wikipediaDocumentGenerator(xmlPage);
			XMLPageCollection.add(wikiDoc);
				
			sb= new StringBuffer("");
			page=false;
		}
	}
	@Override
	public void endDocument() throws SAXException {
		//System.out.println("XML Parsing ends!!!");
	}
}