package com.glaway.sddq.base.revisit.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

public class ReturnVisitSet extends JpoSet implements IJpoSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReturnVisit getJpoInstance() {
		return new ReturnVisit();
	}
}
