package net.quantuminfinity.particles.system;

import net.quantuminfinity.utils.Random;

public class SystemGenerator
{	
	public static int genPosTex16(int size)
	{
		Image16 image = new Image16(size, size);
		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				image.setPixel(x,y, Image16.getRGBA(Random.getInt(65535), Random.getInt(65535), Random.getInt(65535), 0));
		
		return image.toTexture();
	}
	
	public static int genVelTex16(int size)
	{
		Image16 image = new Image16(size, size);
		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				image.setPixel(x,y, Image16.getRGBA(32768, 32768, 0, 0));
		
		return image.toTexture();
	}
	
	public static double fx(float x, float y)
	{
		double a = Math.atan2(y, x + 0.0000001);
		return Math.cos(a + Math.PI/2);
	}

	public static double fy(float x, float y)
	{
		double a = Math.atan2(y, x + 0.0000001);
		return Math.sin(a*a + Math.PI/2);
	}
	
	public static int genFlowTex(int size)
	{
		Image16 image = new Image16(size, size);
		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				image.setPixel(x,y, Image16.getRGBA(
						(int) (fx((x - image.getWidth()/2) / (float) (image.getWidth() / 2f), (y - image.getHeight()/2) / (float) (image.getHeight() / 2f)) * 32768 + 32768), 
						(int) (fy((x - image.getWidth()/2) / (float) (image.getWidth() / 2f), (y - image.getHeight()/2) / (float) (image.getHeight() / 2f)) * 32768 + 32768), 
						0, 
						0));
		
		return image.toTexture();
	}
}
