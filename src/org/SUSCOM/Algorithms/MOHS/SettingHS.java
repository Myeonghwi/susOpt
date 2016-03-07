package org.SUSCOM.Algorithms.MOHS;

public class SettingHS {

	public static void main(String[] args) {

	
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

}
