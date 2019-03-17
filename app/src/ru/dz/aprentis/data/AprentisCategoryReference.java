package ru.dz.aprentis.data;

public class AprentisCategoryReference extends AbstractAprentisReference
{

	public AprentisCategoryReference(String ref) {
		super(ref);
	}

	@Override
	protected void checkFormat(String ref) {
		if( !ref.matches("c[0-9]+a[0-9]+t[0-9]+r") )
			throw new RuntimeException("category ref syntax: "+ref);
	}
	
}
