package ru.dz.aprentis.data;

public class AprentisString extends AprentisFieldValue {

	private String value;

	public AprentisString(String value) {
		this.value = value;		
	}

	public String getValue() {
		return value;
	}

	//public void setValue(String value) {		this.value = value;	}

	
	@Override
	public String toString() { return value; }
}
