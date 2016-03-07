package org.SUSCOM.Algorithms.MOPSO;

import java.io.IOException;
import java.text.DecimalFormat;
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


public class MOPSO extends AbstractProblem {

	private long start;

	private int numberOfVariables;

	private int numberOfObjectives;

	private List<Double> upperArray;

	private List<Double> lowerArray;

	private Replacement replacement;

	private CreateArray createArray;

	private FileHandler fileHandler;

	private ActionFactory actionFactory;

	private DecimalFormat decimalFormat;

	private SimulationRun simulationRun;


	public MOPSO() {
		super(3, 2);
		this.numberOfVariables = super.numberOfVariables;
		this.numberOfObjectives = super.numberOfObjectives;
		initialize();
	}

	public MOPSO(int numberOfVariables, int numberOfObjectives) {
		super(numberOfVariables, numberOfObjectives);
		this.numberOfVariables = numberOfVariables;
		this.numberOfObjectives = numberOfObjectives;
		initialize();
	}

	public void initialize() {
		fileHandler = new FileHandler();
		simulationRun = new SimulationRun();

		this.lowerArray = VariableFrame.getInstace().getLowerBound();
		this.upperArray = VariableFrame.getInstace().getUpperBound();


	}

	@Override
	public Solution newSolution() {
		Solution solution = 
				new Solution(numberOfVariables, numberOfObjectives);
		for(int i = 0; i < numberOfVariables; i++)
			solution.setVariable(i, EncodingUtils.newReal
					(lowerArray.get(i), 
							upperArray.get(i)));

		return solution;
	}

	@Override
	public void evaluate(Solution solution) {
		double[] newVariable = EncodingUtils.getReal(solution);

		double f1 = 0.0;
		double f2 = 0.0;

		decimalFormat = new DecimalFormat("0.00");

		for(int i = 0; i < numberOfVariables; i++){
			newVariable[i] = Double.parseDouble
					(decimalFormat.format(newVariable[i]));
		}

		createArray = new CreateArray(
				numberOfVariables, 
				newVariable,
				MainGUI.getInstace().getObjectiveFunction(),
				MainGUI.getInstace().getSymbol());

		try {
			Replacement replacement = 
					new Replacement(createArray.getVariables());
		} catch (IOException e) {
			e.printStackTrace();
		}

		new SimulationRun();

		fileHandler.fileDelete("C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf");
		fileHandler.fileCopy("C:\\Users\\LMH\\Desktop\\elementABS.idf","C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf");

		FileInputOut run = new FileInputOut();

		run.outputHandler("C:\\workspace\\SUSCOM\\ExampleFiles\\Outputs\\elementABS.csv", newVariable[0], newVariable[1], newVariable[2]);

		f1 = run.getHC_ToT();
		f2 = run.getPPD_ToT();

		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
	}

	public void Run() {

		NondominatedPopulation result = new Executor()
		.withProblemClass(MOPSO.class)
		.withAlgorithm("MOPSO")
		.withMaxEvaluations(MainGUI.getInstace().getIteration())
		.withProperty("populationSize", 50)
		.run();

		//display the results
		System.out.format("Objective1  Objective2%n");

		for (Solution solution : result) {
			System.out.format("%.4f"+"\t"+"%.4f%n",
					solution.getObjective(0),
					solution.getObjective(1));
		}
	}

}
