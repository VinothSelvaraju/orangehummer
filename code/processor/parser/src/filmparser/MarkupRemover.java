package filmparser;

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
			String[] dateFormats = {"yyyy-MMM-dd", "yyyy-MM-dd", "yyyy hh:mm:ss Z","dd MMM yyyy","MMMM dd, yyyy","MMMM yyyy", "MMM dd",  "yyyy"};
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
			//System.out.println("date "+date);
			date = toUtcDate(date);
			modifiedText = modifiedText.replaceAll("\\{\\{B(.*?)\\}\\}", date);
			modifiedText = modifiedText.replaceAll("\\{\\{b(.*?)\\}\\}", date);	
		}
		Pattern p3 = Pattern.compile("\\| *birth_date *=(.*?)\\|", Pattern.CASE_INSENSITIVE);
		Matcher m3 = p3.matcher(modifiedText);
		String workgroup2 = "";
		while (m3.find()) {
			workgroup2 = m3.group(1).trim();
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
		String regex1 = "\\{\\{Death(.*?)\\}\\}";
		Pattern p2 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
		Matcher m2 = p2.matcher(modifiedText);
		String workgroup1 = "";
		while (m2.find()) {
			workgroup1 = m2.group(1);
			System.out.println(workgroup1);
			//split the death date value around |
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
			//System.out.println("date "+date);
			date = toUtcDate(date);
			//replace the death date value with the computed death date value
			modifiedText = modifiedText.replaceAll("\\{\\{D(.*?)\\}\\}", date);
			modifiedText = modifiedText.replaceAll("\\{\\{d(.*?)\\}\\}", date);
			
		}
		//System.out.println("WITHIN DEATH DATE: "+modifiedText);
		Pattern p3 = Pattern.compile("\\| *death_date *=(.*?)\\|", Pattern.CASE_INSENSITIVE);
		Matcher m3 = p3.matcher(modifiedText);
		String workgroup2 = "";
		while (m3.find()) {
			workgroup2 = m3.group(1).trim();
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
	
	
	public String parseAwards (String modifiedText){
		
		System.out.println("ORIGINAL TEXT BEFORE AWARDS PARSING: "+modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{awd *\\|(.*?)\\|(.*?)\\}\\}\\|*", "$1");
		System.out.println("PARSED TEXT AFTER AWARDS PARSING: "+ modifiedText);
		/*String modifiedText = "";
		Pattern p6 = Pattern.compile("\\{\\{Aw(.*?)\\}\\}", Pattern.CASE_INSENSITIVE);
		Matcher m6 = p6.matcher(text);
		while (m6.find()) {
			modifiedText = m6.group();
			String[] s = modifiedText.split(" *\\| *");
			if(s.length>1){
				String awardName = s[1];
			}
			
		}*/
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
		modifiedText = modifiedText.replaceAll("\\[\\[(.*?)\\]\\]","$1");

		// parsing URL
		modifiedText = modifiedText.replaceAll("\\{\\{URL\\|(.*?)\\}\\}", "$1");
		modifiedText = modifiedText.replaceAll("\\{\\{url\\|(.*?)\\}\\}", "$1");

		//System.out.println("WITHIN UNWANTED REM: "+modifiedText);
		
		// parsing marriage
		System.out.println("ORIGINAL TEXT: "+modifiedText);
		//modifiedText = modifiedText.replaceAll("(?i)\\|*\\{\\{Marriage\\|(.*?)\\|(.*?)\\}\\}\\|*", "   $1");
		//modifiedText = modifiedText.replaceAll("(?i)\\|*\\{\\{Marriage\\|(show=)?(.*?)\\|?(.*?)\\}\\}\\|*", "'"+"$3"+"'");
		
		//Marriage {{}} 
		Pattern p6 = Pattern.compile("(?i)\\|*\\{\\{Marriage\\|(show=)?(.*?)\\|?(.*?)\\}\\}\\|*", Pattern.CASE_INSENSITIVE);
		Matcher m6 = p6.matcher(modifiedText);
		String matcherText = "";
		String fullMatchText = "";
		while (m6.find()) {
			matcherText = m6.group(3);
			fullMatchText = m6.group(0);
			//System.out.println("MATCHER TEXT: "+ matcherText);
			//System.out.println("FULL MATCHER TEXT"+ fullMatchText);
			matcherText = matcherText.replaceAll("//|(.*?)//|", "");
			if(matcherText.contains("|")){
				System.out.println("CONTAINS | FLAG: TRUE");
				String s[] = matcherText.split(" *\\| *");
				s[0]=s[0].trim();
				s[0] = s[0].replaceAll(" *, *", " ");
				System.out.println("S[0]: "+s[0]);
				modifiedText = modifiedText.replace(fullMatchText, s[0]+",");
				System.out.println("MATCHER TEXT AFTER REPLACING | (WITHIN IF): "+ modifiedText);
			}
			else{
			matcherText = matcherText.trim();
			matcherText = matcherText.replaceAll(" *, *", " ");
			System.out.println("MATCHER TEXT AFTER REPLACING |(WITHIN ELSE): "+ matcherText);
			modifiedText = modifiedText.replace(fullMatchText,matcherText+",");
			}
		}		
	
		System.out.println("AFTER Marriage parsing: "+ modifiedText);
		
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{nowrap\\|*(.*?)\\}\\}","$1");
		//System.out.println("AFTER nowrap parsing: "+modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)\\{\\{ubl\\|*(.*?)\\}\\}","$1");
		//System.out.println("AFTER ubl parsing: "+modifiedText);
		modifiedText = modifiedText.replaceAll("(?i)spouse *= *\\{\\{Plainlist\\|(.*?)\\}\\}","spouse = "+"$1");
		//System.out.println("AFTER PLAINLIST: "+modifiedText);
		return modifiedText;
	}
	public String parseBrackets(String formattedText) {
		formattedText = formattedText.replaceAll("\\{\\{(.*?)\\}\\}","");
		return formattedText;
	}
	public String parsePartner(String formattedText) {
		formattedText = formattedText.replaceAll("(?i)partner *= *\\{\\{Plainlist\\|(.*)\\}\\}","partner = "+"$1");
		return formattedText;
	}
	public String parseNationality(String formattedText) {
		formattedText = formattedText.replaceAll("(?i)nationality *= *\\{\\{(.*)\\}\\}","nationality = "+"$1");
		return formattedText;
	}
}
