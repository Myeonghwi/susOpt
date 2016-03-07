package org.SUSCOM.Algorithms.MOHS;

public class HarmonySearch {

	private int NVAR;
	private int HMS;
	private int maxIter;
	private double PAR;
	private double BW;
	private double HMCR;
	private int nObjectives;
	private double low[];
	private double high[];
	private double NCHV[];
	private double bestFitHistory[];
	private double bestHarmony[];
	private double worstFitHistory[];
	private double HM[][];
	private double Pareto[][];
	static int generation = 0;
	static int iteration = 0;
	static double resultData[];
	private boolean terminationCriteria = true;

	RandomGenerator randGen = new RandomGenerator(1234);

	public void setMaxIteration(int maxIter) {
		this.maxIter = maxIter;
	}

	public void setNVAR(int NVAR) {
		this.NVAR = NVAR;
	}

	public void setPAR(double PAR) {
		this.PAR = PAR;
	}

	public void setHMCR(double HMCR) {
		this.HMCR = HMCR;
	}

	public void setBW(double BW) {
		this.BW = BW;
	}

	public void setHMS(int HMS) {
		this.HMS = HMS;
	}
	public void setObjectives(int nObjectives) {
		this.nObjectives = nObjectives;
	}

	public void setArrays() {

		low = new double[NVAR];
		high = new double[NVAR];
		NCHV = new double[NVAR];
		bestHarmony = new double[NVAR + 1];
		bestFitHistory = new double[maxIter + 1];
		worstFitHistory = new double[maxIter + 1];
		HM = new double[HMS][NVAR + nObjectives];
		resultData = new double [NVAR + nObjectives];
	}

	public void setBounds(double low[], double high[]) {

		setArrays();
		this.low = low;
		this.high = high;
	}

	public void initiator() {

		int i;
		double[] curFit = new double[nObjectives];

		for (i = 0; i < HMS; i++) {
			for (int j = 0; j < NVAR; j++) {
				HM[i][j] = randGen.randVal(low[j], high[j]);
				NCHV[j] = HM[i][j];
			}
			curFit = fitness(NCHV);

			for (int k = 0; k < nObjectives; k++)
				HM[i][NVAR + k] = curFit[k];

			//			updateHarmonyMemory(curFit);
		}
	}

	public double[] fitness(double x[]) { //한줄 전체를 fitness
		double[] f = new double[nObjectives];
		iteration++;

	//	f[0] = y[0] + x[1] * 0;
	//	f[1] = y[1] + x[0] * 0;
		//f[0] = x[0] + Math.exp(x[1]); 
		//f[1] = -2 * x[0] + Math.exp(-x[1]);

		//System.out.print(f[0] +"\t"+ f[1] + "\t" + x[0] + "\t" + x[1] + "\n" + "\t");
		
		//여기다가 실시간 함수를하나 넣어놓음
		
		
		//System.out.println(Math.pow(x[0],2) + "\t" + x[0]);
		
		if(iteration % 50 == 0) {
			ParetoSet();
			for(int i = 0; i < Pareto.length; i++){
				for(int j = 0; j < Pareto[i].length; j++){
					System.out.print(Pareto[i][j] + "\t");
				}
				System.out.print("\n");
			}
		}
		return f;
	}

	public boolean stopCondition() {
		if (generation > maxIter)
			terminationCriteria = false;
		return terminationCriteria;

	}

	public void updateHarmonyMemory(double[] newFitness) {	//완료
		if (nObjectives == 1)
		{

			// find worst harmony
			double worst = HM[0][NVAR];
			int worstIndex = 0;
			for (int i = 0; i < HMS; i++)
				if (HM[i][NVAR] > worst) {
					worst = HM[i][NVAR];
					worstIndex = i;
				}
			worstFitHistory[generation] = worst;
			// update harmony
			if (newFitness[0] < worst) {
				for (int k = 0; k < NVAR; k++)
					HM[worstIndex][k] = NCHV[k];
				HM[worstIndex][NVAR] = newFitness[0];
			}

			// find best harmony
			double best = HM[0][NVAR];
			int bestIndex = 0;
			for (int i = 0; i < HMS; i++)
				if (HM[i][NVAR] < best) {
					best = HM[i][NVAR];
					bestIndex = i;
				}
			bestFitHistory[generation] = best;
			if (generation > 0 && best != bestFitHistory[generation - 1]) {
				for (int k = 0; k < NVAR; k++)
					bestHarmony[k] = HM[bestIndex][k];
				bestHarmony[NVAR] = best;
			}
		}

		else	//if These are multi-Objectives
		{
			int sum = 0;
			for(int i = 0; i < HMS; i++) {
				sum = 0;
				for(int j = 0; j < nObjectives; j++)
					if(newFitness[j] <= HM[i][NVAR+j])
						sum++;
				if(sum == nObjectives) {
					for(int j = 0; j < nObjectives; j++)
						HM[i][NVAR+j] = newFitness[j];
					for(int k = 0; k < NVAR; k++)
						HM[i][k] = NCHV[k];
					break;
				}

			}
		}
	}

	private void memoryConsideration(int varIndex) {

		NCHV[varIndex] = HM[randGen.randVal(0, HMS - 1)][varIndex];
	}

	private void pitchAdjustment(int varIndex) {

		double rand = randGen.ran1();
		double temp = NCHV[varIndex];
		if (rand < 0.5) {
			temp += rand * BW;
			if (temp < high[varIndex])
				NCHV[varIndex] = temp;
		} else {
			temp -= rand * BW;
			if (temp > low[varIndex])
				NCHV[varIndex] = temp;
		}

	}

	private void randomSelection(int varIndex) {

		NCHV[varIndex] = randGen.randVal(low[varIndex], high[varIndex]);

	}

	public void mainLoop() {

		long startTime = System.currentTimeMillis();
		initiator();

		while (stopCondition()) {

			for (int i = 0; i < NVAR; i++) {
				if (randGen.ran1() < HMCR) {
					memoryConsideration(i);
					if (randGen.ran1() < PAR)
						pitchAdjustment(i);
				} else
					randomSelection(i);
			}

			double[] curFit = new double[nObjectives];
			curFit = fitness(NCHV);
			updateHarmonyMemory(curFit);
			generation++;

		}
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time : " + (endTime - startTime) / 1000.0
				+ " seconds");
	}

	public void ParetoSet() {
		Pareto = new double[HMS][nObjectives+NVAR];
		int count = 0;

		for(int i = 0; i < HMS; i++) {
			for(int j = 0; j < nObjectives; j++)
				Pareto[count][j] = HM[i][NVAR+j];
			for(int j = nObjectives; j < nObjectives + NVAR; j++)
				Pareto[count][j] = HM[i][j-nObjectives];
			count++;
		}

	}

}
