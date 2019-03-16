package ru.dz.aprentis.data.type;

import ru.dz.aprentis.data.AprentisFieldValue;

public class AprentisReference extends AprentisFieldValue {

	private String label;
	private String reference;

	public AprentisReference(String label, String ref) {
		this.label = label;
		this.reference = ref;
	}

	public String getLabel() {		return label;	}

	//public void setLabel(String label) {		this.label = label;	}

	public String getReference() {		return reference;	}

	//public void setReference(String reference) {		this.reference = reference;	}

	@Override
	public String toString() { return label; }

}
