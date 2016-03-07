package org.SUSCOM.Algorithms.GA;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.SUSCOM.ActionFactory;
import org.SUSCOM.MainGUI;
import org.SUSCOM.VariableFrame;
import org.SUSCOM.Core.CreateArray;
import org.SUSCOM.Core.Replacement;
import org.SUSCOM.File.FileHandler;
import org.SUSCOM.File.FileInputOut;
import org.SUSCOM.SimulationRun.SimulationRun;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;


public class NSGA extends AbstractProblem {

	private int numberOfVariables;

	private int numberOfObjectives;

	private List<Double> upperArray;

	private List<Double> lowerArray;

	private List<Boolean> distinctArray;

	private List<String> newVariableArray;

	private CreateArray createArray;

	private Replacement replacement;

	private FileHandler fileHandler;

	private ActionFactory actionFactory;

	private DecimalFormat decimalFormat;

	private SimulationRun simulationRun;


	//FIXME: SuperKeyword is very wrong. Can not push variable parameter.
	public NSGA() {
		super(3, 2);
		this.numberOfVariables = super.numberOfVariables;
		this.numberOfObjectives = super.numberOfObjectives;
		initialize();
	}

	public NSGA(int numberOfVariables, int numberOfObjectives) {
		super(numberOfVariables, numberOfObjectives);
		this.numberOfVariables = numberOfVariables;
		this.numberOfObjectives = numberOfObjectives;
		initialize();
	}

	public void initialize() {
		fileHandler = new FileHandler();
		//simulationRun = new SimulationRun();

		decimalFormat = new DecimalFormat("0.00");
		newVariableArray = new ArrayList<String>();

		this.lowerArray = VariableFrame.getInstace().getLowerBound();
		this.upperArray = VariableFrame.getInstace().getUpperBound();
		this.distinctArray = VariableFrame.getInstace().getDistinctList();
	}

	/**
	 * Using LowerBound array and UpeerBound array, create new variables.
	 * TODO:Divide Continuous Part into Discrete Part
	 */
	@Override
	public Solution newSolution() {
		Solution solution = 
				new Solution(numberOfVariables, numberOfObjectives);

		for(int i = 0; i < numberOfVariables; i++) {
			/*
			 * Discrete Variable Generator
			 * Casting real(double) to integer
			 */
			if(distinctArray.get(i))
				solution.setVariable(i, EncodingUtils.newInt
						(lowerArray.get(i).intValue(), upperArray.get(i).intValue()));
			/**
			 * Continuous Variable Generator
			 */
			else
				solution.setVariable(i, EncodingUtils.newReal
						(lowerArray.get(i), upperArray.get(i)));
		}

		return solution;
	}

	//TODO : Divide Continuous Part into Discrete Part
	@Override
	public void evaluate(Solution solution) {

		for(int i = 0; i < numberOfVariables; i++) {
			if(distinctArray.get(i)) {
				newVariableArray.add(
						String.valueOf(EncodingUtils.getInt(solution.getVariable(i))));
			}
			else{
				newVariableArray.add(
						String.valueOf(EncodingUtils.getReal(solution.getVariable(i))));
			}
		}

		double f1 = 0.0;
		double f2 = 0.0;

		/*
		for(int i = 0; i < numberOfVariables; i++){
			newVariable[i] = Double.parseDouble
					(decimalFormat.format(newVariable[i]));
		}*/
		createArray = new CreateArray(
				numberOfVariables, 
				newVariableArray,
				MainGUI.getInstace().getObjectiveFunction(),
				MainGUI.getInstace().getSymbol());
		
		try {
			Replacement replacement = new Replacement(
					createArray.getVariables());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//TODO : From here, reproduce code simply.
		//TODO : Re design the SimulationRun class
		new SimulationRun();

		fileHandler.fileDelete("C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf");
		fileHandler.fileCopy("C:\\Users\\LMH\\Desktop\\elementABS.idf","C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf");

		FileInputOut run = new FileInputOut();
		 
		//run.outputHandler("C:\\workspace\\SUSCOM\\ExampleFiles\\Outputs\\elementABS.csv", newVariable[0], newVariable[1], newVariable[2]);
		
		f1 = run.getHC_ToT();
		f2 = run.getPPD_ToT();
		 
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
	}

	/**
	 * Run method. You can modify setting of algorithm.
	 * TODO: Create GUI to set algorithm option.
	 */
	public void Run() {

		NondominatedPopulation result = new Executor()
		.withProblemClass(NSGA.class)
		.withAlgorithm("NSGA2")
		.withMaxEvaluations(MainGUI.getInstace().getIteration())
		.withProperty("populationSize", 50)
		.run();

		System.out.format("Objective1  Objective2%n");

		for (Solution solution : result) {
			System.out.format("%.4f"+"\t"+"%.4f%n",
					solution.getObjective(0),
					solution.getObjective(1));
		}
	}

}
