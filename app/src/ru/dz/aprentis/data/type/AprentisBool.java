package ru.dz.aprentis.data.type;

import ru.dz.aprentis.data.AprentisFieldValue;

public class AprentisBool extends AprentisFieldValue {

	private boolean value;

	public AprentisBool(boolean value) {
		this.value = value;		
	}

	public boolean getValue() {
		return value;
	}

	//public void setValue(boolean value) {		this.value = value;	}

	
	@Override
	public String toString() { return Boolean.toString(value); }
}
