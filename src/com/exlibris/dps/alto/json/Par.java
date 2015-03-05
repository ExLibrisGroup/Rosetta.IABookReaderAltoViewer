
package com.exlibris.dps.alto.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@Generated("com.googlecode.jsonschema2pojo")
public class Par {

    private List<Box> boxes = new ArrayList<Box>();
    private int b;
    private int t;
    private int page_width;
    private int r;
    private int l;
    private int page_height;
    private int page;
    private Map<String, Object> additionalProperties ;

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getPage_width() {
        return page_width;
    }

    public void setPage_width(int page_width) {
        this.page_width = page_width;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getPage_height() {
        return page_height;
    }

    public void setPage_height(int page_height) {
        this.page_height = page_height;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "Par [boxes=" + boxes + ", b=" + b + ", t=" + t
				+ ", page_width=" + page_width + ", r=" + r + ", l=" + l
				+ ", page_height=" + page_height + ", page=" + page
				+ ", additionalProperties=" + additionalProperties + "]";
	}

	public Par(int b, int t, int page_width, int r, int l, int page_height,
			int page) {
		super();
		this.b = b;
		this.t = t;
		this.page_width = page_width;
		this.r = r;
		this.l = l;
		this.page_height = page_height;
		this.page = page;
	}



}
