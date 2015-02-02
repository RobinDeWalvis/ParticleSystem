package net.quantuminfinity.utils;

public class Random {
	
	public static int getInt(int min,int max)
	{
		return (int) (Math.random() * (max + 1 - min)) + min;
	}
	
	public static int getInt(int max)
	{
		return (int) (Math.random() * (max + 1));
	}
	
	public static float getFloat(float min,float max)
	{
		return (float) (Math.random() * (max - min)) + min;
	}
	
	public static float getFloat(float max)
	{
		return (float) (Math.random() * (max));
	}
}
