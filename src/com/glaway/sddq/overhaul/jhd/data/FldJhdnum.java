package com.glaway.sddq.overhaul.jhd.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
/**
 * 
 * 交货单校验字段类
 * 
 * @author  public2175
 * @version  [版本号, 2018-12-26]
 * @since  [产品/模块版本]
 */
public class FldJhdnum extends JpoField {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 判断交货单号是否重复
	 * @throws MroException
	 */
	@Override
	public void validate() throws MroException {
		String jhdnum = this.getInputMroType().getStringValue();
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("JXDEORDER", "JHNUM='"+jhdnum+"'");
		if(jposet != null && jposet.count() > 0){
			throw new AppException("jxjhd", "hasexist");
		}
	}
	
}
