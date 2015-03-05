
package com.exlibris.dps.alto.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@Generated("com.googlecode.jsonschema2pojo")
public class Match {

    private String text;
    private List<Par> par = new ArrayList<Par>();
    private Map<String, Object> additionalProperties ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Par> getPar() {
        return par;
    }

    public void setPar(List<Par> par) {
        this.par = par;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "Match [text=" + text + ", par=" + par
				+ ", additionalProperties=" + additionalProperties + "]";
	}

	public Match(String text) {
		this.text = text;
	}



}
