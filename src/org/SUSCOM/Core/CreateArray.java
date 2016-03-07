package org.SUSCOM.Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import org.SUSCOM.MainGUI;
import org.SUSCOM.Algorithms.GA.NSGA2;
import org.SUSCOM.SimulationRun.SimulationRun;

public class CreateArray {

	/**
	 *XXX:There are side effect elements. Can see red line in variables.
	 */
	private double newVariable;

	private double[] objectives;
	
	private int[] newVariablesInt;
	
	private int numberOfvariables;
	
	private String symbol;

	private String[][] variables;

	private String objectiveFunction;
	
	private List<String> newVariableArray;

	public CreateArray() {
		
	}
	
	/**
	 * @param numberOfObjectives
	 * @param numberOfVariables
	 * @param symbol
	 */
	public CreateArray(int numberOfObjectives, int numberOfVariables, String symbol) {

		variables = new String[numberOfVariables][2];
		objectives = new double[numberOfObjectives];
		this.numberOfvariables = numberOfVariables;
		this.symbol = symbol;
		Initialize();
	}
	/**
	 * @param numberOfVariables
	 * @param ObjectiveFunction
	 * @param symbol
	 */
	public CreateArray(int numberOfVariables, String ObjectiveFunction, String symbol) {

		variables = new String[numberOfVariables][2];
		this.numberOfvariables = numberOfVariables;
		this.objectiveFunction = ObjectiveFunction;
		this.symbol = symbol;
		Initialize();
	}
	
	/**
	 * New Double Variables(Discrete)
	 * @param numberOfVariables
	 * @param newVariables
	 * @param ObjectiveFunction
	 * @param symbol
	 */
	public CreateArray(int numberOfVariables, List<String>newVariableArray, String ObjectiveFunction, String symbol) {

		variables = new String[numberOfVariables][2];
		this.numberOfvariables = numberOfVariables;
		this.objectiveFunction = ObjectiveFunction;
		this.newVariableArray = newVariableArray;
		this.symbol = symbol;
		Initialize();
	}
	
	/**
	 * Create 2dimention array with including variable elements
	 * TODO: Divide Discrete into Continuous
	 */
	protected void Initialize() {

		for(int i = 0; i < variables.length; i++) {
			for(int j = 0; j < variables[i].length; j++) {
				if(j % 2 == 0)
					variables[i][j] = symbol;
				else 
					variables[i][j] = newVariableArray.get(i);
			} 
		}
	}
	
	public String[][] getVariables() {
		return variables;
	}
	
	public int getNumberOfVariables() {
		return variables.length;
	}
}
