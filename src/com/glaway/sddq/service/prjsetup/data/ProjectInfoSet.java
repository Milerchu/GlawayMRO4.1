package com.glaway.sddq.service.prjsetup.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 项目信息jpoSet
 * 
 * @author ygao
 * @version [版本号, 2017-11-7]
 * @since [产品/模块版本]
 */
public class ProjectInfoSet extends JpoSet implements IJpoSet {
	private static final long serialVersionUID = 1L;

	public ProjectInfo getJpoInstance() {
		return new ProjectInfo();
	}
}
