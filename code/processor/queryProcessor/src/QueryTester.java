public class QueryTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String queryStrArr[] = { "what", "was", "*bill gate*", "occupation"};
		Query query = QueryMapper.queryMap(queryStrArr);
		QuerySearch.queryResults(query);
		
		
		System.out.println("------------------------------------------------------------------------------------------");
		
		String type = "person";
		Query queryFacet = QueryFacet.queryMap(type);
		QuerySearch.queryResults(queryFacet);
	}

}
