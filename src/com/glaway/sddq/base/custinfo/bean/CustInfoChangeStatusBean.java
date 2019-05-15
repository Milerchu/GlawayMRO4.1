package com.glaway.sddq.base.custinfo.bean;

import java.io.IOException;
import java.util.Date;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.base.custinfo.data.CustInfo;

public class CustInfoChangeStatusBean extends DataBean
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
        CustInfo ct = (CustInfo)mr;
        Date curDate = MroServer.getMroServer().getDate();
        checkSave();
        String oldStatus = getAppBean().getJpo().getString("status");
        
        //zzx add
//        String oldStatus = getString("status");
//        
//        IJpoSet hisJpoSet = mr.getJpoSet("CUSTINFOSTATUSHISTORY");
//        IJpo hisJpo = hisJpoSet.addJpo(GWConstant.P_NOVALIDATION_AND_NOACTION);
//        
//        hisJpo.setValue("oldstatus", oldStatus, GWConstant.P_NOVALIDATION_AND_NOACTION); 
        //zzx add
        
        //判断客户是否被出所和实时台帐引用，被引用后不可设置无效
        //        if ("无效".equals(getString("status")))
        //        {
        //            String num = mr.getString("CUSTNUM");
        //            IJpoSet jposet =
        //                this.getUserInfo().getUserServer().getJpoSet("ASSETCS",
        //                    "CUSTNUM ='" + StringUtil.getSafeSqlStr(num) + "'");
        //            if (!jposet.isEmpty())
        //            {
        //                throw new AppException("custinfo", "canNoDel");
        //            }
        //            IJpoSet assetJposet =
        //                this.getUserInfo().getUserServer().getJpoSet("ASSET",
        //                    "CUSTNUM ='" + StringUtil.getSafeSqlStr(num) + "'");
        //            if (!assetJposet.isEmpty())
        //            {
        //                throw new AppException("custinfo", "canNoDel");
        //            }
        //        }
        
        ct.changestatus(getString("status"), curDate, getString("memo"),oldStatus);
        try
        {
            getAppBean().SAVE();
            this.reloadPage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 1;
    }
}
