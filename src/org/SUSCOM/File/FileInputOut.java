package org.SUSCOM.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.SUSCOM.SimulationRun.SimulationRun;


/**
 * @author LMH
 * This class will be deleted
 */

public class FileInputOut {

	private BufferedReader bufferedReader;

	private FileReader fileReader;
	
	private String getLine;
	
	static double HC_ToT = 0.0;

	static double PPD_ToT = 0.0;
	
	public void fileHandler(String fileName, double x0, double x1, double x2) throws IOException {

		fileReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(fileReader);

		String[][] variable = {{"@@Ghei@@", x0 +""},
				{"@@GLAI@@", x1 +""},
				{"@@GSOI@@", x2 +""}};

		StringBuffer stringBuffer = new StringBuffer();
		try {

			int x = 0;
			while((getLine = bufferedReader.readLine())!= null) {

				stringBuffer.append(getLine.replaceAll(variable[x][0], variable[x][1]) + "\n");

				if(getLine.contains(variable[x][0]) && x < 2) {
					x++;
				}
			}

			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
			bufferedWriter.close();

			fileWriter.close();
			bufferedReader.close();
			fileReader.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		new SimulationRun();

		fileDelete("C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf");
		fileCopy("C:\\Users\\LMH\\Desktop\\elementABS.idf","C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf");
	}


	public void fileDelete(String deleteFileName) {
		File file = new File(deleteFileName);
		file.delete();
	}


	public void fileCopy(String inFileName, String outFileName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(inFileName);
			FileOutputStream fileOutputStream = new FileOutputStream(outFileName);

			int data = 0;

			while((data = fileInputStream.read()) != -1) {
				fileOutputStream.write(data);
			}
			fileInputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void outputHandler(String FileName, double var1, double var2, double var3) {

		int row = 0;

		String line;
		String[] arr;
		String[][] outData = new String [2][100];

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(FileName));


			while ((line = bufferedReader.readLine()) != null) { //세로수 측정
				arr = line.split(",");

				for(int i = 1; i < arr.length; i++) {
					outData[row][i] = arr[i];
				}

				row++;
			}

			for(int j = 1; outData[1][j] != null; j++) {


				if(j < 63)
					HC_ToT += Double.parseDouble(outData[1][j]);	//Heating & Cooling data
				else
					PPD_ToT += Double.parseDouble(outData[1][j]);	//PPD data
			}

			bufferedReader.close();

			System.out.println(HC_ToT + "\t" + PPD_ToT + "\t" + var1 + "\t" + var2 + "\t" + var3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public double getHC_ToT() {

		double HC_ToT2 = 0.0;
		HC_ToT2 = HC_ToT;
		HC_ToT = 0.0;
		return HC_ToT2;

	}

	public double getPPD_ToT() {

		double PPD_ToT2 = 0.0;
		PPD_ToT2 = PPD_ToT;
		PPD_ToT = 0.0;
		return PPD_ToT2;

	}
}
