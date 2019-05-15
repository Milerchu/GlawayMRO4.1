package com.glaway.sddq.overhaul.scheme.bean;

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
import com.glaway.sddq.overhaul.scheme.data.RepairScheme;

/**
 * 
 * 检修方案版本变更
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-17]
 * @since  [产品/模块版本]
 */
public class RepairSchemeVersionDataBean extends DataBean
{
    @Override
    public int execute()
        throws MroException
    {
        IJpo mr = getAppBean().getJpo();
        //列表数据为空时
        if (mr == null)
        {
            return GWConstant.NOACCESS_SAMEMETHOD;
        }
        checkSave();
        RepairScheme ct = (RepairScheme)mr;
        String oldVersion = mr.getString("VERSION");
        String newVersion = this.getString("VERSION");
        if (oldVersion.equals(newVersion))
        {
            throw new AppException("RepairScheme", "isVersion");
        }
        else
        {
            String cmodel = mr.getString("CMODEL");
            String repairProcess = mr.getString("REPAIRPROCESS");
            IJpoSet jposet =
                MroServer.getMroServer().getSysJpoSet("REPAIRSCHEME",
                    "cmodel='" + cmodel + "' and repairProcess='" + repairProcess + "' and VERSION='" + newVersion
                        + "'");
            if (jposet != null && jposet.count() > 0)
            {
                throw new AppException("RepairScheme", "isVersion");
            }
            else
            {
                IJpo newJpo = ct.duplicate();
                newJpo.setValue("version", newVersion, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                try
                {
                    ct.changeVersion(getString("version"), getString("memo"), newJpo);
                    this.getAppBean().SAVE();
                    this.getAppBean().selectRow(0);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
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
