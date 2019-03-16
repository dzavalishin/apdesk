package ru.dz.aprentis.data.type;

import ru.dz.aprentis.data.AprentisFieldValue;

public class AprentisEnum extends AprentisFieldValue {

	private String label;
	private int value;

	public AprentisEnum(String label, int value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {		return label;	}

	//public void setLabel(String label) {		this.label = label;	}

	public int getValue() {		return value;	}

	//public void setValue(int value) {		this.value = value;	}

	@Override
	public String toString() { return label; }
	
}
