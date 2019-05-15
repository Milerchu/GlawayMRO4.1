package com.glaway.sddq.material.materborrowline.data;

import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * <配件借用行JPOSet类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class MaterBorrowLineSet extends JpoSet {
	 @Override
	    public MaterBorrowLine getJpoInstance()
	    {
	        return new MaterBorrowLine();
	    }
}
