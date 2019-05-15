package com.glaway.sddq.material.toolnsp.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <工具校检单JpoSet>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class ToolinspSet extends JpoSet {

	private static final long serialVersionUID = 42415563064965294L;

	public Toolinsp getJpoInstance() {
		return new Toolinsp();
	}

}
