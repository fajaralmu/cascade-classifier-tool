package com.fajar.classifierherlper.app;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {

	private static final String BASE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\spoon\\positives";
	private static final String BASE_DIR_BG = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\spoon\\bg";

	// https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
	public static boolean resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {
		try {
			// reads input image
			File inputFile = new File(inputImagePath);

			BufferedImage inputImage = ImageIO.read(inputFile);

			// creates output image
			BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();

			// extracts extension of output file
			String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

			// writes to output file
			File outputFile = new File(outputImagePath);
			boolean write = ImageIO.write(outputImage, formatName, outputFile);
			return write;
		} catch (Exception ex) {

			return false;
		}

	}

	public static boolean flip(String inputImagePath,String outputImagePath, int mode) {
		try {
			// reads input image
			File inputFile = new File(inputImagePath);

			BufferedImage image = ImageIO.read(inputFile);

			// Flip the image vertically
			AffineTransformOp op;
			AffineTransform tx;
			if (mode == 1) {
				tx = AffineTransform.getScaleInstance(1, -1);
				tx.translate(0, -image.getHeight(null));
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			} else if (mode == 2) {
				// Flip the image horizontally
				tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			} else {
				// Flip the image vertically and horizontally; equivalent to rotating the image
				// 180 degrees
				tx = AffineTransform.getScaleInstance(-1, -1);
				tx.translate(-image.getWidth(null), -image.getHeight(null));
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			}
			File outputFile = new File(outputImagePath);
			boolean write = ImageIO.write(image, "jpg", outputFile);
			System.out.println("WRITE:"+write);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		File baseFile = new File(BASE_DIR);
		File[] files = baseFile.listFiles();
		int count = 0;
		for (File file : files) {
			if (file.isDirectory() || (!file.getName().endsWith(".jpg") && !file.getName().endsWith(".JPG"))) {
				continue;
			}
			
			BufferedImage image = ImageIO.read(file);
			
			System.out.println(file.getName()+" 1 0 0 "+image.getWidth()+" "+image.getHeight()); 
			//flip
//			flip(file.getCanonicalPath(),BASE_DIR.concat("\\flip_"+1).concat(file.getName()), 1);
//			flip(file.getCanonicalPath(),BASE_DIR.concat("\\flip_"+2).concat(file.getName()), 2);
//			flip(file.getCanonicalPath(),BASE_DIR.concat("\\flip_"+3).concat(file.getName()), 3);
			
			count++;
		}

		System.out.println("TOTAL: " + count);
	}
}
