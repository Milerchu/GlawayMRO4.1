package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 配件申请 车型选择类
 * 
 * @author yangyi
 * @version [GlawayMro4.0, 2018-8-1]
 * @since [GlawayMro4.0/配件申请]
 */
public class FldMrLineModel extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 过滤车型
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String project = this.getJpo().getString("PROJECT");
		setListWhere(" modelnum in (select CMODEL from asset where PROJECTNUM='"
				+ project + "')");
		return super.getList();
	}

}
