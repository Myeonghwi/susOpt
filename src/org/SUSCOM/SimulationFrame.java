package org.SUSCOM;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.SUSCOM.Util.Localization;

public class SimulationFrame extends JFrame{
	
	private JPanel simulationPanel;
	
	private Container contentPane;
	
	private JTextField epField;
	
	private JTextField objField;
	
	private JTextField copyField;
	
	private JTextField outputField;
	
	private JTextField weatherField;
	
	private JButton epBtn;
	
	private JButton objBtn;
	
	private JButton copyBtn;
	
	private JButton outputBtn;
	
	private JButton weatherBtn;
	
	private int textFieldLength = 50;

	private static Localization localization = Localization.getLocalization(
			SimulationFrame.class);
	
	/**
	 * This class is useless.
	 * Probably, It will be deleted.
	 */
	public SimulationFrame() {
		super(localization.getString("title.simulationFrame"));
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		initialize();
		layoutComponents();
		setVisible(true);
	}
	
	protected void initialize() {
		epField = new JTextField(textFieldLength);
		
		epBtn = new JButton(localization.getString("action.epLoad.name"));
		
		weatherField = new JTextField(textFieldLength);
		weatherBtn = new JButton(localization.getString("action.weatherLoad.name"));
		
		objField = new JTextField(textFieldLength);
		objBtn = new JButton(localization.getString("action.objectiveLoad.name"));
		
		copyField = new JTextField(textFieldLength);
		copyBtn = new JButton(localization.getString("action.copyLoad.name"));
		
		outputField = new JTextField(textFieldLength);
		outputBtn = new JButton(localization.getString("action.outputFile.name"));
	}
	
	public void layoutComponents() {
		simulationPanel = new JPanel();
		
		simulationPanel.add(epField);
		simulationPanel.add(epBtn);
		simulationPanel.add(weatherField);
		simulationPanel.add(weatherBtn);
		simulationPanel.add(objField);
		simulationPanel.add(objBtn);
		simulationPanel.add(copyField);
		simulationPanel.add(copyBtn);
		simulationPanel.add(outputField);
		simulationPanel.add(outputBtn);

		contentPane = getContentPane();
		contentPane.add(simulationPanel, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	}
}
