package ru.dz.aprentis.data;

public class AprentisCategoryReference implements IAprentisReference 
{
	private String ref;

	public AprentisCategoryReference(String ref) {
		this.ref = ref;
		
		if( !ref.matches("c[0-9]+a[0-9]+t[0-9]+r") )
			throw new RuntimeException("category ref syntax: "+ref);
	}
	
	@Override
	public String getAsString() { return ref; }
}
