package ru.dz.aprentis.data.type;

import ru.dz.aprentis.data.AprentisFieldValue;

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
