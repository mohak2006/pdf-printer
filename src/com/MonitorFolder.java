package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class MonitorFolder {  
    public static void main(String[] args) throws Exception {
        final long pollingInterval = 1 * 1000;
		
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		
		String INPUT_FOLDER = prop.getProperty("FOLDER_TO_MONITOR");
		String OUTPUT_FOLDER = prop.getProperty("FOLDER_TO_DROP");
		
        File inputDir = new File(INPUT_FOLDER);
        File outputDir = new File(OUTPUT_FOLDER);
        
        if (!inputDir.exists()) {
        	inputDir.mkdir();
        }

        FileAlterationObserver observer = new FileAlterationObserver(inputDir);
        FileAlterationMonitor monitor = new FileAlterationMonitor(pollingInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            // Is triggered when a file is created in the monitored folder
            @Override
            public void onFileCreate(File file) {
            	PrintAndMove.printAndMove(file,inputDir,outputDir);
            }
            
            @Override
            public void onFileChange(File file) {
            	PrintAndMove.printAndMove(file,inputDir,outputDir);
            	super.onFileChange(file);
            }
        };
        
        
        observer.addListener(listener);
        monitor.addObserver(observer);


		int month = getCurrentMonth();
		
        if(month == 8 || month == 9){
        	monitor.start();
        	CreateSystemTray.createSystemTray();
        	System.out.println("Monitoring Started for : " + INPUT_FOLDER);
        }

        
        
    }
    
    @SuppressWarnings("deprecation")
	public static int getCurrentMonth() throws IOException{
    	int month;
		try{
			String TIME_SERVER = "time-a.nist.gov";   
			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
			Date time = new Date(returnTime);
			
			month = time.getMonth();
			
		}
		catch(UnknownHostException uhe){
	        System.out.println("No internet connection");
			Calendar cal = Calendar.getInstance();
	        month = cal.get(Calendar.MONTH);
		}
		
		return month;
    }
    
}