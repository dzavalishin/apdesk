package ru.dz.vita2d.data.type;


import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import ru.dz.vita2d.data.ref.EntityRef;
import ru.dz.vita2d.data.ref.IRef;

/**
 * This is actually a string with a server entity (table) name
 * @author dz
 *
 */
public class EntityType extends AbstractEntityType
{
	protected static Set<EntityType> all;
	static {
		all = new HashSet<>();
	}

	static final public EntityType MEAN_KINDS = new EntityType("meanKind","вид средства","виды средств");
	static final public EntityType EMPLOYEES = new EntityType("employee","сотрудник","сотрудники");
	static final public EntityType DIVISIONS = new EntityType("division","подразделение","подразделения");
	static final public EntityType CONTRAGENTS = new EntityType("contragent","контрагент","контрагенты");
	static final public EntityType DIVISION_TYPES = new EntityType("divisionType","тип подразделения","типы подразделений");
	static final public EntityType LOCATIONS = new EntityType("location","место","места");

	static final public EntityType EVENT_TYPES = new EntityType("eventType","тип события","типы событий");
	static final public EntityType EVENT_CAUSES = new EntityType("eventCause","причина события","причины событий");
	static final public EntityType EVENT_SOURCES = new EntityType("eventSource","источник события","источники событий");
	// TODO single/plural suffix -es! make special constructor?
	static final public EntityType EVENT_STATUSES = new EntityType("eventStatuse","статус события","статусы событий");
	static final public EntityType EVENT_EFFECT_TYPES = new EntityType("eventEffectType","последствие события","последствия событий");
	static final public EntityType EVENT_END_CODES = new EntityType("eventEndCode","код завершения события","коды завершения событий");
	
	private EntityType(String name, String displayName, String pluralDisplayName) {
		super(name, displayName, pluralDisplayName);
		
		all.add(this);
	}
	
	static public void forEach(Consumer<? super EntityType> action)
	{
		all.forEach(action);
	}
	
	
	@Override
	public String toString() {
		return plural;
	}

	static class etptr {
		EntityType t = null;
	}
	
	public static EntityType fromString(String unitType )
	{
		etptr p = new etptr();
		
		all.forEach( t -> { 
			if( t.plural.equalsIgnoreCase(unitType) ) 
				p.t = t; 
			});
		
		if(p.t == null)
			all.forEach( t -> { 
				if( t.single.equalsIgnoreCase(unitType) ) 
					p.t = t; 
				});
		
		//if(p.t == null)			System.out.println("Unknown Entity Type ="+unitType); // TODO log
		
		return p.t;
	}

	@Override
	public IRef makeIRef(int id) {		
		return new EntityRef(this.plural,id);
	}
	
}
