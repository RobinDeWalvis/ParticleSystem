package net.quantuminfinity.particles.system;

import java.nio.IntBuffer;

import net.quantuminfinity.utils.Vector2;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class FBO
{
	int fbo = 0, textures;
	Vector2 size;
	int [] FBOData;
	IntBuffer drawbuffers;
	
	public FBO(int textures, Vector2 size)
	{
		this.size = size;
		this.textures = textures;
		
		FBOData = new int[textures];
		
		genFBO();
		
		drawbuffers = BufferUtils.createIntBuffer(textures);
		for (int i=0;i<textures;i++)
			drawbuffers.put(Attachment.get(i).GL_ATTACHMENT);
		drawbuffers.rewind();
	}
	
	public void bind()
	{
		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport(0, 0, (int) size.x, (int) size.y);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		GL20.glDrawBuffers(drawbuffers);
	}
	
	public int getData(int n)
	{
		while (n < 0) n += textures;
		while (n > textures) n -= textures;
		return FBOData[n];
	}
	
	public void setData(int... ntextures)
	{
		if (ntextures.length != textures)
			throw new RuntimeException("textures != new textures");
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		for (int i=0; i<textures; i++)
		{
			FBOData[i] = ntextures[i];
			EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, Attachment.get(i).FBO_ATTACHMENT, GL11.GL_TEXTURE_2D, FBOData[i], 0);
		}
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
	}
	
	public void release()
	{
		GL11.glPopAttrib();
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
	}
	
	public void destroy()
	{
		EXTFramebufferObject.glDeleteFramebuffersEXT(fbo);
		for (int i:FBOData)
			GL11.glDeleteTextures(i);
	}
	
	private void genFBO()
	{
		fbo = EXTFramebufferObject.glGenFramebuffersEXT();
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		
		for (int i=0; i<textures; i++)
		{
			FBOData[i] = getTex(size);
			EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, Attachment.get(i).FBO_ATTACHMENT, GL11.GL_TEXTURE_2D, FBOData[i], 0);
		}
		
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
	}
	
	public static int getTex(Vector2 size)
	{
		int tex = GL11.glGenTextures();        
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA16, (int) size.x, (int) size.y, 0, GL11.GL_RGBA, GL11.GL_INT, (java.nio.ByteBuffer) null);
		return tex;
	}
	
	public enum Attachment
	{
		ATTACHMENT_0(GL30.GL_COLOR_ATTACHMENT0, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT),
		ATTACHMENT_1(GL30.GL_COLOR_ATTACHMENT1, EXTFramebufferObject.GL_COLOR_ATTACHMENT1_EXT),
		ATTACHMENT_2(GL30.GL_COLOR_ATTACHMENT2, EXTFramebufferObject.GL_COLOR_ATTACHMENT2_EXT),
		ATTACHMENT_3(GL30.GL_COLOR_ATTACHMENT3, EXTFramebufferObject.GL_COLOR_ATTACHMENT3_EXT),
		ATTACHMENT_4(GL30.GL_COLOR_ATTACHMENT4, EXTFramebufferObject.GL_COLOR_ATTACHMENT4_EXT),
		ATTACHMENT_5(GL30.GL_COLOR_ATTACHMENT5, EXTFramebufferObject.GL_COLOR_ATTACHMENT5_EXT),
		ATTACHMENT_6(GL30.GL_COLOR_ATTACHMENT6, EXTFramebufferObject.GL_COLOR_ATTACHMENT6_EXT),
		ATTACHMENT_7(GL30.GL_COLOR_ATTACHMENT7, EXTFramebufferObject.GL_COLOR_ATTACHMENT7_EXT),
		ATTACHMENT_8(GL30.GL_COLOR_ATTACHMENT8, EXTFramebufferObject.GL_COLOR_ATTACHMENT8_EXT),
		ATTACHMENT_9(GL30.GL_COLOR_ATTACHMENT9, EXTFramebufferObject.GL_COLOR_ATTACHMENT9_EXT),
		ATTACHMENT_10(GL30.GL_COLOR_ATTACHMENT10, EXTFramebufferObject.GL_COLOR_ATTACHMENT10_EXT),
		ATTACHMENT_11(GL30.GL_COLOR_ATTACHMENT11, EXTFramebufferObject.GL_COLOR_ATTACHMENT11_EXT),
		ATTACHMENT_12(GL30.GL_COLOR_ATTACHMENT12, EXTFramebufferObject.GL_COLOR_ATTACHMENT12_EXT),
		ATTACHMENT_13(GL30.GL_COLOR_ATTACHMENT13, EXTFramebufferObject.GL_COLOR_ATTACHMENT13_EXT),
		ATTACHMENT_14(GL30.GL_COLOR_ATTACHMENT14, EXTFramebufferObject.GL_COLOR_ATTACHMENT14_EXT),
		ATTACHMENT_15(GL30.GL_COLOR_ATTACHMENT15, EXTFramebufferObject.GL_COLOR_ATTACHMENT15_EXT);
	
		public int GL_ATTACHMENT;
		public int FBO_ATTACHMENT;
		
		private Attachment(int GL_ATTACHMENT, int FBO_ATTACHMENT)
		{
			this.GL_ATTACHMENT = GL_ATTACHMENT;
			this.FBO_ATTACHMENT = FBO_ATTACHMENT;
		}
		
		public static Attachment get(int attachment)
		{
			for (int i=0;i<Attachment.values().length;i++)
				if (i == attachment)
					return Attachment.values()[i];
			throw new RuntimeException("No attachment " + attachment);
		}
	}
}
