

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nikhillo This class implements Wikipedia markup processing. Wikipedia
 *         markup details are presented here:
 *         http://en.wikipedia.org/wiki/Help:Wiki_markup It is expected that all
 *         methods marked "todo" will be implemented by students. All methods
 *         are static as the class is not expected to maintain any state.
 */
public class WikipediaParser {
	
	static String[] texturlink = new String [2] ;
	/* TODO */

	/**
	 * Method to parse section titles or headings. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * 
	 * @param titleStr
	 *            : The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	public static String parseSectionTitle(String titleStr) {

		if (titleStr == null) {
			return null;
		} else if (titleStr == "") {
			return ("");
		} else {
			Pattern pattern = Pattern.compile("==+([^==+]*)==+",
					Pattern.DOTALL);
			Matcher matcher = pattern.matcher(titleStr);
			while (matcher.find()) {
				String str = matcher.group(0);
				str = str.replaceAll("\\s*==+\\s*", "");						
				return(str);
			}
			return null;
		}
			}

	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * 
	 * @param itemText
	 *            : The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		
		if (itemText == null) {
			return null;
		} else if (itemText == "") {
			return ("");
		} else {
			/*Pattern pattern = Pattern.compile("(^\\*+|#+|;|:)(.+)$");
			Matcher matcher = pattern.matcher(itemText);
			while (matcher.find()) {
				String str = matcher.group(0);
				str = str.replaceAll("(^\\*+|#+|;|:)\\s", "");
				return(str);
		} 
		return null;*/
			String text1 = itemText.replaceAll("(?m)^[*:#]+\\s*", "");
			return(text1.trim());
	}
}
		

	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		
		if (text == null) {
			return null;
		} else if (text == "") {
			return ("");
		} else {
			String regex1 = "'{2,}";
			String text1 = text.replaceAll(regex1, "");
			return(text1.trim());

		} 
		
		
}

	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz> For most
	 * cases, simply removing the tags should work.
	 * 
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String text) {
		if (text == null) {
			return null;
		} else if (text == "") {
			return ("");
		} else {
			String regex1 = "<.*?> ?";
			String regex2 ="&lt;(.+?)&gt;";
			String text1 = text.replaceAll(regex1, "");
			String textOut = text1.replaceAll(regex2, "");
			return(textOut.trim());
		}
	}

	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags For
	 * most cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {
		
		if (text == null) {
			return null;
		} else if (text == "") {
			return ("");
		} else {
			StringBuilder output = new StringBuilder();
			char[] input = text.toCharArray();
			int braceLevel = 0;
			for (int i = 0; i < input.length; i++) {
			   final char character = input[i];
			   if (character == '{') {
			      // Check for {{
			      if (i < input.length - 1 && input[i+1] == '{') {
			         braceLevel++;
			         i++;
			         continue;
			      }
			   }
			   else if (character == '}' && braceLevel > 0) {
			      // Check for a closing brace
			      if (i < input.length - 1 && input[i+1] == '}') {
			         braceLevel--;
			         i++;
			         continue;
			      }
			   }

			   if (braceLevel == 0) {
			      output.append(character);
			   }
			}
			
			return(output.toString());

		
		}
	}

	/* TODO */
	/**
	 * Method to parse links and URLs. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return An array containing two elements as follows - The 0th element is
	 *         the parsed text as visible to the user on the page The 1st
	 *         element is the link url
	 */
	public static String[] parseLinks(String text) {
		
		String str[] = {"",""};
			
		String regex1 = "\\[\\[(.+?)\\]\\]";
		String regex2 = "\\[http:(.+)\\]";
		
		//Test harness
		//String x = "[[public transport]]ation";
		//System.out.println(x.matches("\\w*?\\[\\[(.*?)\\]\\]\\w*?")+"this");
				
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Pattern pspace = Pattern.compile("\\s");
		
		if((text == "") | (text == null)){
			
			return(str);
		}
		else{
				Matcher matcher1 = pattern1.matcher(text);
				Matcher matcher2 = pattern2.matcher(text);
				
						
				while(matcher1.find()){
										
					// Replace the [[..]] part with the display text we extarcted
					text = text.replaceAll(regex1, getLinkText(matcher1.group()));
					

					if(text.contains("<nowiki />")){
						text = text.replaceAll("<nowiki />", "");
					}
					
				}
				while(matcher2.find()){
										
						String[] items = pspace.split(matcher2.group());
						if(items.length ==1){
							return(str);
						}
						else{
							
							String[] temp = {"",""};
							temp[0] = matcher2.group().replaceAll("\\[http://(.+)\\s+|\\]", "");
							return(temp);
						}
					
					
				}
			texturlink[0] = text;
			return(texturlink);
			}
		
		
	}		
	
	public static String getLinkText(String rawText){
		
		
		Pattern p1 = Pattern.compile("\\[\\[(.*)\\]\\]");// for finding a link
		Matcher m1 = p1.matcher(rawText);    
		
		Pattern p3 = Pattern.compile("\\s");  // for splitting by whitespace 
		
		String linkpart = new String();
		
		while(m1.find()){
			
			linkpart = m1.group();
						
			//CASE 2, 15 & 10
			if(linkpart.matches("\\[\\[(.*?)\\|(.+)\\]\\]") && !linkpart.contains("File:wiki.png")){
				
				String workgroup = new String(m1.group(1));
				
				Pattern p = Pattern.compile("\\|");
				String items[] = p.split(workgroup);
				//Above can used for the first if too, if there arises a case where replaceAll will be difficult to implement
				
				if(linkpart.matches(".+:.+\\|.+")){
										
					workgroup = workgroup.replaceAll("(.*?):(.*?)\\|", "").trim();
					texturlink[0] = workgroup;
					texturlink[1] = "";
				
				}
				
				else if(items[0].contains(" ")){
						Pattern plocal = Pattern.compile("\\s");
						String workgroup1 = items[0];
						String[] items1 = plocal.split(workgroup1);
						
						String temp = "";
						for(int i = 0; i < items1.length; i++){
							if(i==0){
								char[] stringArray = items1[i].trim().toCharArray();
								stringArray[0] = Character.toUpperCase(stringArray[0]);
								items1[i] = new String(stringArray);
								temp = temp.concat(items1[i]);
																
							}
							else{
								temp = temp.concat("_").concat(items1[i]);
							}
							
							texturlink[0] = items[1];
							texturlink[1] = temp;
						}
					}
				
					else{
						for(int i = 0; i< items.length; i++){
					
								if(i==0){
								texturlink[1] = items[i].replaceAll("\\[\\[\\s*", "");
								}
						
							else{
								texturlink[0] = items[i].replaceAll("\\]\\]", "");
								

							}
					
				
					}
				}
		} 
			
			//CASE 3 REVIEW THIS CASE
			else if(linkpart.matches("\\[\\[[a-zA-Z_0-9|\\s]*\\]\\]")){
				
				//if(linkpart.matches("\\[\\[(.*?)\\((.*?)\\)\\|\\]\\]")){
					
					String[] items1 = p3.split(m1.group(1));
					String temp ="";
					for(int i = 0; i < items1.length; i++){
						if(i==0){
							char[] stringArray = items1[i].trim().toCharArray();
							stringArray[0] = Character.toUpperCase(stringArray[0]);
							items1[i] = new String(stringArray);
							temp = temp.concat(items1[i]);
														
						}
						else {
								temp = temp.concat("_").concat(items1[i]);
								
							}
						
						
					}
					texturlink[1] = temp;
					texturlink[0] = m1.group(1);
				}
			
				//Case nowiki
				else if(linkpart.matches("\\[\\[(\\w+)-\\]\\]")){
						
						
						String[] items = p3.split(m1.group());
						String temp2 = "";
						
						String workgroup = m1.group(1);
						if(items.length == 1){
							
							
							char[] stringArray = workgroup.trim().toCharArray();
							stringArray[0] = Character.toUpperCase(stringArray[0]);
							temp2 = new String(stringArray);
						}
						else{
							
							for(int i = 0; i < items.length; i++){
								
								if(i==0){
										
									char[] stringArray = items[i].trim().toCharArray();
									stringArray[0] = Character.toUpperCase(stringArray[0]);
									temp2 = new String(stringArray);
								}
								else{
									
									temp2 = temp2.concat("_").concat(items[i]);
								}
							}
					
						}
						texturlink[0] = m1.group(1);
						texturlink[1] = temp2;
						
				}
				// CASE 4
				else if(linkpart.matches("\\[\\[(.*?)\\((.+)\\)\\|\\]\\]") && !(linkpart.matches("\\[\\[Wikipedia:(.*?)"))){
					
					String workgroup = m1.group(1).replaceAll("\\|", "");
					String[] items2 = p3.split(workgroup);
					String temp1 = "";
					String temp2 = "";
					for(int i = 0; i < items2.length; i++){
						
						if(i==0){
								
							char[] stringArray = items2[i].trim().toCharArray();
							stringArray[0] = Character.toUpperCase(stringArray[0]);
							temp2 = new String(stringArray);
							
							
						}
						
						else{
							
							temp2 = temp2.concat("_").concat(items2[i]);
						}
					}
						for(int j=0; j< items2.length-1; j++){
							
							if(j==0){
								temp1 = temp1.concat(items2[j]);
							}
							else{
								temp1 = temp1.concat(" ").concat(items2[j]);
							}
					}

					texturlink[1] = temp2;
					texturlink[0] = temp1;
				}
			
				// CASE 5
				else if(linkpart.matches("\\[\\[(.*?),(.*?)\\|\\]\\]")) { //[[Seattle, Washinton]]
										
					String workgroup = m1.group(1).replaceAll("\\|", "");
					String[] items3 = p3.split(workgroup);
					
					String temp1 = "";
					String temp2 = "";
					for(int i = 0; i < items3.length; i++){
						
						if(i==0){
							
							if(items3[i].contains(",")){
								temp1 = temp1.concat(items3[i]).replaceAll(",", "");
							}
							else{
								temp1 = temp1.concat(items3[i]);
							}
							
							char[] stringArray = items3[i].trim().toCharArray();
							stringArray[0] = Character.toUpperCase(stringArray[0]);
							items3[i] = new String(stringArray);
							temp2 = temp2.concat(items3[i]).concat("_");
							
							
						}
						else if(i == items3.length-1){
							
							temp2 = temp2.concat(items3[i]);
							
						}
						else{
							if(items3[i].contains(",")){
								temp1 = temp1.concat(items3[i]).replaceAll(",", "");
							}
							else{
								
								temp1 = temp1.concat(items3[i]);
							}
							
							temp2 = temp2.concat(items3[i]);
							
							
						}
						
					}
					texturlink[1] = temp2;
					texturlink[0] = temp1;
				}
				
				//CASE 6 & 7
				else if(linkpart.matches("\\[\\[Wikipedia:(.*?)\\|\\]\\]")){
					
					String workgroup = new String(m1.group(1));
					
					if(linkpart.matches("\\[\\[Wikipedia:([\\w ]+)#([\\w ]+)\\|\\]\\]")){
						
						texturlink[0] = workgroup.replaceAll("\\|", "");
						texturlink[1] = "";
					}
					else if(linkpart.matches("\\[\\[Wikipedia:(.*?)\\(.*\\)\\|\\]\\]")){
						
						
						workgroup = workgroup.replaceAll("Wikipedia:|\\(.*\\)|\\|","").trim();
						
						texturlink[0] = workgroup;
						texturlink[1] = "";
					}
					else {

						workgroup = workgroup.replaceAll("Wikipedia:|\\|", "");
						
						
						texturlink[1] = "";
						texturlink[0] = workgroup;
						
					}
				}	
			
				//CASE 8 & 9
				else if(linkpart.matches("\\[\\[Wiktionary:(.*?)\\]\\]")){
				
					
					String workgroup = new String(m1.group(1));
					if(linkpart.matches("\\[\\[Wiktionary:(\\w{2,}):(.*?)\\|\\]\\]")){
						
						workgroup = workgroup.replaceAll("Wiktionary:|\\|", "").trim();
						
						texturlink[0] = workgroup;
						texturlink[1] = "";
					}
					else{
						texturlink[0] = workgroup;
						texturlink[1] = "";
					}
				}
				
				//CASE 11 ,12 & 13
				
				else if(linkpart.matches("\\[\\[File:wiki.png(.*?)\\]\\]")){
					
					String workgroup = new String(m1.group(1));
					workgroup = workgroup.replaceAll("File:wiki.png", "");
					Pattern pbar = Pattern.compile("\\|");
					String strOut = new String();
					if(workgroup.isEmpty()){
						texturlink[0] = "";
						texturlink[1] = "";
					}
					else{
						String[] items3 = pbar.split(workgroup);
						for(int i = 0; i< items3.length; i++){
							if(i== items3.length -1){
								strOut = items3[i];
							}
							else
								continue;
						}
						texturlink[0] = strOut;
						texturlink[1] = "";
					}
						
						
				}
				//CASE 16, 17, 18
				else if(linkpart.matches("\\[\\[Category:(.+?)\\]\\]")){
					String workgroup = m1.group(1);
					workgroup = workgroup.replaceAll("Category:", "");
					
					texturlink[0] = workgroup;
					texturlink[1] = "";
				}
				else if(linkpart.matches("\\[\\[:Category:(.+?)\\]\\]")){
					String workgroup = m1.group(1);
					workgroup = workgroup.replaceFirst(":", "");
					
					texturlink[0] = workgroup;
					texturlink[1] = "";
				}
				else if(linkpart.matches("\\[\\[[a-zA-Z]{2}:.+\\]\\]")){
									
					String workgroup = m1.group(1);
										
					texturlink[0] = workgroup;
					texturlink[1] = "";
					
				}
				
					
			}	
		
		
		return(texturlink[0]);
	}
	
	//Add document in WikiPediaDocument object 
	
	public static WikipediaDocument wikipediaDocumentGenerator(MyXMLPage xmlPage){
		WikipediaDocument wikiDoc;
			try {
				wikiDoc = new WikipediaDocument(xmlPage.getId(), xmlPage.getPublishDate(), xmlPage.getAuthor(), xmlPage.getTitle());
				if(xmlPage.section!=null){
					for(String[] str:xmlPage.section){
						wikiDoc.addSection(str[0], str[1]);
					}
				}
				//wikiDoc.addCategories(xmlPage.categories);
				//wikiDoc.addLInks(xmlPage.links);
			return wikiDoc;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	
	
	
	
	
	
	
	
	
}				