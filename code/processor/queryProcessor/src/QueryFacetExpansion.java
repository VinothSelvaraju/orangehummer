import java.util.Arrays;

public class QueryFacetExpansion {

	public static Query queryMap(String[] facetStr) {
		// QueryStr -> where was rajinikanth born

		Query query = null;
		String queryStr;
		try {
			if (facetStr.length == 0)

				throw new Exception(
						"Type not available. Query faceting expansion not possible");

			try {
				query = new Query();
				queryStr = QueryConstants.facettagger.get(facetStr[0].toLowerCase())+":\""+facetStr[1]+"\"";
				query.setQueryStr(queryStr);
				query.setRows(100);
				query.addField(QueryConstants.queryMainTag);
				query.addField(QueryConstants.facettagger.get(facetStr[0].toLowerCase()));
				query.addFilterQuery(QueryConstants.queryTypeTag, facetStr[0].toLowerCase());

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
				throw new Exception("Required Java args : FileDirec type facetTerm");
			}
			System.out.println(Arrays.asList(args));
			QueryConstants.UIDirc = args[0];
			String[] queryStr = new String[args.length - 1];
			for (int i = 1; i < args.length; i++)
				queryStr[i - 1] = args[i];
			Query query = QueryFacetExpansion.queryMap(queryStr);

			QuerySearch
					.queryResults(
							query,
							QueryConstants.UIDirc
									+ QueryConstants.facetQueryExpansion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
