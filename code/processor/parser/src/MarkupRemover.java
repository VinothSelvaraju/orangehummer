
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;



public class MarkupRemover {
	
	public static String toUtcDate(String dateStr) {
		if (dateStr.isEmpty()) {
			return dateStr;
		} else {
			SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
			for (k = 0; k<s.length; k++) {
				for(int m =0; m < monthss.length;m++){
					if(s[k].toLowerCase().contains(monthss[m])){
						flag = true;
					}	
				}
				if ((s[k].matches("[0-9]+") || flag ==true) && j <= 2) {
					flag = false;
					if (j == 0) {
						year = s[k];	
					} else if (j == 1) {
						month = s[k];		
					} else if (j == 2) {
							day = s[k];
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
}
