package com.glaway.sddq.overhaul.scheme.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * 检修方案版本字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-8]
 * @since  [产品/模块版本]
 */
public class FldVersion extends JpoField {


	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void validate() throws MroException {
		// TODO Auto-generated method stub
		super.validate();
		String version =this.getInputMroType().asString();
		String reg="^[a-zA-Z0-9]*$";
		Pattern pat =Pattern.compile(reg);
		Matcher mat=pat.matcher(version);
		//System.out.println(version+"  "+Pattern.matches(reg, version));
		if(!Pattern.matches(reg, version))
		{
			throw new AppException("jobbook", "version");
		}			
	}
}
