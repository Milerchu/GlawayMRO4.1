package com.glaway.sddq.base.person.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 人员信息中的岗位信息字段类
 * 
 * @author bchen
 */
public class FldJobCode extends JpoField {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void init()
        throws MroException
    {
//        this.setLookupMap(new String[] {"JOBCODE", "DEPARTMENT" }, new String[] {"POSTNUM", "DETPNUM" });
    }

    @Override
    public IJpoSet getList() throws MroException {
        
          IJpoSet list = super.getList(); 
          String deptnum =this.getJpo().getString("DEPARTMENT");
          String mdm_deptid ="";
          if (!StringUtil.isStrEmpty(deptnum))
          { 
        	  IJpoSet deptSet = MroServer.getMroServer().getSysJpoSet("sys_dept");
        	  deptSet.setUserWhere(" deptnum = '"+deptnum+"' ");
        	  deptSet.reset();
        	  if(!deptSet.isEmpty()){
        		  mdm_deptid= deptSet.getJpo().getString("MDM_DEPTID");
        		  list.setUserWhere("  detpnum='"+deptnum+"' or detpnum='"+mdm_deptid+"' "); 
            	  list.reset(); 
            	  return list;
        	  }
          }
		return list;
    }

    @Override
    public void action() throws MroException {
    	  super.action();
        String jobcode = this.getValue();
        String personid = this.getJpo().getString("PERSONID");
        IJpoSet postset = this.getJpo().getJpoSet("post");
        String director = this.getJpo().getString("POST.DIRECTOR");
        IJpoSet deptset = postset.getJpo().getJpoSet("SYS_DEPT");
        if (jobcode != null) {
            if (director.equals("是")&&!deptset.isEmpty()) {
                    IJpo deptsetpo = deptset.getJpo();
                    deptsetpo.setValue("OWNER", personid, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//                deptset.save();
            }
        }
      
    }
}
