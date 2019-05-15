package com.glaway.sddq.overhaul.plan.bean;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.overhaul.plan.data.RepairPlan;

/**
 * 年度计划版本变更
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-18]
 * @since  [产品/模块版本]
 */
public class RepairYearPlanVersionDataBean extends DataBean
{
    
    @Override
    public int execute()
        throws MroException
    {
        
        IJpo mr = getAppBean().getJpo();
        if (mr == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        checkSave();
        RepairPlan yr = (RepairPlan)mr;
        String oldVersion = mr.getString("VERSION");
        String newVersion = this.getString("VERSION");
        if (oldVersion.equals(newVersion))
        {
            throw new AppException("RepairPlan", "isversion");
        }
        String cmodel = mr.getString("CMODEL");
        String repairprocess = mr.getString("REPAIRPROCESS");
        String factory = mr.getString("FACTORY");
        IJpoSet jposet =
            MroServer.getMroServer().getSysJpoSet("REPAIRPLANS",
                "cmodel='" + cmodel + "'and repairprocess='" + repairprocess + "'and factory='" + factory
                    + "' and version='" + newVersion + "'");
        if (jposet != null && jposet.count() > 0)
        {
            throw new AppException("RepairPlan", "isversion");
        }
        else
        {
            IJpo newjpo = yr.duplicate();
            newjpo.setValue("VERSION", newVersion);
            yr.changeVersion(getString("version"), getString("memo"), newjpo);
            try
            {
                this.getAppBean().SAVE();
                this.getAppBean().selectRow(0);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    //版本上级字段控制
    @Override
    public void initialize() throws MroException {
    	super.initialize();
    	
    	String version = getParent().getString("version");
		String intreg="^.*((?<!\\d)\\d+).*$";
		Pattern intpat =Pattern.compile(intreg);
		Matcher m = intpat.matcher(version);
		if(m.matches()){
			int lastIndex =version.lastIndexOf(m.group(1));
			String prefix = version.substring(0,lastIndex);
			String suffix = m.group(1);
			int versonCount = Integer.parseInt(suffix);
			version = prefix+(versonCount+1);
		}
		setValue("version", version);
    }
}
