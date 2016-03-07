package org.SUSCOM;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.SUSCOM.Algorithms.GA.NSGA;
import org.SUSCOM.Algorithms.GA.NSGA2;
import org.SUSCOM.Core.CreateArray;
import org.SUSCOM.Core.Replacement;
import org.SUSCOM.Util.Localization;


public class ActionFactory {

	private MainGUI mainGUI;

	private CreateArray createArray;

	private Replacement replacement;

	private VariableFrame variableFrame;

	private SimulationFrame simulationFrame;

	private static Localization localization = Localization.getLocalization(
			ActionFactory.class);

	private static String EXTENSION = 
			"." + localization.getString("file.extension").toLowerCase();

	private static FileFilter FILTER = new FileNameExtensionFilter(
			localization.getString("file.extension.description"),
			localization.getString("file.extension"));

	private String FileLocation;

	private Action runAction;

	private Action aboutAction;

	private Action clearAction;

	private Action cancelAction;

	private Action variableAction;

	private Action fileOpenAction;

	private Action simulationAction;

	private boolean isFile = false;

	private int numberOfVariables;

	public ActionFactory() {
		initialize();
	}

	/**
	 * iNITIALIZES THE ACTION USED BY THIS ACTION FACTORY.
	 */
	protected void initialize() {

		runAction = new AbstractAction() {
			private static final long serialVersionUID = -1909996187887919230L;

			{
				putValue(Action.NAME, localization.getString("action.run.name"));
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.run.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {
				//TODO: will make Try-Catch sentence
				if(!isFile)
					JOptionPane.showMessageDialog(null, localization.getString("text.input.idf"));
				else {
					NSGA nsga = new NSGA(3,2);
					nsga.Run();
				}
			}
		};

		cancelAction = new AbstractAction() {
			private static final long serialVersionUID = 6667076082827906472L;

			{
				putValue(Action.NAME, localization.getString("action.cancel.name"));
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.cancel.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {

			}
		};

		clearAction = new AbstractAction() {
			private static final long serialVersionUID = 5291581694356532809L;

			{
				putValue(Action.NAME, localization.getString("action.clear.name"));
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.clear.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {

			}
		};

		fileOpenAction = new AbstractAction() {
			private static final long serialVersionUID = 8388268233198826720L;

			{
				putValue(Action.NAME, localization.getString("action.fileOpen.name"));
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.fileOpen.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {

				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileFilter(FILTER);

				int result = filechooser.showOpenDialog(null);

				if(result == JFileChooser.APPROVE_OPTION) {
					File SelectedFile = filechooser.getSelectedFile();
					FileLocation = SelectedFile + "";
					JOptionPane.showMessageDialog(null,"Success : "+FileLocation);
					isFile = true;
				}

			}
		};

		variableAction = new AbstractAction() {
			private static final long serialVersionUID = 8388268233198827720L;

			{
				putValue(Action.NAME, localization.getString("action.variable.name"));
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.variable.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {
				VariableFrame.getInstace();
			}
		};

		aboutAction = new AbstractAction() {
			private static final long serialVersionUID = 8288268533198827720L;

			{
				putValue(Action.NAME, localization.getString("action.about.name"));
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.about.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, localization.getString("text.about"));
			}
		};

		simulationAction = new AbstractAction() {
			private static final long serialVersionUID = 8288268533198827720L;

			{
				putValue(Action.SHORT_DESCRIPTION, localization.getString("action.simulFrame.description"));
			}

			@Override
			public void actionPerformed(ActionEvent event) {
				simulationFrame = new SimulationFrame();
			}
		};

	}

	public Action getRunAction() {
		return runAction;
	}

	public Action getAboutAction() {
		return aboutAction;
	}

	public Action getClearAction() {
		return clearAction;
	}

	public Action getCancelAction() {
		return cancelAction;
	}

	public Action getVariableAction() {
		return variableAction;
	}

	public Action getOpenFileAction() {
		return fileOpenAction;
	}
	public Action getSimulationAction() {
		return simulationAction;
	}
	public String getSelectedFile() {
		return FileLocation;
	}
	public int getNumberOfVariables() {
		return numberOfVariables
				= variableFrame.getNumberOfVariables();
	}

}



















/*
	public void StartHS() {

		HarmonySearch hs = new HarmonySearch();

		hs.setBW(.2);
		hs.setNVAR(2);
		hs.setHMCR(.8);
		hs.setHMS(80);
		hs.setPAR(.4);
		hs.setMaxIteration(10000);
		hs.setObjectives(2);

		double[] low = { -5.0, -5.0 };
		double[] high = { 5.0, 5.0 };

		hs.setBounds(low, high);
		hs.mainLoop();

	}
 */

