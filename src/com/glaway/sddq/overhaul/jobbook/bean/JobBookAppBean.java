package com.glaway.sddq.overhaul.jobbook.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 标准作业指导书AppBean
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-11]
 * @since  [产品/模块版本]
 */
public class JobBookAppBean extends AppBean
{
    /**
     * 
     * 版本变更
     * @return
     * @throws MroException
     * @throws IOException [参数说明]
     *
     */
    // public int UPVERSION()
    //   throws MroException, IOException
    // {
    //    return super.DUPLICATE();
    // }
    
    /**
     * 变更状态前判断
     */
    public int STATUS()
        throws MroException, IOException
    {
        //判断是否被引用，如果已经被引用，则不可变更状态
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("REPAIRSCOPE");
        jposet.setUserWhere("SOPNUM='" + this.getJpo().getString("JPNUM") + "'");
        if (jposet != null && jposet.count() > 0)
        {
        	
        	String schemenum =jposet.getJpo().getString("SCHEMENUM");
        	IJpoSet repairschemeSet = MroServer.getMroServer().getSysJpoSet("REPAIRSCHEME");
        	repairschemeSet.setQueryWhere("SCHEMENUM='"+schemenum+"'");
        	String status=repairschemeSet.getJpo().getString("STATUS");
        	String isforsche=schemenum;
        	if(status.equals("已发布")){
            throw new AppException("jobbook", "isForSche",new String[] { isforsche });
            
        	}else{
        		return GWConstant.ACCESS_SAMEMETHOD;
        	}
        }
        else
        {
            return GWConstant.ACCESS_SAMEMETHOD;
        }

    }
}
