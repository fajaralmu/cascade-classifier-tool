package com.fajar.classifierherlper.app.component;

import java.awt.Color;

public class PanelRequest {
	int Col;
	int W;
	int H;
	int Margin;
	Color color;
	boolean autoScrool;

	int panelX;
	int panelY;
	int panelW;
	int panelH;
	
	public PanelRequest(int col, int w, int h, int margin, Color color, int panelX, int panelY, int panelW,
			int panelH, boolean autoScrool) {
		super();
		Col = col;
		W = w;
		H = h;
		Margin = margin;
		this.color = color;
		this.panelX = panelX;
		this.panelY = panelY;
		this.panelW = panelW;
		this.panelH = panelH;
		this.autoScrool = autoScrool;
	}
	
	

}
