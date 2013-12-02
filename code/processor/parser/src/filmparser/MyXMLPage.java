package filmparser;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MyXMLPage
{
	String title;
	int id;
	String publishDate;
	String author;
	String text;
	HashMap<String,String> infobox;
	String summary;
	public ArrayList<String> categories;
	public ArrayList<String[]> section;
	public Set<String> links;
	
	
	public void setTitle(String str) {
		this.title = str;
		}
	
	public void setSummary(String str) {
		this.summary = str;
		}

	public void setiD(String str) {
		this.id = Integer.parseInt(str);
		}
	public void setAuthor(String str) {
		this.author = str;
		}
	public void setPublishDate(String str) {
		this.publishDate = str;
		}
	public void setText(String str) {
		this.text=str;
		}
	
	public void setInfobox(HashMap<String,String> hMap) {
		this.infobox=hMap;
		}
	
	public void setSection(ArrayList<String[]> args){
		this.section=args;
	}
	
	public String getTitle() {
		return this.title;
		}
	public int getId() {
		return this.id;
		}

	public String getAuthor() {
		return this.author;
		}

	public String getPublishDate() {
		return this.publishDate;
		}

	public String getText() {
		return this.text;
		}
	public HashMap getInfobox() {
		return this.infobox;
		}
	public String getSummary() {
		return this.summary;
		}
}