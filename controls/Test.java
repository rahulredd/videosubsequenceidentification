package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
		try {
			String cmpdata=getData();
			File file = new File("DB_Images");
			if (file.isDirectory()) {
				File list[] = file.listFiles();
				for (int i = 0; i < list.length; i++) {
					FileInputStream fis = new FileInputStream(list[i]);
					byte[] b = new byte[fis.available()];
					fis.read(b);
					String str = new String(b);
					if (str.equals(cmpdata)) {
						System.out.println("Matched Frame:"+list[i].getAbsolutePath());
						break;
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getData() {
		// TODO Auto-generated method stub
		String str="";
		try {
			File file = new File("Input_Images/Frame55.jpg");
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			str = new String(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new Test();
		int result=new Comparitor().compare("Frame4.jpg", "Frame3.jpg");
		System.out.println(result);
	}

}
