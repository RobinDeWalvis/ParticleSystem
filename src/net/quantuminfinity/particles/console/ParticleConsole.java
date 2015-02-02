package net.quantuminfinity.particles.console;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.quantuminfinity.particles.Renderer;

public class ParticleConsole extends JFrame
{
	private static final long serialVersionUID = -3104396871233022992L;

	Renderer renderer;
	JButton startButton, colorButton, resetButton, setGravityButton;
	JSpinner textureSizeIn, gravity;
	JLabel staticSettings, texPowText, uniformSettings, gravityText;
	JCheckBox useNoise, useGravity, useAge;
	ColorSelectFrame csf = null;
	
	public ParticleConsole(Renderer r)
	{
		super("Particle System Console");
		this.renderer = r;
		csf = new ColorSelectFrame(renderer);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);		
		setup();
		setSize(250,300);
		setVisible(true);
		setResizable(false);
	}

	private void setup()
	{
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});

		startButton = new JButton("Update settings");
		startButton.setPreferredSize(new Dimension(200, 30));
		startButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Settings.textureSize = (Integer) textureSizeIn.getValue();
				renderer.scheduleReloadSystem();
			}
		});
		
		add(startButton);
		
		resetButton = new JButton("Reset settings");
		resetButton.setPreferredSize(new Dimension(200, 30));
		resetButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Settings.resetValues();
				renderer.scheduleReloadSystem();
			}
		});
		
		add(resetButton);
		
		staticSettings = new JLabel("Static settings (requires restart):");
		staticSettings.setPreferredSize(new Dimension(200, 15));
		staticSettings.setToolTipText("Settings requiring a restart");
		add(staticSettings);
		
		texPowText = new JLabel("Texture size:");
		texPowText.setToolTipText("Square root of # particles");
		add(texPowText);
		
		textureSizeIn = new JSpinner(new SpinnerNumberModel(Settings.textureSize, 64, 4096, 1));
		textureSizeIn.setToolTipText("Square root of # particles");
		add(textureSizeIn);
		
		uniformSettings = new JLabel("Uniform settings:");
		uniformSettings.setToolTipText("Settings applied to the shaders");
		uniformSettings.setPreferredSize(new Dimension(200, 15));
		add(uniformSettings);
		
		useNoise = new JCheckBox("Use noise");
		useNoise.setSelected(Settings.useNoise);
		useNoise.setToolTipText("Let particles be affected by noise");
		useNoise.setPreferredSize(new Dimension(200, 15));
		useNoise.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Settings.useNoise = useNoise.isSelected();
				renderer.reqReloadShaderSettings();
			}
		});
		add(useNoise);
		
		useGravity = new JCheckBox("Use gravity");
		useGravity.setToolTipText("Let particles be affected by gravity");
		useGravity.setSelected(Settings.useGravity);
		useGravity.setPreferredSize(new Dimension(200, 15));
		useGravity.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Settings.useGravity = useGravity.isSelected();
				renderer.reqReloadShaderSettings();
			}
		});
		add(useGravity);
		 
		gravityText = new JLabel("Gravity strength:");
		gravityText.setToolTipText("Strength of gravity");
		add(gravityText);
		
		gravity = new JSpinner(new SpinnerNumberModel(-Settings.gravity*10000, -50, 50, 1));
		gravity.setToolTipText("Strength of gravity");
		gravity.addChangeListener(new ChangeListener()
		{      
			@Override
			public void stateChanged(ChangeEvent e)
			{
				double tmp = (Double) gravity.getValue();
				Settings.gravity = (float) (-tmp/10000.);
				renderer.reqReloadShaderSettings();
			}
		});
		add(gravity);
		
		useAge = new JCheckBox("Use age");
		useAge.setSelected(Settings.useGravity);
		useAge.setToolTipText("When particles reach a certain age, they respawn");
		useAge.setPreferredSize(new Dimension(200, 15));
		useAge.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Settings.useAge = useAge.isSelected();
				renderer.reqReloadShaderSettings();
			}
		});
		add(useAge);
		
		colorButton = new JButton("Set particle color");
		colorButton.setPreferredSize(new Dimension(150, 20));
		colorButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				csf.setVisible(true);
			}
		});
		
		add(colorButton);
	}
}
