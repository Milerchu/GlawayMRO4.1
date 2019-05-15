package com.glaway.sddq.material.altitem.data;

import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * <物料替换关系表JPoSet>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class AltitemSet extends JpoSet {
	@Override
	public Altitem getJpoInstance() {
		return new Altitem();
	}
}
