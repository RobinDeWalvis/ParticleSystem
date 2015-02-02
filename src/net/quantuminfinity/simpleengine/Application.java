package net.quantuminfinity.simpleengine;

import net.quantuminfinity.simpleengine.Engine.EngineState;
import net.quantuminfinity.simpleengine.Engine.GameType;
import net.quantuminfinity.utils.Vector2;

public abstract class Application
{	
	EngineState state;
	
	public abstract boolean preInit();
	
	public abstract void onCreate();
	
	public abstract void onDraw();
	
	public abstract void onDestroy();
	
	public abstract Vector2 getDisplaySize();
	
	public abstract GameType getGameType();
}
