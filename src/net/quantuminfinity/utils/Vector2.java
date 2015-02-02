package net.quantuminfinity.utils;


public class Vector2
{
  public float x;
  public float y;
  
  public Vector2(float x, float y)
  {
    this.setX(x);
    this.setY(y);
  }
  
  public Vector2(Vector2 copyFrom)
  {
    this.setX(copyFrom.getX());
    this.setY(copyFrom.getY());
  }
  
  public Vector2 neg()
  {
	 setX(getX() * -1);
	 setY(getY() * -1);
	  return this;
  }
  
  public Vector2 clone()
  {
	  return new Vector2(this);
  }
  
  public static Vector2 neg(Vector2 v)
  {
	  
	  return new Vector2(v.getX()*-1,v.getY()*-1);
  }
  
  
  public Vector2 set(float x, float y)
  {
	    this.setX(x);
	    this.setY(y);
		return this;
  }
  
  public Vector2 add(Vector2 v)
  {
	  this.setX(v.getX() + getX());
	  this.setY(v.getY() + getY());
	return this;
  }
  
  public static Vector2 add(Vector2 v, Vector2 v1)
  {
	return new Vector2(v.getX() + v1.getX(),v.getY() + v1.getY());
  }
  
  public static Vector2 sub(Vector2 v, Vector2 v1)
  {
	  return new Vector2(v.getX() - v1.getX(),v.getY() - v1.getY());
  }
  
  public Vector2 sub(Vector2 v)
  {
	  this.setX(getX() - v.getX());
	  this.setY(getY() - v.getY());
	return this;
  }
  
  public Vector2 mul(Vector2 v)
  {
	  this.setX(v.getX() * getX());
	  this.setY(v.getY() * getY());
	return this;
  }
  
  public Vector2 mul(float v)
  {
	  this.setX(v * getX());
	  this.setY(v * getY());
	return this;
  }
  
  public Vector2 div(Vector2 v)
  {
	  this.setX(getX() / v.getX());
	  this.setY(getY() / v.getY());
	return this;
  }
  
  public float length()
  {
	return (float) Math.sqrt(getX()*getX() + getY()*getY());
  }
  
  public Vector2 setLength(Float l)
  {
	  setX((float) (l*Math.cos(Math.toRadians(getAngDeg()))));
	  setY((float) (l*Math.sin(Math.toRadians(getAngDeg()))));
	  return this;
  }
  
  public float getAngDeg(){
	  return (float) Math.toDegrees(Math.atan2(getY(), getX()));
  }

	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public Vector2 normalise()
	{
		x /= length();
		y /= length();
		return this;
	}
}