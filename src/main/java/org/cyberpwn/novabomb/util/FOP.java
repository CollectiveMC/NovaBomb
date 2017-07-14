package org.cyberpwn.novabomb.util;

public interface FOP
{
	public void operate();
	
	public void reverse();
	
	public void log(String op, CharSequence... s);
}
