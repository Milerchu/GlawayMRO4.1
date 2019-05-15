package com.glaway.sddq.base.revisit.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * @ClassName: ProblemmgSet
 * @Description: TODO
 * @author sqzhou
 * @date 2017-2-9 下午02:26:17
 * 
 */

public class ProblemMgSet extends JpoSet implements IJpoSet {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;

	public ProblemMg getJpoInstance() {
		return new ProblemMg();
	}

}
