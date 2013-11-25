import java.util.Arrays;

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
				query.setFacetField(QueryConstants.facettagger.get(type
						.toLowerCase()));

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

	public static void main(String[] args) {

		try {
			if (args.length == 0) {
				throw new Exception("Required Java args : FileDirec type");
			}
			System.out.println(Arrays.asList(args));
			QueryConstants.UIDirc = args[0];
			String[] queryStr = new String[args.length - 1];
			for (int i = 1; i < args.length; i++)
				queryStr[i - 1] = args[i];
			Query query = QueryFacet.queryMap(queryStr[0]);

			QuerySearch
					.queryResults(
							query,
							QueryConstants.UIDirc
									+ QueryConstants.facetFileTagger
											.get(args[0].toLowerCase()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
