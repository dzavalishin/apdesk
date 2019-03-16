package ru.dz.aprentis.data;

public class AprentisDocument extends AprentisFieldValue {

	private String label;
	private String url;
	
	public AprentisDocument(String label, String url) {
		this.label = label;
		this.url = url;
	}

	@Override
	public String toString() { return label; }
	
}
