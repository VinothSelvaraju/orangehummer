import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class QueryMltHorExp {

	public static Query queryMap(String[] queryStrArr) {
		
		//QueryStr -> where was rajinikanth born
		Query query = null;
		String queryStr;
		try {
			if (queryStrArr.length < 5) {

				throw new Exception("Query not sufficiently formed");

			}
			query = new Query();
			queryStr = queryStrArr[3];
			query.setQueryStr(queryStr);
			query.addFilterQuery(QueryConstants.queryTypeTag, queryStrArr[0]);
			//query.addField(QueryConstants.queryMainTag);
			query.addFieldCollection(fieldMapper(queryStrArr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}

	
	
	public static ArrayList<String> fieldMapper(String[] queryStrArr) {
		ArrayList<String> fields = new ArrayList<String>();
		List<String> fieldsQueried = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		
		if(queryStrArr[0].toLowerCase().equalsIgnoreCase("person")) {
			tags=Arrays.asList(QueryConstants.personTags);
		} else if(queryStrArr[0].toLowerCase().equalsIgnoreCase("place")) {
			tags=Arrays.asList(QueryConstants.personTags);
		} else if(queryStrArr[0].toLowerCase().equalsIgnoreCase("film")) {
			tags=Arrays.asList(QueryConstants.personTags);
		} 
		
		fieldsQueried = QueryMapper.fieldMapper(queryStrArr);
		Iterator iteTags = tags.iterator();
		while(iteTags.hasNext()) {
			String fieldAdd = (String) iteTags.next();
			if(!fieldsQueried.contains(fieldAdd))
				fields.add(fieldAdd);
		}
		fields.add(QueryConstants.queryMainTag);
		return fields;
	}
	
	
	public static void main(String[] args) {
		
		//String queryStrArr[] = { "what", "was", "*bill gate*", "occupation"};
		
		try {
		if(args.length == 0) {
			throw new Exception("Required Java args : FileDirec type what was rajini occupation");
		}
		System.out.println(Arrays.asList(args));
		QueryConstants.UIDirc = args[0];
		String[] queryStr = new String[args.length-1];
		for(int i = 1; i<args.length;i++)
			queryStr[i-1] = args[i];
		Query query = QueryMltHorExp.queryMap(queryStr);
		QuerySearch.queryResults(query,QueryConstants.UIDirc+QueryConstants.mltHorizontalExpansion);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
