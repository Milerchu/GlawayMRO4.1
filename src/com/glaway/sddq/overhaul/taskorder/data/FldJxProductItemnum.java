package com.glaway.sddq.overhaul.taskorder.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
/**
 * 
 * 检修工单中的检修产品列表中的物料的字段类
 * 
 * @author  public2175
 * @version  [版本号, 2018-12-19]
 * @since  [产品/模块版本]
 */
public class FldJxProductItemnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public IJpoSet getList() throws MroException {
		String jpnum = this.getJpo().getString("JPNUM");
		if(StringUtil.isNullOrEmpty(jpnum)){
			throw new AppException("jxtaskorder", "selectJp");
		}
		this.setListObject("SYS_ITEM");
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("JOBPRODUCTSCOPE", "JPNUM='"+jpnum+"'");
		if(jposet != null && jposet.count() >0){
			String itemnums = "";
			for (int index = 0; index < jposet.count(); index++) {
				itemnums = itemnums + "'"
						+ jposet.getJpo(index).getString("itemnum") + "',";
			}
			if (!StringUtil.isNullOrEmpty(itemnums)) {
				if (itemnums.endsWith(",")) {
					itemnums = itemnums.substring(0, itemnums.length() - 1);
				}
				this.setListWhere("itemnum in (" + itemnums + ")");
			} else {
				this.setListWhere("1=2");
			}
		}else{
			this.setListWhere("1=2");
		}
		return super.getList();
	}
}
