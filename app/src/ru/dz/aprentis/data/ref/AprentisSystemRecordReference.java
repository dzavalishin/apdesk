package ru.dz.aprentis.data.ref;

public class AprentisSystemRecordReference extends AprentisRecordReference {

	public AprentisSystemRecordReference(String ref) {
		super(ref);
	}

	@Override
	protected void checkFormat(String ref) {
		if( ref.matches("c[0-9]+a[0-9]+ts[0-9]+r[0-9]+") )
			return;

		if( ref.matches("c[0-9]+ts[0-9]+r[0-9]+") )
			return;
		
		// zts11r564
		if( ref.matches("zts[0-9]+r[0-9]+") )
			return;
		
		throw new RuntimeException("system record ref syntax: "+ref);
	}

	// Cat ref is rec ref but final number
	@Override
	public AprentisCategoryReference getCategoryReference()
	{
		int endIndex = ref.indexOf('r');
		if( endIndex < 0 )
			throw new RuntimeException("record ref syntax, no 'r': "+ref);
		
		String s = ref.substring(0, endIndex+1);
		return new AprentisSystemCategoryReference(s);
	}
	
	
}
