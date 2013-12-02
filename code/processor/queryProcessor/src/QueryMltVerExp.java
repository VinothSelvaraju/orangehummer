import java.util.Arrays;

public class QueryMltVerExp {

	public static Query queryMap(String[] mltStr) {
		// QueryStr -> where was rajinikanth born

		Query query = null;
		String queryStr;
		try {
			if (mltStr.length == 0)

				throw new Exception(
						"Type not available. Query More Like this options not possible");

			try {
				query = new Query();
				String fieldSimilarity = QueryConstants.mlttagger.get(mltStr[0].toLowerCase());
				queryStr = mltStr[3].toLowerCase();
				query.setQueryStr(queryStr);
				query.addField(QueryConstants.queryMainTag);
				query.addField(QueryConstants.queryKeyTag);
				//query.addField(fieldSimilarity);
				query.setMlt(true);
				query.setMltMinTf(QueryConstants.mltMinTf);
				query.setMltMinDf(QueryConstants.mltMinDf);
				query.setMltMaxTf(QueryConstants.mltMaxTf);
				query.setMltMaxDf(QueryConstants.mltMaxDf);
				query.setMltCount(QueryConstants.mltCount);
				query.setMltField(fieldSimilarity);
				query.addFilterQuery(QueryConstants.queryTypeTag, mltStr[0].toLowerCase());

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
				throw new Exception("Required Java args : FileDirec type where was rajinikanth born");
			}
			System.out.println(Arrays.asList(args));
			QueryConstants.UIDirc = args[0];
			String[] queryStr = new String[args.length - 1];
			for (int i = 1; i < args.length; i++)
				queryStr[i - 1] = args[i];
			Query query = QueryMltVerExp.queryMap(queryStr);

			QuerySearch
					.queryResults(
							query,
							QueryConstants.UIDirc
									+ QueryConstants.mltVerticalExpansion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
