import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class QueryMapper {

	public static Query queryMap(String[] queryStrArr) {
		
		//QueryStr -> where was rajinikanth born
		Query query = null;
		String queryStr;
		try {
			if (queryStrArr.length < 4) {

				throw new Exception("Query not sufficiently formed");

			}
			query = new Query();
			queryStr = QueryConstants.queryMainTag+":"+queryStrArr[2];
			query.setQueryStr(queryStr);
			query.addField(QueryConstants.queryMainTag);
			query.addFieldCollection(fieldMapper(queryStrArr));
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
		if(queryStrArr.length == 5) {
			if(queryStrArr[4] != "") {
				number = queryStrArr[4]+"_";
			}
		}
			
		
		//Case 1:
		//To satisfy for where and when query
		//Key and value mapping to be different
		//key will have born
		//value will where, when
		String[] valueFields = {"where","when"};
		String key = QueryConstants.fieldtagger.get(queryStrArr[3]);
		String value = QueryConstants.fieldtagger.get(queryStrArr[0]);
		//System.out.println(key +" :: "+value);
//		if(key == null) {
//			key = queryStrArr[3];
//		}
//		
//		if(value == null) {
//			value = queryStrArr[0];
//		}
		
		if(key != null) {
			if(value != null && Arrays.asList(valueFields).contains(queryStrArr[0])) {
				fields.add(number+key+value);
			}
		}
		
		//Case 2:
		//To satisfy who query
		//key must be important
		//value not to be considered
		if(fields.size() == 0) {
			if(key == null) {
				key = queryStrArr[3];
			}
			fields.add(number+key);
		}
		
		return fields;
	}
}
