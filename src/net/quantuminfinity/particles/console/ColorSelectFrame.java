package net.quantuminfinity.particles.console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.quantuminfinity.particles.Renderer;

public class ColorSelectFrame extends JFrame
{
	private static final long serialVersionUID = 8613838179888841767L;

	final JColorChooser colorChooser;
	Renderer renderer;
	
	public ColorSelectFrame(Renderer r)
	{
		super("Particle color picker");
		this.renderer = r;
		setLayout(new FlowLayout());
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});

		colorChooser = new JColorChooser();
		colorChooser.setColor((int) (Settings.color.x * 255), (int) (Settings.color.y * 255), (int) (Settings.color.z * 255));
		colorChooser.setPreferredSize(new Dimension(300, 300));
		ColorSelectionModel model = colorChooser.getSelectionModel();
		
		ChangeListener changeListener = new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Color c = colorChooser.getColor();
				Settings.color.set(c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f);
				renderer.reqReloadShaderSettings();
			}
		};
		model.addChangeListener(changeListener);
		
		add(colorChooser);
		pack();
	}
}
