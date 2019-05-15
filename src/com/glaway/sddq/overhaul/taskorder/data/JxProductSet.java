package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

public class JxProductSet extends JpoSet implements IJpoSet {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	public JxProduct getJpoInstance() {
		return new JxProduct();
	}
}
