package org.SUSCOM.Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Replacement {

	private String getLine;
	//TODO should be replaced with relative path
	private String filePath = "C:\\workspace\\SUSCOM\\ExampleFiles\\elementABS.idf";

	private FileReader fileReader;

	private FileWriter fileWriter;

	private StringBuffer stringBuffer;

	private BufferedWriter bufferedWriter;

	private BufferedReader bufferedReader;
	

	public Replacement(String[][] Array) throws IOException {
		initialize();
		ReplaceArray(Array);
	}


	protected void initialize() throws FileNotFoundException {
		stringBuffer = new StringBuffer();
		fileReader = new FileReader(filePath);
		bufferedReader = new BufferedReader(fileReader);
	}

	/**
	 * Text file replacer
	 * replace symbol with new variable.
	 * @param Array
	 * @throws IOException
	 */
	protected void ReplaceArray(String[][] Array) throws IOException { 
		int row = 0;
		try {
			while((getLine = bufferedReader.readLine()) != null) {
				stringBuffer.append(getLine.
						replaceAll(Array[row][0],Array[row][1]) + "\n");
				if((getLine.contains(Array[row][0])) &&
						(row < Array.length - 1)) {
					row++;
				}
			}
			
			fileWriter = new FileWriter(filePath);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
			bufferedWriter.close();

			fileWriter.close();
			bufferedReader.close();
			fileReader.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
