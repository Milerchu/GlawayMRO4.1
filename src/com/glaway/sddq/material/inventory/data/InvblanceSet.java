package com.glaway.sddq.material.inventory.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 调拨单JpoSet
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/调拨单]
 */
public class InvblanceSet extends JpoSet implements IJpoSet {

	private static final long serialVersionUID = 1L;

	public Invblance getJpoInstance() {
		return new Invblance();
	}
}
