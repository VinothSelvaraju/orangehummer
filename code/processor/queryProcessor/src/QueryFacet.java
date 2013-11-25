public class QueryFacet {

	public static Query queryMap(String type) {
		// QueryStr -> where was rajinikanth born

		Query query = null;
		String queryStr;
		try {
			if (type == null || type.equalsIgnoreCase(""))

				throw new Exception(
						"Type not available. Query faceting not possible");

			try {
				query = new Query();
				queryStr = QueryConstants.queryAllFetch;
				query.setQueryStr(queryStr);
				query.setFacet(true);
				query.setFacetField(QueryConstants.facettagger.get(type));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return query;
	}

}
