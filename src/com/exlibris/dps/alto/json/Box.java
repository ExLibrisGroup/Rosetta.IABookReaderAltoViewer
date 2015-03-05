package com.exlibris.dps.alto.json;

import java.util.Map;

import javax.annotation.Generated;

@Generated("com.googlecode.jsonschema2pojo")
public class Box {

	private float r;
	private float b;
	private float t;
	private float page;
	private float l;
	private Map<String, Object> additionalProperties;

	public float getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public float getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public float getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public float getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public float getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public Box(float r, float b, float t, float page, float l) {
		super();
		this.r = r;
		this.b = b;
		this.t = t;
		this.page = page;
		this.l = l;
	}

	@Override
	public String toString() {
		return "Box [r=" + r + ", b=" + b + ", t=" + t + ", page=" + page
				+ ", l=" + l + ", additionalProperties=" + additionalProperties
				+ "]";
	}



}
