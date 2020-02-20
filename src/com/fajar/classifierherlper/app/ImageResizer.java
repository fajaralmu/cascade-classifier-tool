package com.fajar.classifierherlper.app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageResizer {

	//SPOON
//	private static final String BASE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\spoon\\positives\\final";
//	private static final String BASE_RAW_POSITIVE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\spoon\\positives\\RAW";
//	private static final String BASE_DIR_BG = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\spoon\\bg";
	
	//BALSEM
//	private static final String BASE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\balsem\\positives\\final";
//	private static final String BASE_RAW_POSITIVE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\balsem\\positives\\RAW";
//	private static final String BASE_DIR_BG = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\balsem\\bg";
//	
	/**
	 * PERFUME
	 */
//	private static final String BASE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\perfume\\positives\\final";
//	private static final String BASE_RAW_POSITIVE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\perfume\\positives\\RAW";
//	private static final String BASE_DIR_BG = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\perfume\\bg";
//	private static final String BASE_DIR_RECT = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\perfume\\positives\\RECT";

	/**
	 * DYNO
	 */
//	private static final String BASE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\dyno\\positives\\final";
//	private static final String BASE_RAW_POSITIVE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\dyno\\positives\\RAW";
//	private static final String BASE_DIR_BG = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\dyno\\bg";
//	private static final String BASE_DIR_RECT = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\dyno\\positives\\RECT";
	
	/**
	 * CACTUS 1
	 */
	private static final String BASE_FINAL_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\cactus_new_single\\positives\\final";
	private static final String BASE_RAW_POSITIVE_DIR = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\cactus_new_single\\positives\\RAW";
	private static final String BASE_DIR_BG = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\cactus\\bg";
	private static final String BASE_DIR_RECT = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\cactus_new_single\\positives\\RECT";
	
	
	private static final String BASE_BG_IMG_PATH = "C:\\Users\\Republic Of Gamers\\Pictures\\cascadeclassifier\\spoon\\positives\\BASE\\base_BG.JPG";
	static BufferedImage baseBackgroundImage;
	static final Random random = new Random();

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

	public static boolean flip(String inputImagePath, String outputImagePath, int mode) {
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
			System.out.println("WRITE:" + write);
			return write;
		} catch (Exception ex) {
			return false;
		}
	}

	public static Color randomColor() {

		Color color = new Color(random.nextInt(250) + 1, random.nextInt(250) + 1, random.nextInt(250) + 1);
		return new Color(190,190,190);
	}

	public static boolean resizeAddBg(String inputImagePath, String outputImagePath, int w, int h) {

		if (w == h) {
			return true;
		}

		try {
			File inputFile = new File(inputImagePath);
			int sideLength = w > h ? w : h;

			BufferedImage inputImage = ImageIO.read(inputFile);

			// creates output image
			BufferedImage outputImage = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = outputImage.createGraphics();

			int initialX = 0, initialY = 0;

			// horizontal rectangle
			if (w >= h) {
				initialY = (w - h) / 2;
			} else {
				initialX = (h - w) / 2;
			}

			g2d.setColor(randomColor());

			g2d.drawImage(baseBackgroundImage, 0, 0, sideLength, sideLength, randomColor(), null);
			 g2d.fillRect(0, 0, sideLength, sideLength);
			g2d.drawImage(inputImage, initialX, initialY, w, h, null);
			g2d.dispose();

			// extracts extension of output file
			String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

			// writes to output file
			File outputFile = new File(outputImagePath);
			boolean write = ImageIO.write(outputImage, "jpg", outputFile);
			System.out.println("Writing: " + outputFile);
			return write;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return true;
	}

	public static void main(String[] args) {
		Application application = new Application();
	 
	}
	
	public static void run() throws IOException {

		File baseBgFile = new File(BASE_BG_IMG_PATH);
		baseBackgroundImage = ImageIO.read(baseBgFile);

		File baseFile = new File(BASE_FINAL_DIR);
		File[] files = baseFile.listFiles();
		int count = 0;

		final String fileExt = ".jpg";

		for (File file : files) {
			if (file.isDirectory()
					|| (!file.getName().endsWith(fileExt) && !file.getName().endsWith(fileExt.toUpperCase()))) {
				continue;
			}

			BufferedImage image = ImageIO.read(file);
			final int w = image.getWidth();
			final int h = image.getHeight();

			int sizeLength = w >= h ? w : h;

			// System.out.println(file.getName()+" 1 0 0 "+image.getWidth()+"
			// "+image.getHeight());
			// flip
//			flip(file.getCanonicalPath(),BASE_DIR.concat("\\flip_"+1).concat(file.getName()), 1);
//			flip(file.getCanonicalPath(),BASE_DIR.concat("\\flip_"+2).concat(file.getName()), 2);
//			flip(file.getCanonicalPath(),BASE_DIR.concat("\\flip_"+3).concat(file.getName()), 3);

			// BG
//			if(file.getName().endsWith(fileExt))
//			resize(file.getCanonicalPath(), file.getCanonicalPath().replace("png", "JPG"), 200, 200);
//			else
//			resize(file.getCanonicalPath(), file.getCanonicalPath().replace("PNG", "JPG"), 200, 200);
			boolean resizeAddBg = 
					true;
					
// 					resizeAddBg(file.getCanonicalPath(), BASE_DIR_RECT.concat("\\rect_").concat(file.getName()), w, h);
			if(resizeAddBg) {
				System.out.println( file.getName() + " 1 0 0 " + sizeLength + " " + sizeLength);
				count++;
			}
		}

		System.out.println("TOTAL: " + count);
	}
}
