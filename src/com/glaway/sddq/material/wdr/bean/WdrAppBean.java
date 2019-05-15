package com.glaway.sddq.material.wdr.bean;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;

public class WdrAppBean extends AppBean {
	/**
	 * 发送流程前判断行是否可以发送
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ROUTEWF() throws Exception {
		IJpo wdr = getJpo();
		IJpoSet wdrlineset = wdr.getJpoSet("wdrline");
		if(wdrlineset.isEmpty()){
			throw new MroException("处置物料信息为空，请先补全处置物料信息在发送工作流");
		}
		return super.ROUTEWF();
	}
}
