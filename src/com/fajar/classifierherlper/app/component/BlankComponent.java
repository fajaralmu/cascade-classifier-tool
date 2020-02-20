package com.fajar.classifierherlper.app.component;

import java.awt.Component;

public class BlankComponent extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8048823224801306780L;
	
	public final ReservedFor reserved;
	public BlankComponent(ReservedFor reservedFor, int w, int h) {
		super();
		this.reserved = reservedFor;
		this.setBounds(0, 0, w, h);
	}

}
