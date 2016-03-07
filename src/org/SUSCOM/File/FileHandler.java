package org.SUSCOM.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler implements FileIO {

	private File file;

	private FileInputStream fileInputStream;

	private FileOutputStream fileOutputStream;

	@Override
	public void inputHandler(File filePath) {
	}

	@Override
	public void outputHandler(File filePath) {
	}

	@Override
	public String getFileLocation() {
		return null;
	}

	/**
	 * TODO : Replace absPath with relative Path
	 */
	@Override
	public void fileDelete(String filePath) {
		file = new File(filePath);
		file.delete();
	}

	@Override
	public void fileCopy(String FromfilePath, String TofilePath) {
		try {
			fileInputStream = new FileInputStream(FromfilePath);
			fileOutputStream = new FileOutputStream(TofilePath);

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


}
