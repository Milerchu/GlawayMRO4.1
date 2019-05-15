package com.glaway.sddq.base.person.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <人员选择站点过滤>
 * 
 * @author  public2795
 * @version  [版本号, 2018-8-15]
 * @since  [产品/模块版本]
 */
public class FldStation extends JpoField {
	@Override
    public void init()
        throws MroException
    {
        super.init();
        String[] thisAttrs = {this.getFieldName()};
        String[] srcAttrs = {"deptnum"};
        setLookupMap(thisAttrs, srcAttrs);
    }
    @Override
    public IJpoSet getList()
        throws MroException
    {
        setListObject("SYS_DEPT");
        
        String DEPARTMENT=this.getJpo().getString("DEPARTMENT");
        String listSql = "";

        	listSql = "deptnum in (select deptnum from sys_dept start with DEPTNUM='"+DEPARTMENT+"' connect by prior DEPTNUM=parent) and HIERARCHY in ('5级组织','6级组织')";   

        
        setListWhere(listSql);    
        return super.getList();
    }
}
