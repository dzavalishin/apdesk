package ru.dz.aprentis.data.type;

import ru.dz.aprentis.data.AprentisFieldValue;

public class AprentisNumeric extends AprentisFieldValue {

	private double value;

	public AprentisNumeric(double value) {
		this.value = value;
	}

	public double getValue() {		return value;	}

	//public void setValue(double value) {		this.value = value;	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
	
}
