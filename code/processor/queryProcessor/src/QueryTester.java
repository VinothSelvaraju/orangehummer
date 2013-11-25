public class QueryTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// queryStrArr[] = { "what", "was", "*bill gate*", "occupation"};
		Query query = QueryMapper.queryMap(args);
		QuerySearch.queryResults(query,QueryConstants.UIDirc+QueryConstants.queryFile);
		
		
//		System.out.println("------------------------------------------------------------------------------------------");
//		
//		String type = "person";
//		Query queryFacet = QueryFacet.queryMap(type);
//		QuerySearch.queryResults(queryFacet);
	}

}
