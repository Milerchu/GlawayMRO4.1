package com.glaway.sddq.material.materborrow.data;

import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * <配件借用JPOSet类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class MaterBorrowSet extends JpoSet {
	  @Override
	    public MaterBorrow getJpoInstance()
	    {
	        return new MaterBorrow();
	    }
}
