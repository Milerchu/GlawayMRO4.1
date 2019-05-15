package com.glaway.sddq.config.soft.data;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 软件配置库车号字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-5-30]
 * @since  [产品/模块版本]
 */
public class FldCarNo extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
        this.setLookupMap(new String[] {"CARNO", "ASSETNUM","CMODEL"}, new String[] {"CARNO", "ASSETNUM","CMODEL"});
	}

	@Override
	public IJpoSet getList() throws MroException {
		
		if(this.getJpo() != null && this.getJpo().getParent() != null){
			
			String cmodel = this.getJpo().getParent().getString("CMODEL");//车型
			if(StringUtils.isEmpty(cmodel)){
				
				throw new AppException("ASSET", "cmodelisnull");
				
			}else{
				
				this.setListObject("ASSET");
		        this.setListWhere("assetlevel = 'ASSET' and cmodel ='" + cmodel + "' and type='2'");
		        return super.getList();
		        
			}
		}
		return null;
	}
}
