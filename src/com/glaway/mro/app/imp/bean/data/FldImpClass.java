package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * @author ygao
 *
 */
public class FldImpClass extends JpoField
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -950514711676860483L;
    
    @Override
    public void action() throws MroException {
    	String impclass = getMroType().getStringValue();
    	if(!StringUtil.isNull(impclass)){
    		try{
        		Class.forName(impclass).newInstance();
        	}catch(Exception e){
        		throw new MroException("impclass", "impclassnotfound");
        	}
    	}
    	super.action();
    }
    
    
}
