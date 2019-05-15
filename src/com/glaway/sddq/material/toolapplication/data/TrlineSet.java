package com.glaway.sddq.material.toolapplication.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <工具设备申请行JpoSet>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class TrlineSet extends JpoSet {
	private static final long serialVersionUID = 42415563064965294L;

	public Trline getJpoInstance() {
		return new Trline();
	}
}
