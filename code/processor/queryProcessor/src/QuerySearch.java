import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.noggit.JSONUtil;

public class QuerySearch {

	public static void queryResults(Query query, String file) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(QueryConstants.solrURL);
			String urlParameters = frameURLParameters(query);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(QueryConstants.solrRequestMethod);
			connection.setRequestProperty("Content-Type",
					QueryConstants.solrContentType);

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language",
					QueryConstants.solrContentLanguage);

			connection.setUseCaches(QueryConstants.solrCaches);
			connection.setDoInput(QueryConstants.solrDoInput);
			connection.setDoOutput(QueryConstants.solrDoOutput);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			if (file != null && !file.equalsIgnoreCase(""))
				responseToFile(response, file);
			System.out.println(response);
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static void responseToFile(StringBuffer response, String file) {

		System.out.println("File :: " + file);
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(response.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String frameURLParameters(Query query) {
		String urlParameters = "";
		try {
			// Specifying the query
			String queryStr = query.getQueryStr();
			ArrayList<String> queryFields = query.getField();
			if (queryStr != null)
				urlParameters += "q="
						+ URLEncoder.encode(queryStr,
								QueryConstants.solrEncodeType);
			
			// filtering the fields
			if (!queryFields.isEmpty()) {
				String field = "";
				Iterator<String> fieldIte = queryFields.iterator();
				while (fieldIte.hasNext()) {
					field += fieldIte.next() + ",";
				}
				if (!field.isEmpty()) {
					if (field.endsWith(","))
						field = field.substring(0, field.length() - 1);
				}
				urlParameters += "&fl="
						+ URLEncoder.encode(field,
								QueryConstants.solrEncodeType);

			}
			
			if(query.getRows() != null)
				urlParameters += "&rows="
						+ URLEncoder.encode(String.valueOf(query.getRows()),
								QueryConstants.solrEncodeType);
			//For faceting
			if (query.getFacet())
				urlParameters += "&facet="
						+ URLEncoder.encode(String.valueOf(query.getFacet()),
								QueryConstants.solrEncodeType);
			if (query.getFacetQuery() != null)
				urlParameters += "&facet.query="
						+ URLEncoder.encode(query.getFacetQuery(),
								QueryConstants.solrEncodeType);
			if (query.getFacetField() != null)
				urlParameters += "&facet.field="
						+ URLEncoder.encode(query.getFacetField(),
								QueryConstants.solrEncodeType);
			if (!query.getFilterQuery().isEmpty()) {
				for (Map.Entry<String, String> entry : query.getFilterQuery()
						.entrySet()) {
					urlParameters += "&fq="
							+ URLEncoder.encode(
									entry.getKey() + ":" + entry.getValue(),
									QueryConstants.solrEncodeType);

				}

			}
			
			//for more like this options
			if(query.getMlt()) {
				urlParameters += "&mlt="
						+ URLEncoder.encode(String.valueOf(query.getMlt()),
								QueryConstants.solrEncodeType);
				if(query.getMltMinTf() != 0) {
					urlParameters += "&mlt.mintf="
							+ URLEncoder.encode(String.valueOf(query.getMltMinTf()),
									QueryConstants.solrEncodeType);
				}
				if(query.getMltMinDf() != 0) {
					urlParameters += "&mlt.mindf="
							+ URLEncoder.encode(String.valueOf(query.getMltMinDf()),
									QueryConstants.solrEncodeType);
				}
				if(query.getMltMaxTf() != 0) {
					urlParameters += "&mlt.maxtf="
							+ URLEncoder.encode(String.valueOf(query.getMltMaxTf()),
									QueryConstants.solrEncodeType);
				}
				if(query.getMltMaxDf() != 0) {
					urlParameters += "&mlt.maxdf="
							+ URLEncoder.encode(String.valueOf(query.getMltMaxDf()),
									QueryConstants.solrEncodeType);
				}
				if(query.getMltCount() != 0) {
					urlParameters += "&mlt.count="
							+ URLEncoder.encode(String.valueOf(query.getMltCount()),
									QueryConstants.solrEncodeType);
				}
				if(query.getMltField() != null) {
					urlParameters += "&mlt.fl="
							+ URLEncoder.encode(query.getMltField(),
									QueryConstants.solrEncodeType);
				}
			}
			
			//for more like this options
			if(query.isHl()) {
				urlParameters += "&hl="
						+ URLEncoder.encode(String.valueOf(query.isHl()),
								QueryConstants.solrEncodeType);
				if(query.getHlSimplePre() != null) {
					urlParameters += "&hl.simple.pre="
							+ URLEncoder.encode(query.getHlSimplePre(),
									QueryConstants.solrEncodeType);
				}
				if(query.getHlSimplePost() != null) {
					urlParameters += "&hl.simple.post="
							+ URLEncoder.encode(query.getHlSimplePost(),
									QueryConstants.solrEncodeType);
				}
				if(query.getHlField() != null) {
					urlParameters += "&hl.fl="
							+ URLEncoder.encode(query.getHlField(),
									QueryConstants.solrEncodeType);
				}
				if(query.isHlRequireFieldMatch()) {
					urlParameters += "&hl.requireFieldMatch="
							+ URLEncoder.encode(String.valueOf(query.isHlRequireFieldMatch()),
									QueryConstants.solrEncodeType);
				}
				if(query.isHlUsePhraseHighlighter()) {
					urlParameters += "&hl.usePhraseHighlighter="
							+ URLEncoder.encode(String.valueOf(query.isHlUsePhraseHighlighter()),
									QueryConstants.solrEncodeType);
				}
			}
			
			//for spell check
			if(query.isSpellCheck()) {
				urlParameters += "&spellcheck="
						+ URLEncoder.encode(String.valueOf(query.isSpellCheck()),
								QueryConstants.solrEncodeType);
				if(query.isSpellCheckBuild()) {
					urlParameters += "&spellcheck.build="
							+ URLEncoder.encode(String.valueOf(query.isSpellCheckBuild()),
									QueryConstants.solrEncodeType);
				}
			}
			
			urlParameters += "&wt="
					+ URLEncoder.encode(QueryConstants.solrRequestFormat,
							QueryConstants.solrEncodeType);
			// Intending the request output
			urlParameters += "&indent="
					+ URLEncoder.encode(
							String.valueOf(QueryConstants.solrIndent),
							QueryConstants.solrEncodeType);

			System.out.println(urlParameters);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return urlParameters;

	}

}
