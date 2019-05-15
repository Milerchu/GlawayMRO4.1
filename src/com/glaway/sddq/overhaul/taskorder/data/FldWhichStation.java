package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * 工单所属站段 whichstation字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-10-18]
 * @since  [产品/模块版本]
 */
public class FldWhichStation extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -8177858340209659724L;

	@Override
	public IJpoSet getList() throws MroException {
		// 根据办事处过滤站点
		String whichoffices = getJpo().getString("WHICHOFFICES");
		if(whichoffices.isEmpty())
		{
			throw new MroException("jxtaskorder", "nullwhichoffices");
		}else{
		String where = "";
		if (StringUtil.isStrNotEmpty(whichoffices)) {
			where = "parent='" + whichoffices + "'";
		} else {
			where = "1=2";
		}
		this.setListWhere(where);
		}
		return super.getList();
		
	}
}
