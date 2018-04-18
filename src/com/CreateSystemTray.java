package com;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CreateSystemTray {
	public static void createSystemTray() throws IOException, AWTException{
		if(SystemTray.isSupported()){
			File icon = new File("print-pdf.png");
			TrayIcon ti = null;
			if(!icon.exists()){
				byte[] byteArray = new byte[] { -1, 0 };
				ColorModel colorModel = new IndexColorModel(1, 2, byteArray, byteArray, byteArray);
		        WritableRaster writeableRaster = Raster.createPackedRaster(DataBuffer.TYPE_BYTE, 1700, 2200, 1, 1, null);
		        BufferedImage bufImg = new BufferedImage(colorModel, writeableRaster, false, null);
				ti = new TrayIcon(bufImg);
			}
			else{
				ti = new TrayIcon(ImageIO.read(icon));
			}
			ti.setImageAutoSize(true);
			
			SystemTray.getSystemTray().add(ti);
			
			PopupMenu popup = new PopupMenu();
			MenuItem exitItem = new MenuItem("Exit");
			exitItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			
			popup.add(exitItem);
			ti.setPopupMenu(popup);
			ti.setToolTip("PDF Monitor and Printer");
		}
	}

}
