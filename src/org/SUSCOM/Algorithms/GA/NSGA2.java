package org.SUSCOM.Algorithms.GA;

import java.io.IOException;
import java.text.DecimalFormat;

import org.SUSCOM.ActionFactory;
import org.SUSCOM.File.FileInputOut;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;


public class NSGA2 extends AbstractProblem {

	public NSGA2() {
		super(3, 2);	//numberOfVariables, numberOfObjectives
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(numberOfVariables,
				numberOfObjectives);


		solution.setVariable(0, EncodingUtils.newReal(0.1, 1.0)); //upperBound & lowBound
		solution.setVariable(1, EncodingUtils.newReal(1.0, 5.0));
		solution.setVariable(2, EncodingUtils.newReal(0.1, 0.7));
		
		return solution;
	}
	@Override
	public void evaluate(Solution solution) {	//Core part
		double[] x = EncodingUtils.getReal(solution);
		double f1 = 0.0;
		double f2 = 0.0;
		
		DecimalFormat df = new DecimalFormat("0.0");
		x[0] = Double.parseDouble(df.format(x[0]));
		x[1] = Double.parseDouble(df.format(x[1]));
		x[2] = Double.parseDouble(df.format(x[2]));
		////////////////////////////////////////계산되어 나온 설계변수 값들을 array에 저장
		FileInputOut run = new FileInputOut();
		
		try {
			run.fileHandler("C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf",x[0],x[1],x[2]);
			//여기에 wait
			//run.outputHandler("C:\\workspace\\SUSCOM\\ExampleFiles\\Outputs\\elementABS.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		////////////////////////////////////////
		
		////////////////////////////////////////시뮬레이션 호출부
		
		////////////////////////////////////////목적함수 계산부
		//f1 = Math.pow(x[0], 2) + 2 + x[0];
		//f2 = Math.pow(x[0], 2) * x[0];
		
		f1 = run.getHC_ToT();
		f2 = run.getPPD_ToT();
		
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		
		////////////////////////////////////////출력부(우선 각 function계산 결과값만 출력, 나중에 .csv로 출력)
	}

	public void Start_NSGA2() {
		
		NondominatedPopulation result = new Executor()
		.withProblemClass(NSGA2.class)
		.withAlgorithm("NSGA2")
		.withMaxEvaluations(1000) //default 1000
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
