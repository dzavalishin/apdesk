package ru.dz.aprentis.data.ref;

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

	public String getCategoryMetadataReferenceString()
	{
		int tpos = ref.indexOf('t');
		int rpos = ref.indexOf('r');
		String meta = ref.substring(0,tpos)+"r"+ref.substring(tpos+1, rpos);
		
		//System.out.println(ref+" -> "+meta);
		
		return meta;
	}
	
}
