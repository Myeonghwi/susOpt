package org.SUSCOM;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.SUSCOM.Util.Localization;

public class VariableFrame extends JFrame{

	private JButton addBtn;

	private JButton delBtn;

	private JButton confirmBtn;

	private JPanel variablePanel;

	private Container contentPane;

	private Stack<JComboBox> comboObjectStack;

	private Stack<JTextField> lowerObjectStack;

	private Stack<JTextField> upperObjectStack;

	private List<Boolean> distinctArray;

	private List<Double> lowerArray;

	private List<Double> upperArray;

	private int numberOfVariables;

	private int textFieldLength = 30;

	private static String[] comboItem = {"Integer Type", "Real Type"};

	private static VariableFrame uniqueInstance;

	private static final long serialVersionUID = 8288268533198823420L;

	private static Localization localization = Localization.getLocalization(
			VariableFrame.class);


	public VariableFrame() {
		super(localization.getString("title.variableFrame"));
		setSize(350, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initialize();
		layoutComponents();
		setVisible(true);
	}

	/**
	 * -Singleton pattern-
	 * Due to constructor, create window frame repeatedly.
	 * To prevent this problem, specify singleton pattern.
	 * @return
	 */
	public static VariableFrame getInstace() {
		if(uniqueInstance == null)
			uniqueInstance = new VariableFrame();

		return uniqueInstance;
	}


	public void initialize() {
		numberOfVariables = 0;

		lowerArray = new ArrayList<Double>();
		upperArray = new ArrayList<Double>();
		distinctArray = new ArrayList<Boolean>();

		comboObjectStack = new Stack<JComboBox>();
		lowerObjectStack = new Stack<JTextField>();
		upperObjectStack = new Stack<JTextField>();

		addBtn = new JButton(localization.getString("action.addvariable.name"));
		delBtn = new JButton(localization.getString("action.delvariable.name"));
		confirmBtn = new JButton(localization.getString("action.convariable.name"));
	}

	//TODO: Implement Try-Catch sentence
	//TODO: Make a scroll
	public void layoutComponents() {
		variablePanel = new JPanel();
		variablePanel.add(addBtn);
		variablePanel.add(delBtn);
		variablePanel.add(confirmBtn);

		contentPane = getContentPane();
		contentPane.add(variablePanel, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == addBtn){
					lowerObjectStack.push(new JTextField(textFieldLength));
					upperObjectStack.push(new JTextField(textFieldLength));

					variablePanel.add((Component) lowerObjectStack.peek());
					variablePanel.add((Component) upperObjectStack.peek());

					lowerObjectStack.peek().setText(("LowerBound " + numberOfVariables));
					upperObjectStack.peek().setText(("UpperBound " + numberOfVariables));

					comboObjectStack.push(new JComboBox(comboItem));
					variablePanel.add((Component) comboObjectStack.peek());

					variablePanel.updateUI();
					numberOfVariables++;
				}
			}
		});

		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == delBtn){
					variablePanel.remove((Component) lowerObjectStack.pop());
					variablePanel.remove((Component) upperObjectStack.pop());
					variablePanel.remove((Component) comboObjectStack.pop());

					variablePanel.updateUI();
					numberOfVariables--;
				}
			}
		});

		//TODO: Divide Continuous into Discrete.
		//TODO: Create Array Rotation Module. 
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == confirmBtn){
					while(!lowerObjectStack.isEmpty()) {
						lowerArray.add(Double.parseDouble(
								lowerObjectStack.pop().getText()));
					}

					while(!upperObjectStack.isEmpty()) {
						upperArray.add(Double.parseDouble(
								upperObjectStack.pop().getText()));						
					}

					while(!comboObjectStack.isEmpty()) {
						String str = (String)comboObjectStack.pop().getSelectedItem();			
						if(str.equals("Integer Type"))
							distinctArray.add(true);
						else
							distinctArray.add(false);
					}
					inverseDoubleList(lowerArray);
					inverseDoubleList(upperArray);
					inverseBoolList(distinctArray);
				}
			}
		});

	}

	/**
	 * Due to stack function, It need to inverse array.
	 * @param array
	 */
	public void inverseDoubleList(List<Double> array) {
		Collections.reverse(array);
	}
	
	public void inverseBoolList(List<Boolean> array) {
		Collections.reverse(array);
	}
	
	public List<Double> getLowerBound() {
		return lowerArray;
	}

	public List<Double> getUpperBound() {
		return upperArray;
	}
	
	public List<Boolean> getDistinctList() {
		return distinctArray;
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}
}
