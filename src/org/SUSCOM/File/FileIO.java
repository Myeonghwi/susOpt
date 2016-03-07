package org.SUSCOM.File;

import java.io.File;

public interface FileIO {
	
	/**
	 * this is a input file handler
	 */
	void inputHandler(File filePath);
	
	/**
	 * this is a output file handler
	 */
	void outputHandler(File filePath);
	
	/**
	 * fileCopy method(A file, B file)
	 */
	void fileCopy(String FromfilePath, String TofilePath);
	
	/**
	 * fileDelete method(file)
	 */
	void fileDelete(String filePath);
	
	/**
	 * 
	 * @return return to FileLocation
	 */
	String getFileLocation();

}
