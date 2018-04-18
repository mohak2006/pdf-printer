package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PDFValidity {
	public static boolean checkValidPDF(File file){
    	boolean validity;
    	
    	try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String firstLine = br.readLine();
			if(firstLine == null){
				System.out.println("AN INVALID PDF FILE RECEIVED : " + file);
				validity = false;
			}
			else{
				if(firstLine.contains("%PDF-")){
					validity = true;
				}
				else{
					System.out.println("AN INVALID PDF FILE RECIEVED : " + file);
					validity = false;
				}	
			}
			br.close();
		} catch (FileNotFoundException e) {
			validity = false;
			e.printStackTrace();
		} catch (IOException e) {
			validity = false;
			e.printStackTrace();
		}
    	
    	
    	
		return validity;
    }
}
