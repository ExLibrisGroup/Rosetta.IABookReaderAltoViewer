
package com.exlibris.dps.alto.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@Generated("com.googlecode.jsonschema2pojo")
public class Book {

	private String ia;
    private String q;
    private Boolean indexed;
    private Integer page_count;
    private Integer body_length;
    private Boolean leaf0_missing;
    private List<Match> matches = new ArrayList<Match>();
    private Map<String, Object> additionalProperties;

    public String getIa() {
        return ia;
    }

    public void setIa(String ia) {
        this.ia = ia;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getBody_length() {
        return body_length;
    }

    public void setBody_length(int body_length) {
        this.body_length = body_length;
    }

    public boolean isLeaf0_missing() {
        return leaf0_missing;
    }

    public void setLeaf0_missing(boolean leaf0_missing) {
        this.leaf0_missing = leaf0_missing;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
	public String toString() {
		return "Book [ia=" + ia + ", q=" + q + ", indexed=" + indexed
				+ ", page_count=" + page_count + ", body_length=" + body_length
				+ ", leaf0_missing=" + leaf0_missing + ", matches=" + matches
				+ ", additionalProperties=" +  "]";
	}

	public Book(String ia, String q, Boolean indexed, Integer page_count,
			Integer body_length, Boolean leaf0_missing) {
		super();
		this.ia = ia;
		this.q = q;
		this.indexed = indexed;
		this.page_count = page_count;
		this.body_length = body_length;
		this.leaf0_missing = leaf0_missing;
	}



}
