package com.fajar.classifierherlper.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class HtmlBuilder {
 
	
	private int[][][] imageMatrix;
	private final String SIDE_LENGTH;
	private final boolean bordered;
	
	public HtmlBuilder(String inputImagePath, int sideLength, boolean bordered) {
		build(inputImagePath);
		this.SIDE_LENGTH = sideLength+"px";
		this.bordered = bordered;
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
		
		imageMatrix = new int[height][width][3];
		 
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) { 
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				imageMatrix[y][x][0] = red;
				imageMatrix[y][x][1] = green;
				imageMatrix[y][x][2] = blue;
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
		System.out.println(StringUtils.repeat("=", 100));
		System.out.println("");
		System.out.println("");
		System.out.println(stringBuilder.toString());
		
	}
	
	private String generateStyle() {
		
		StringBuilder stringBuilder = new StringBuilder("<style>\n");
		
		int cols = imageMatrix[0].length;
		
		String style = ".row "
				+ "{"
				+ "display: grid;\r\n" + 
				" grid-template-columns: ${sizes};\r\n" + 
				" vertical-align: middle; "
				+ "}\n"
				
				+ ".cell"
				+ "{"
				+ " width:"+SIDE_LENGTH+";"
				+ " height:"+SIDE_LENGTH+"; "
				+ 	(this.bordered?"border:solid 1px yellow":"")
				+ "}\n";
		
		String sizes = StringUtils.repeat(SIDE_LENGTH+" ", cols);
				
		stringBuilder.append(style.replace("${sizes}", sizes) );
		
		return stringBuilder.append("</style>\n").toString();
		
	}
	
	private String generateHtmlBody() {
		
		StringBuilder stringBuilder = new StringBuilder("<div class=\"content\">\n");
		
		for (int i = 0; i < imageMatrix.length; i++) {
			
			int[][] currentRow = imageMatrix[i];
			
			stringBuilder.append("<div class=\"row\"")// id=\"").append(randomId())
			.append( ">");
			 
			
			for (int j = 0; j < currentRow.length; j++) {
				
				int[] currentCell = currentRow[j];
				
				int r = currentCell[0];
				int g = currentCell[1];
				int b = currentCell[2];
				
				stringBuilder
				.append("<div class=\"cell\" ")// id=\"")
				//.append(randomIdCell())
				//.append("\"")
				.append("style=\"background-color:rgb("+r+", "+g+", "+b+")\"")
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
	
	public static String randomId() {
		
		return String.valueOf(RandomUtils.nextInt(1, 90000000));
	}
	
	public static String randomIdCell() {
		
		 return UUID.randomUUID().toString();
	}

}
