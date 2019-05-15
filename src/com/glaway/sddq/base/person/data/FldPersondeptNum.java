package com.glaway.sddq.base.person.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <人员选择站点过滤>
 * 
 * @author public2795
 * @version [版本号, 2018-8-15]
 * @since [产品/模块版本]
 */
public class FldPersondeptNum extends JpoField {
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
		String deptnums = "";
    	IJpoSet thisjpoSet = this.getJpo().getJpoSet();
    	for(int i=0; i<thisjpoSet.count();i++){
    		IJpo jpo = thisjpoSet.getJpo(i);
    		if(jpo!=this.getJpo()){
    			String deptnum =jpo.getString("deptnum");
    			if(StringUtil.isStrEmpty(deptnums)){
    				deptnums=" '"+deptnum+"' ";
    			}else{
    				deptnums=deptnums+" , '"+deptnum+"' ";
    			}
    		}
    	}
        setListObject("SYS_DEPT");
        String listSql = "";
        IJpo parentJpo =this.getJpo().getParent();
        if(parentJpo!=null){
        	 String department=parentJpo.getString("DEPARTMENT");
			if (StringUtil.isStrNotEmpty(deptnums)) {
				listSql = " deptnum not in ("
						+ deptnums
						+ ") and deptnum in (select deptnum from sys_dept start with DEPTNUM='"
						+ department
						+ "' connect by prior DEPTNUM=parent) and HIERARCHY in ('5级组织','6级组织')";
			} else {
				listSql = " deptnum in (select deptnum from sys_dept start with DEPTNUM='"
						+ department
						+ "' connect by prior DEPTNUM=parent) and HIERARCHY in ('5级组织','6级组织')";
			}

        }else{
        	listSql=" 1=2 ";
        }
        setListWhere(listSql);    
        return super.getList();
    }

	@Override
	protected void validateList() throws MroException {
	}
}
