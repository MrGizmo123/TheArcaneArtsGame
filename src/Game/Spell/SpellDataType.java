package Game.Spell;

import Game.Entities.Entity;
import Game.tools.GameResourcesAndSettings;
import VecMath.Vector3f;

public enum SpellDataType {

	ENTITY(Entity.class, GameResourcesAndSettings.GUI_PINK),
	FLOAT(Float.class, GameResourcesAndSettings.GUI_LIGHT_BLUE),
	VECTOR(Vector3f.class, GameResourcesAndSettings.GUI_GREEN);
	
	private Class<?> type;
	private int texture;
	
	SpellDataType(Class<?> t, int texture)
	{
		this.type = t;
		this.texture = texture;
	}
	
	public Class<?> getType()
	{
		return type;
	}
	
	public int getTexture()
	{
		return texture;
	}
	
}
