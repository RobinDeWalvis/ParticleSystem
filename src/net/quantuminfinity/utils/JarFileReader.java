package net.quantuminfinity.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class JarFileReader
{	
	public static String read(String file)
	{
		String out = "";
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader( JarFileReader.class.getResourceAsStream(file)));
			String line;
			while((line = reader.readLine()) != null)
				out += line + "\n";
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		return out;
	}
}
