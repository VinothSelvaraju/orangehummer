import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

public class Query {

	private String queryStr;
	private ArrayList<String> field;
	private Integer start;
	private Map<String, ArrayList<String>> param;
	private Boolean facet = false;
	private String facetField;
	private String facetQuery;
	private Map<String, String> filterQuery;
	private Boolean mlt;
	private int mltMinDf;
	private int mltMinTf;
	private int mltMaxDf;
	private int mltMaxTf;
	private String mltField;
	private int mltCount;
	private boolean hl;
	private String hlField;
	private String hlSimplePre;
	private String hlSimplePost;
	private boolean hlRequireFieldMatch;
	private boolean hlUsePhraseHighlighter;
	
	public Query() {
		field = new ArrayList<String>();
		start = new Integer(-1);
		param = new HashMap<String, ArrayList<String>>();
		filterQuery = new HashMap<String, String>();
		mlt = false;
	}
	
	public boolean isHl() {
		return hl;
	}

	public void setHl(boolean hl) {
		this.hl = hl;
	}

	public String getHlField() {
		return hlField;
	}

	public void setHlField(String hlField) {
		this.hlField = hlField;
	}

	public String getHlSimplePre() {
		return hlSimplePre;
	}

	public void setHlSimplePre(String hlSimplePre) {
		this.hlSimplePre = hlSimplePre;
	}

	public String getHlSimplePost() {
		return hlSimplePost;
	}

	public void setHlSimplePost(String hlSimplePost) {
		this.hlSimplePost = hlSimplePost;
	}

	public boolean isHlRequireFieldMatch() {
		return hlRequireFieldMatch;
	}

	public void setHlRequireFieldMatch(boolean hlRequireFieldMatch) {
		this.hlRequireFieldMatch = hlRequireFieldMatch;
	}

	public boolean isHlUsePhraseHighlighter() {
		return hlUsePhraseHighlighter;
	}

	public void setHlUsePhraseHighlighter(boolean hlUsePhraseHighlighter) {
		this.hlUsePhraseHighlighter = hlUsePhraseHighlighter;
	}

	public int getMltCount() {
		return mltCount;
	}

	public void setMltCount(int mltCount) {
		this.mltCount = mltCount;
	}

	public void setMltField(String mltField) {
		this.mltField = mltField;
	}

	public Boolean getMlt() {
		return mlt;
	}

	public void setMlt(Boolean mlt) {
		this.mlt = mlt;
	}

	public int getMltMinDf() {
		return mltMinDf;
	}

	public void setMltMinDf(int mltMinDf) {
		this.mltMinDf = mltMinDf;
	}

	public int getMltMinTf() {
		return mltMinTf;
	}

	public void setMltMinTf(int mltMinTf) {
		this.mltMinTf = mltMinTf;
	}

	public int getMltMaxDf() {
		return mltMaxDf;
	}

	public void setMltMaxDf(int mltMaxDf) {
		this.mltMaxDf = mltMaxDf;
	}

	public int getMltMaxTf() {
		return mltMaxTf;
	}

	public void setMltMaxTf(int mltMaxTf) {
		this.mltMaxTf = mltMaxTf;
	}

	public String getMltField() {
		return mltField;
	}

	public Boolean getFacet() {
		return facet;
	}

	public void setFacet(Boolean facet) {
		this.facet = facet;
	}

	public String getFacetField() {
		return facetField;
	}

	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}

	public String getFacetQuery() {
		return facetQuery;
	}

	public void setFacetQuery(String facetQuery) {
		this.facetQuery = facetQuery;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public Map<String, String> getFilterQuery() {
		return filterQuery;
	}

	public void addFilterQuery(String key, String value) {
		this.filterQuery.put(key, value);
	}

	public ArrayList<String> getField() {
		return field;
	}

	public void addField(String fieldComponent) {
		this.field.add(fieldComponent);
	}

	public void addFieldCollection(ArrayList<String> fields) {
		this.field.addAll(fields);
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Map<String, ArrayList<String>> getParam() {
		return param;
	}

	public void setParam(Map<String, ArrayList<String>> param) {
		this.param = param;
	}

	/*
	 * public SolrQuery solrQueryFrame() { SolrQuery query = new SolrQuery();
	 * if(query!=null){ query.setQuery(queryStr); } if(!filterQuery.isEmpty()) {
	 * query.setFilterQueries((String[])filterQuery.toArray()); }
	 * if(!field.isEmpty()) { query.setFields((String[])field.toArray()); }
	 * if(start != -1 ) { query.setStart(start); } if(param.size() != 0 ) {
	 * Iterator<Map.Entry<String, ArrayList<String>>> it =
	 * param.entrySet().iterator(); while(it.hasNext()) { Map.Entry<String,
	 * ArrayList<String>> entry = it.next(); String key = entry.getKey();
	 * ArrayList<String> value = entry.getValue(); query.setParam(key,
	 * (String[])value.toArray()); } } return query; }
	 */

}
