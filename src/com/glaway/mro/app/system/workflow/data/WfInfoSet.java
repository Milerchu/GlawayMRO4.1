package com.glaway.mro.app.system.workflow.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * @ClassName: WfInfoSet
 * @Description: 工作流基本信息表jpoSet-wfinfo
 * @author hyhe
 * @date Mar 23, 2016 2:46:02 PM
 */
public class WfInfoSet extends JpoSet implements IJpoSet {

	private static final long serialVersionUID = 1L;
	
	public WfInfo getJpoInstance() {
		return new WfInfo();
	}
	
}
