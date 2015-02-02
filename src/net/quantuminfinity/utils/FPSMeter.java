package net.quantuminfinity.utils;

import org.lwjgl.Sys;

public class FPSMeter
{
	long lastFrame;
	int fps,lastfps=0;
	long lastFPS;
	
	public FPSMeter()
	{
		getDelta();
		lastFPS = getTime();
	}
	
	public int updateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{
			lastfps = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
		return lastfps;
	}    
	
	public int getDelta()
	{
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    return delta;
	}
	
	public long getTime()
	{
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
		
}
