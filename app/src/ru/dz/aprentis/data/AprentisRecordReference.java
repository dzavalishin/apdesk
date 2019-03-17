package ru.dz.aprentis.data;

public class AprentisRecordReference extends AbstractAprentisReference 
{

	public AprentisRecordReference(String ref) {
		super(ref);
	}


	@Override
	protected void checkFormat(String ref) {
		if( !ref.matches("c[0-9]+a[0-9]+t[0-9]+r[0-9]+") )
			throw new RuntimeException("record ref syntax: "+ref);
	}
	

	// Cat ref is rec ref but final number
	public AprentisCategoryReference getCategoryReference()
	{
		int endIndex = ref.indexOf('r');
		if( endIndex < 0 )
			throw new RuntimeException("record ref syntax, no 'r': "+ref);
		
		String s = ref.substring(0, endIndex+1);
		return new AprentisCategoryReference(s);
	}
	
}
