package com.glaway.sddq.base.dept.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * <功能描述> 部门结构-站点人员
 * 
 * @author 杨毅
 * @version [MRO4.1, 2018-8-16]
 * @since [MRO4.1/]
 */
public class StationPersonBean extends DataBean
{
    @Override
    public void initialize()
        throws MroException
    {
        super.initialize();
        
    }
    
    @Override
    public synchronized void delete() throws MroException {
    	getJpo().setValue("STATION", "");
    	try {
			this.reloadPage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
