package org.SUSCOM.SimulationRun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SimulationRun {

	/**
	 * TODO:Process path should be replaced with relative path
	 */
	public SimulationRun() {
		Runtime runTime = Runtime.getRuntime();
		try {
			Process process = runTime.exec
					("C:\\EnergyPlusV8-1-0\\RunEPlus.bat elementABS KOR_Inchon.471120_IWEC");
			InputStream inputStream = process.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

			String line = null;
			while((line = bufferedReader.readLine())!=null) {
				System.out.println(line);	
			}
			process.getInputStream().close();
			bufferedReader.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
