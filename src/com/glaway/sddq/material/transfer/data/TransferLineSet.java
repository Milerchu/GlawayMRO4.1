package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 调拨单行JpoSet
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since [GlawayMro4.0/调拨单]
 */
public class TransferLineSet extends JpoSet implements IJpoSet {

	private static final long serialVersionUID = 1L;

	public TransferLine getJpoInstance() {
		return new TransferLine();
	}
}
