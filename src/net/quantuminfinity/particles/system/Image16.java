package net.quantuminfinity.particles.system;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Image16
{
	private final int width, height;
	long[] pixels;
	final int BYTES_PER_PIXEL = 8;
	
	public Image16(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new long[getWidth() * getHeight()];
	}
	
	public static long getRGBA(int r, int g, int b, int a)
	{
		return ((long) r << 48) | ((long) g << 32) | ((long) b << 16) | (long) a;
	}
	
	public int toTexture()
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(getWidth() * getHeight() * BYTES_PER_PIXEL);
		
		for(int i = 0; i < getHeight() * getWidth(); i++)
		{
				long pixel = pixels[i];
				buffer.putShort((short) ((pixel >> 48) & 0xFFFF));
				buffer.putShort((short) ((pixel >> 32) & 0xFFFF));
				buffer.putShort((short) ((pixel >> 16) & 0xFFFF));
				buffer.putShort((short) ((pixel >>  0) & 0xFFFF));
		}

		buffer.flip();
		
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA16, getWidth(), getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_SHORT, buffer);
		return textureID;
	}
	
	public void setPixel(int x, int y, long color)
	{
		pixels[y*getWidth() + x] = color;
	}
	
	public long getPixel(int x, int y)
	{
		return pixels[y*getWidth() + x];
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
