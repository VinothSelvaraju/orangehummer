import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class QueryConstants {

	static String solrURL = "http://localhost:8983/solr/QACollection/select";
	// static String NLPModeler =
	// "C:\\Pals\\Graduate_Course\\Subjects\\Information_Retrieval\\Project\\Project 2\\Downloads\\stanford-postagger-2013-06-20\\stanford-postagger-2013-06-20\\models\\english-left3words-distsim.tagger";
	static String solrRequestMethod = "GET";
	static String solrEncodeType = "UTF-8";
	static String solrRequestFormat = "json";
	static String solrContentType = "application/x-www-form-urlencoded";
	static String solrContentLanguage = "en-US";
	static boolean solrCaches = false;
	static boolean solrDoInput = true;
	static boolean solrDoOutput = true;
	static boolean solrIndent = true;

	static String UIDirc;
	static String queryFile = "queryOutput.json";
	static String facetPersonFile = "facetPerson.json";
	static String facetPlaceFile = "facetPlace.json";
	static String facetFilmFile = "facetFilm.json";
	static String facetQueryExpansion = "facetExpansion.json";
	static String mltVerticalExpansion = "mltVerExp.json";
	static String mltHorizontalExpansion = "mltHorExp.json";

	static String queryMainTag = "name";
	static String queryTypeTag = "type";
	static String queryAllFetch = "*:*";
	
	//if set to zero, will not be considered
	static int mltMinTf = 1;
	static int mltMinDf = 1;
	static int mltMaxTf = 0;
	static int mltMaxDf = 0;
	static int mltCount = 2;
	
	static String flSimplePre = "<strong>";
	static String flSimplePost = "</strong>";
	
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
		facettagger.put("person", "occupationFacet");

	}

	static Map<String, String> mlttagger;

	static {
		mlttagger = new HashMap<String, String>();
		mlttagger.put("person", "occupation");

	}

	static Map<String, String> facetFileTagger;

	static {
		facetFileTagger = new HashMap<String, String>();
		facetFileTagger.put("person", facetPersonFile);
		facetFileTagger.put("place", facetPlaceFile);
		facetFileTagger.put("film", facetFilmFile);

	}

	static String[] personTags = { "birthname", "education", "birthplace",
			"deathplace", "occupation", "awards", "weight",
			"height", "religion", "parents", "caption", "deathdate", "partner",
			"title", "spouse", "deathcause", "yearsactive", "website",
			"residence", "knownfor", "nationality", "birthdate", "links",
			"role", "year", "children", "denomination", "networth",
			"credits", "gender", "othernames", "party", "family", "successor",
			"predecessor", "salary", "alias", "employer", "season", "tribe",
			"television", "movement", "status", "ethnicity", "siblings",
			"citizenship" };
}
