package com.glaway.sddq.config.zcconfig.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 仓位字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-5-21]
 * @since  [产品/模块版本]
 */
public class FldBinnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("locbin");
		String location = this.getJpo().getString("LOCATION");
		if(this.getJpo() != null && StringUtils.isNotEmpty(location)){
//			IJpoSet jposet = this.getUserServer().getJpoSet("locbin", "LOCATION='"+location+"'");
//			if(jposet != null && jposet.count() >0){
//				return jposet;
//			}
			this.setListWhere("LOCATION='"+location+"'");
		}else{
			this.setListWhere("1=2");
		}
		return super.getList();
	}
	
}
