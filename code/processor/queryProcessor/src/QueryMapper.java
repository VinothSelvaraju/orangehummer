import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class QueryMapper {

	public static Query queryMap(String[] queryStrArr) {
		
		//QueryStr -> where was rajinikanth born
		Query query = null;
		String queryStr;
		try {
			if (queryStrArr.length < 5) {

				throw new Exception("Query not sufficiently formed");

			}
			query = new Query();
			queryStr = QueryConstants.queryMainTag+":"+queryStrArr[3];
			query.setQueryStr(queryStr);
			query.addFilterQuery(QueryConstants.queryTypeTag, queryStrArr[0]);
			query.addField(QueryConstants.queryKeyTag);
			query.addField(QueryConstants.queryMainTag);
			query.addFieldCollection(fieldMapper(queryStrArr));
			query.setHl(true);
			query.setHlField(QueryConstants.queryMainTag);
			query.setHlSimplePre(QueryConstants.flSimplePre);
			query.setHlSimplePost(QueryConstants.flSimplePost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query;
	}

	public static ArrayList<String> fieldMapper(String[] queryStrArr) {
		ArrayList<String> fields = new ArrayList<String>();
		
		//Case to answer dynamic query
		//first_ , second_
		String number = ""; 
		if(queryStrArr.length == 6) {
			if(queryStrArr[5] != "") {
				number = queryStrArr[5]+"_";
			}
		}
			
		
		//Case 1:
		//To satisfy for where and when query
		//Key and value mapping to be different
		//key will have born
		//value will where, when
		String[] valueFields = {"where","when"};
		String key = QueryConstants.fieldtagger.get(queryStrArr[4]);
		String value = QueryConstants.fieldtagger.get(queryStrArr[1]);
		//System.out.println(key +" :: "+value);
//		if(key == null) {
//			key = queryStrArr[3];
//		}
//		
//		if(value == null) {
//			value = queryStrArr[0];
//		}
		
		if(key != null) {
			if(value != null && Arrays.asList(valueFields).contains(queryStrArr[1])) {
				fields.add(number+key+value);
			}
		}
		
		//Case 2:
		//To satisfy who query
		//key must be important
		//value not to be considered
		if(fields.size() == 0) {
			if(key == null) {
				key = queryStrArr[4];
			}
			fields.add(number+key);
		}
		
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
		Query query = QueryMapper.queryMap(queryStr);
		QuerySearch.queryResults(query,QueryConstants.UIDirc+QueryConstants.queryFile);
		}catch(Exception e) {
			
		}
	}
}
