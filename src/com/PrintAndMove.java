package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;

public class PrintAndMove {
	public static void printAndMove(File file, File inputDir, File outputDir){

    	if(file.getParent().equals(inputDir.toString())){ 	//To only process immediate files
        	if(PDFValidity.checkValidPDF(file)){ 			//To check if this is a valid PDF
        		PrintService myService = PrintServiceLookup.lookupDefaultPrintService();
        		FileInputStream fis = null;
        	    if (myService == null) {
        	        throw new IllegalStateException("Printer not found");
        	    }
        		try {
        			fis = new FileInputStream(file);
					Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
				    DocPrintJob printJob = myService.createPrintJob();
				    printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
				    System.out.println("Printing the file: " + file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (PrintException e) {
					e.printStackTrace();
				}
        		finally{
        			try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        		
				if(!outputDir.exists()){
					outputDir.mkdir();
				}

				if(!inputDir.equals(outputDir)){
					System.out.println("Moving file to folder : " + outputDir);
					File newFile = new File(outputDir.toString() + "\\" + file.getName());
					if(!newFile.exists()){
						file.renameTo(newFile);
					}
					else{
						newFile.delete();
						file.renameTo(newFile);
					}
				}
				else{
					System.out.println("Leaving file in same folder as input and output folders are same");
				}
        	}
    	}
	}
}
