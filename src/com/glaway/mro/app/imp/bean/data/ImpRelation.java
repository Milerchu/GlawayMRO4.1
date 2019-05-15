package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;

public class ImpRelation extends Jpo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8958773217018841771L;

	@Override
	public void init() throws MroException {
		super.init();
//		IJpoSet impattrset = this.getJpoSet("IMPATTRIBUTE");
//    	if(!impattrset.isEmpty()&&!StringUtil.isNull(getField("OBJECTNAME").getMroType().asString())){
//    		setFieldFlag(new String[]{"OBJECTNAME"},GWConstant.S_READONLY, true);
//    	}else{
//    		setFieldFlag(new String[]{"OBJECTNAME"},GWConstant.S_READONLY, false);
//    	}
	}
}
