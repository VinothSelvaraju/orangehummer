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
	private Map<String,String> filterQuery;
	
	public Query() {
		field = new ArrayList<String>();
		start = new Integer(-1);
		param = new HashMap<String, ArrayList<String>>();
		filterQuery = new HashMap<String,String>();
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
		this.filterQuery.put(key,value);
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

	
	
	/*public SolrQuery solrQueryFrame() {
		SolrQuery query = new SolrQuery();
		if(query!=null){
			query.setQuery(queryStr);
		}
		if(!filterQuery.isEmpty()) {
			query.setFilterQueries((String[])filterQuery.toArray());
		}
		if(!field.isEmpty()) {
			query.setFields((String[])field.toArray());
		}
		if(start != -1 ) {
			query.setStart(start);
		}	
		if(param.size() != 0 ) {
			Iterator<Map.Entry<String, ArrayList<String>>> it = param.entrySet().iterator();
			while(it.hasNext()) {
	            Map.Entry<String, ArrayList<String>> entry = it.next();
	            String key = entry.getKey();
	            ArrayList<String> value = entry.getValue();
	            query.setParam(key, (String[])value.toArray());
	        }
		}
		return query;
	}*/
	
}
