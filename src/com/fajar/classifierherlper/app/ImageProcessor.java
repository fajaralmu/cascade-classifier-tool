package com.fajar.classifierherlper.app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {

	private static final int PERCENTAGE = 17;

	public static void process(String inputImagePath) {

		File inputFile = new File(inputImagePath);

		System.out.println("Printing binary image in console");
		
		
		try {
			BufferedImage image = ImageIO.read(inputFile);
			int scaledWidth = image.getWidth() * PERCENTAGE/100;
			int scaledHeight = image.getHeight() * PERCENTAGE / 100;
			BufferedImage result = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_BINARY);

			// scales the input image to the output image
			Graphics2D g2d = result.createGraphics();
			g2d.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
            
            File output = new File("C:\\Users\\Republic Of Gamers\\Pictures\\Binary\\black_white.png");
            
            ImageIO.write(result, "png", output);
            
            printImage(ImageIO.read(output));
            
            System.out.println("DONE");
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}
	
	private static void printImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		 
		StringBuilder stringBuilder = new StringBuilder();
		 
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) { 
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				boolean black = red == 0 && green == 0 && blue == 0;
				boolean white = red == 255 && green == 255 && blue == 255;
				
				if(black) {
					stringBuilder.append('o');
					
				}else if(white){
					stringBuilder.append(' ');
				}
			}
			stringBuilder.append('\n');
		}
		System.out.println(stringBuilder.toString());
	}
}
