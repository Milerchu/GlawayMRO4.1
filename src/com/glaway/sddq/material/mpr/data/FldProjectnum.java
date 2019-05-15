package com.glaway.sddq.material.mpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <检修领料单项目编号字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-8-2]
 * @since  [产品/模块版本]
 */
public class FldProjectnum extends JpoField {
	/**
	 * 映射赋值
	 * @throws MroException
	 */
	@Override
    public void init()
        throws MroException
    {
        super.init();
        String[] thisAttrs = {this.getFieldName(),"WORKORDERNUM"};
        String[] srcAttrs = {"projectnum","WORKORDERNUM"};
        setLookupMap(thisAttrs, srcAttrs);
    }
	/**
	 * 过滤项目信息
	 * @return
	 * @throws MroException
	 */
	 @Override
	    public IJpoSet getList()
	        throws MroException
	    {
		 setListObject("PROJECTINFO");
	        String listSql = "";
		    listSql = listSql + "STATUS='已立项'";	        
	        if (!listSql.equals(""))
	        {
	            setListWhere(listSql);
	        }
	        return super.getList();
	    }
}
