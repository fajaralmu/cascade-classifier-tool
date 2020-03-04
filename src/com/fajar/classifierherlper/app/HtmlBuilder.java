package com.fajar.classifierherlper.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.fajar.classifierherlper.app.util.StringUtil;

public class HtmlBuilder {
 
	
	private int[][][] imageMatrix;
	
	public HtmlBuilder(String inputImagePath) {
		build(inputImagePath);
	}
	
	private void build(String inputImagePath) {

		File inputFile = new File(inputImagePath);

		try {
			BufferedImage inputImage = ImageIO.read(inputFile);
			readPixels(inputImage);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}
	
	private  void readPixels(BufferedImage image) {
		
		System.out.println("Will read pixels");
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		imageMatrix = new int[width][height][3];
		 
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) { 
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				imageMatrix[x][y][0] = red;
				imageMatrix[x][y][1] = green;
				imageMatrix[x][y][2] = blue;
			}
		}
		
		System.out.println("Done Read Pixels");

	}
	
	public void printHtml() {
		
		StringBuilder stringBuilder = new StringBuilder("<html>\n<head>\n<title>Generated</title>\n");
		
		/**
		 * head
		 */
		String style = generateStyle();
		
		stringBuilder.append(style);
		stringBuilder.append("</head>\n<body>\n");
		/**
		 * body
		 */
		String body = generateHtmlBody();
		
		stringBuilder.append(body);
		stringBuilder.append("\n</body>\n</html>");
		
		System.out.println(stringBuilder.toString());
		
	}
	
	private String generateStyle() {
		
		StringBuilder stringBuilder = new StringBuilder("<style>\n");
		
		int cols = imageMatrix[0].length;
		
		String style = ".row {display: grid;\r\n" + 
				"		grid-template-columns: ${sizes};\r\n" + 
				"		vertical-align: middle; }";
		
		String sizes = StringUtils.repeat("1px ", cols);
				
		stringBuilder.append(style.replace("${sizes}", sizes) );
		
		return stringBuilder.append("</style>\n").toString();
		
	}
	
	private String generateHtmlBody() {
		
		StringBuilder stringBuilder = new StringBuilder("<div class=\"content\">\n");
		
		for (int i = 0; i < imageMatrix.length; i++) {
			
			int[][] currentRow = imageMatrix[i];
			
			stringBuilder.append("<div class=\"row\" id=\"").append(randomId()).append("\">");
			
			 
			
			for (int j = 0; j < currentRow.length; j++) {
				
				int[] currentCell = currentRow[j];
				
				int r = currentCell[0];
				int g = currentCell[1];
				int b = currentCell[2];
				
				stringBuilder
				.append("<div class=\"cell\" id=\"")
				.append(randomIdCell())
				.append("\"")
				.append("style=\"width:1px;height:1px;background-color:rgb("+r+", "+g+", "+b+")\"")
				.append(">");
				
				stringBuilder.append("</div>");
				
				if(j % 50 == 0) {
					stringBuilder.append("\n");
				}
			}
			
			stringBuilder.append("</div>\n");
		}
		
		return stringBuilder.append("</div>").toString();
	}
	
	private String randomId() {
		
		return String.valueOf(RandomUtils.nextInt(1, 90000000));
	}
	
	private String randomIdCell() {
		
		 return UUID.randomUUID().toString();
	}

}
