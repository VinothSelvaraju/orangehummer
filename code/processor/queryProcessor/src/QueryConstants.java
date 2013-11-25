import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class QueryConstants {

	static String solrURL = "http://localhost:8983/solr/QACollection/select";
	// static String NLPModeler =
	// "C:\\Pals\\Graduate_Course\\Subjects\\Information_Retrieval\\Project\\Project 2\\Downloads\\stanford-postagger-2013-06-20\\stanford-postagger-2013-06-20\\models\\english-left3words-distsim.tagger";
	static String solrRequestMethod = "GET";
	static String solrEncodeType = "UTF-8";
	static String solrRequestFormat = "JSON";
	static String solrContentType = "application/x-www-form-urlencoded";
	static String solrContentLanguage = "en-US";
	static boolean solrCaches = false;
	static boolean solrDoInput = true;
	static boolean solrDoOutput = true;
	static boolean solrIndent = true;

	static String UIDirc = ".."+File.separator+".."+File.separator+"userInterface"+File.separator+"docs"+File.separator;
	static String queryFile = "queryOutput.txt";
	static String facetPersonFile = "facetPerson.txt";
	static String facetPlaceFile = "facetPlace.txt";
	static String facetFilmFile = "facetFilm.txt";
	
	static String queryMainTag = "name";
	static String queryAllFetch = "*:*";

	static Map<String, String> fieldtagger;

	static {
		fieldtagger = new HashMap<String, String>();
		fieldtagger.put("where", "place");
		fieldtagger.put("when", "date");
		fieldtagger.put("born", "birth");
		fieldtagger.put("child", "children");
	}

	static Map<String, String> facettagger;

	static {
		facettagger = new HashMap<String, String>();
		facettagger.put("person", "occupation");

	}
	
	static Map<String, String> facetFileTagger;

	static {
		facetFileTagger = new HashMap<String, String>();
		facetFileTagger.put("person", facetPersonFile);
		facetFileTagger.put("place", facetPlaceFile);
		facetFileTagger.put("film", facetFilmFile);

	}
}
