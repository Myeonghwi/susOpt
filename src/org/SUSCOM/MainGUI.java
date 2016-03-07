package org.SUSCOM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.SUSCOM.DB.ConnectMSSQLServer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;



public class MainGUI extends JFrame {

	private int row = 0;

	private JTable csvTable;

	private JFreeChart chart;

	private JPanel resultPlot;

	private JPanel NSGA2;

	private JPanel MOHS;

	private JPanel MOPSO;

	private ChartPanel chartPanel;

	private String xAxisLabel = "Objective(1)";

	private String yAxisLabel = "Objective(2)";

	private String PlotTitle = "SUSCOM";

	private String line;

	private String[] arr;

	private String[] comboboxItem = {"NSGAⅡ", "MOHS", "MOPSO"};

	private JButton runBtn;

	private JButton clearBtn;

	private JButton cancelBtn;

	private JButton variableBtn;

	private JButton openfileBtn;

	private JButton simulationBtn;

	private JMenuItem aboutBtn;

	private ImageIcon yonseiIcon;

	private JTextField symbolField;

	private JTextField objField;

	private JTextField iterationField;

	private ActionFactory actionFactory;

	private ConnectMSSQLServer connectDatabase;

	private static MainGUI uniqueInstance;


	MainGUI() {
		/**
		 * Window Main Frame : 1280 X 800
		 * Resize : false
		 */
		super("Multi-Objective Optimization Program");
		setSize(1280, 800);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initialize();
		layoutMenu();
		layoutComponents();
		layoutGetResult();

		setVisible(true);
	}

	/**
	 * Singleton Pattern
	 * Due to constructor, create window frame repeatedly.
	 * To prevent this problem, specify singleton pattern.
	 * @return
	 */
	public static MainGUI getInstace() {
		if(uniqueInstance == null)
			uniqueInstance = new MainGUI();

		return uniqueInstance;
	}


	protected void initialize() {
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//TODO: Make Try-Catch sentence.
		}

		actionFactory = new ActionFactory();
		connectDatabase = new ConnectMSSQLServer();

		csvTable = new JTable();
		resultPlot = new JPanel();
		symbolField = new JTextField(10);
		iterationField = new JTextField(10);
		objField = new JTextField(30);

		yonseiIcon = new ImageIcon("C:/workspace/SUSCOM/src/img/energy.png");

		runBtn = new JButton(actionFactory.getRunAction());
		clearBtn = new JButton(actionFactory.getClearAction());
		cancelBtn = new JButton(actionFactory.getCancelAction());
		aboutBtn = new JMenuItem(actionFactory.getAboutAction());
		variableBtn = new JButton(actionFactory.getVariableAction());
		openfileBtn = new JButton(actionFactory.getOpenFileAction());
		simulationBtn = new JButton(actionFactory.getSimulationAction());

		simulationBtn.setIcon(yonseiIcon);
	}



	protected void layoutMenu() {
		JMenu file = new JMenu("파일");
		file.add(new JMenuItem("저장하기"));
		file.add(new JMenuItem("불러오기"));
		file.addSeparator();
		file.add(new JMenuItem("나가기"));

		JMenu help = new JMenu("도움말");
		help.add(aboutBtn);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(help);
		setJMenuBar(menuBar);
	}

	protected void layoutPlot() {

		PlotOrientation pOrientation = PlotOrientation.VERTICAL;

		chart = ChartFactory.createScatterPlot(PlotTitle, xAxisLabel, yAxisLabel, 
				new ResultChart().ScatterPlot(),
				pOrientation, true, true, false);


		Shape ellipse2D = new Ellipse2D.Double(0, 0, 5.0, 5.0);

		XYPlot xyPlot = (XYPlot) chart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);

		XYItemRenderer renderer = xyPlot.getRenderer();
		renderer.setSeriesShape(0, ellipse2D);
		renderer.setSeriesPaint(0, Color.blue);
	}


	public void layoutLoad() {

		DefaultTableModel model = (DefaultTableModel)csvTable.getModel();

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader("C:\\Users\\LMH\\Desktop\\elementABS.csv"));

			line = bufferedReader.readLine(); //제목
			arr = line.split("\t");
			for(int i = 0; i < arr.length; i++)
				model.addColumn(arr[i]);


			while ((line = bufferedReader.readLine()) != null) {	//한라인을 읽어들임
				arr = line.split("\t");
				model.addRow(new Object[0]);

				for(int i = 0; i < arr.length; i++)
					model.setValueAt(arr[i], row, i);
				row++;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void layoutGetResult() {

		DefaultTableModel model = (DefaultTableModel)csvTable.getModel();
		JTable table = new JTable(model);
		JScrollPane jsp = new JScrollPane(table);

		//무언가 들어왔을 때 
		table.repaint();
		table.updateUI();
		jsp.getViewport().repaint();
	}

	protected void layoutComponents() {
		GridBagConstraints label = new GridBagConstraints();
		label.gridx = 0;
		label.gridy = GridBagConstraints.RELATIVE;
		label.anchor = GridBagConstraints.NORTHWEST;
		label.insets = new Insets(0, 5, 5, 2);


		GridBagConstraints field = new GridBagConstraints();
		field.gridx = 2;
		field.gridy = GridBagConstraints.RELATIVE;
		field.fill = GridBagConstraints.HORIZONTAL;
		field.weightx = 0;
		field.insets = new Insets(0, 0, 5, 5);

		GridBagConstraints button = new GridBagConstraints();
		button.gridx = 0;
		button.gridwidth = 0;
		button.fill = GridBagConstraints.HORIZONTAL;
		button.insets = new Insets(0, 0, 4, 0);

		GridBagConstraints energyLabel = new GridBagConstraints();
		//		energyLabel.gridx = 2;
		//		energyLabel.gridy = GridBagConstraints.RELATIVE;
		//		energyLabel.anchor = GridBagConstraints.NORTHEAST;
		//		energyLabel.insets = new Insets(0, 0, 5, 2);

		JPanel funcPanel = new JPanel(new GridLayout());
		funcPanel.setBorder(BorderFactory.createTitledBorder("Objective Function"));
		funcPanel.add(objField, BorderLayout.CENTER);
		objField.setText("Input *.idf File...");


		NSGA2 = new JPanel(new GridBagLayout());
		NSGA2.add(new JLabel("Population"), label);
		NSGA2.add(new JTextField(15),field);
		NSGA2.add(new JLabel("Crossover"), label);
		NSGA2.add(new JTextField(15),field);
		NSGA2.add(new JLabel("Mutation"), label);
		NSGA2.add(new JTextField(15),field);

		MOHS = new JPanel(new GridBagLayout());
		MOHS.add(new JLabel("HMCR"), label);
		MOHS.add(new JTextField(15),field);
		MOHS.add(new JLabel("PAR"), label);
		MOHS.add(new JTextField(15),field);

		MOPSO = new JPanel(new GridBagLayout());
		MOPSO.add(new JLabel("Particle"), label);
		MOPSO.add(new JTextField(15),field);
		MOPSO.add(new JLabel("Mutation"), label);
		MOPSO.add(new JTextField(15),field);


		JTabbedPane optionPanel = new JTabbedPane();
		optionPanel.addTab("NSGAⅡ", NSGA2);
		optionPanel.addTab("MOHS", MOHS);
		optionPanel.addTab("MOPSO", MOPSO);


		JPanel JEPane = new JPanel(new GridLayout());
		JEPane.setBorder(BorderFactory.createTitledBorder("PBECAS, E+, JE+"));
		JEPane.add(new JPanel(), energyLabel);
		JEPane.add(simulationBtn, button);
		JEPane.add(new JButton(new ImageIcon("C:/workspace/SUSCOM/src/img/yonsei.png")), button);
		JEPane.add(new JButton(new ImageIcon("C:/workspace/SUSCOM/src/img/je.png")), button);

		JPanel selectionPanel = new JPanel(new GridLayout(1, 3));
		selectionPanel.add(funcPanel);
		selectionPanel.add(optionPanel);
		selectionPanel.add(JEPane);

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER));

		buttonPane.add(runBtn);
		buttonPane.add(clearBtn);
		buttonPane.add(cancelBtn);
		buttonPane.add(openfileBtn);
		buttonPane.add(variableBtn);

		///////////////////////////////////////////////////패널설정
		JPanel setting = new JPanel(new GridBagLayout());
		setting.setBorder(BorderFactory.createTitledBorder("Setting"));
		setting.add(new JLabel("Algorithm"), label);
		setting.add(new JComboBox(comboboxItem), field); //object 넣어야함

		setting.add(symbolField, field);
		setting.add(new JLabel("Symbol"), label);
		setting.add(iterationField, field);
		setting.add(new JLabel("Iteration"), label);

		setting.add(buttonPane, button);
		setting.add(new JPanel(), button);
		///////////////////////////////////////////////////

		JPanel wholePanel = new JPanel();
		wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.X_AXIS));
		wholePanel.add(setting);
		wholePanel.add(selectionPanel);
		wholePanel.setMinimumSize(setting.getPreferredSize());
		wholePanel.setPreferredSize(setting.getPreferredSize());

		///////////////////////////////////////////////////////

		JPanel csvPanel = new JPanel();
		csvPanel.setLayout(new GridLayout());
		//csvPanel.add(new JTable(), csvTable);
		//getContentPane().add(csvTable);
		JScrollPane scroll = new JScrollPane(csvTable);
		scroll.setBounds(20, 200, 500, 500);
		getContentPane().add(scroll);

		//////////////////////////////////////////////////////

		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(550, 200, 700, 500);
		csvTable.add(chartPanel);
		getContentPane().add(chartPanel);

		//////////////////////////////////////////////////////

		JSplitPane splitPane = 
				new JSplitPane(JSplitPane.VERTICAL_SPLIT, wholePanel, csvPanel) {
			private static final long serialVersionUID = 1L;
			private final int location = -1;
			{
				setDividerLocation(location);
			}

			@Override
			public int getDividerLocation() {
				return location;
			}

			@Override
			public int getLastDividerLocation() {
				return location;
			}

		};
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(splitPane, BorderLayout.CENTER);
	}


	public String getSymbol() {
		return symbolField.getText().toString();
	}

	public String getObjectiveFunction() {
		return objField.getText().toString();
	}

	public int getIteration() {
		return Integer.parseInt(iterationField.getText());
	}
}
