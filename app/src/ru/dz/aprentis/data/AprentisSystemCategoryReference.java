package ru.dz.aprentis.data;

public class AprentisSystemCategoryReference extends AprentisCategoryReference {

	public AprentisSystemCategoryReference(String ref) {
		super(ref);
	}

	@Override
	protected void checkFormat(String ref) {
		if( ref.matches("c[0-9]+a[0-9]+ts[0-9]+r") )
			return;

		if( ref.matches("c[0-9]+ts[0-9]+r") )
			return;

		// zts11r564
		if( ref.matches("zts[0-9]+r") )
			return;
		
		throw new RuntimeException("system category ref syntax: "+ref);
	}
	

}
