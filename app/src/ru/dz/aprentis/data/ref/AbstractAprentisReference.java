package ru.dz.aprentis.data.ref;

public abstract class AbstractAprentisReference implements IAprentisReference {

	protected String ref;

	protected AbstractAprentisReference(String ref) 
	{
		this.ref = ref;		
		checkFormat(ref);
	}

	protected abstract void checkFormat(String ref);
	
	@Override
	public String getAsString() { return ref; }

	@Override
	public String toString() { return getAsString(); }
	
}
