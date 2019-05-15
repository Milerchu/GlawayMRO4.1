package com.glaway.sddq.base.custinfo.data;

import java.util.Date;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 客户资料Jpo
 * 
 * @author  sqh
 * @version  [MRO4.0, 2016-6-1]
 * @since  [MRO4.0]
 */
public class CustInfo extends Jpo
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -6247352411157691585L;
    
    @Override
    public void init()
        throws MroException
    {
        if (isNew())
        {
            setFieldFlag("CUSTNUM", GWConstant.S_READONLY, false);
        }
        else
        {
            setFieldFlag("CUSTNUM", GWConstant.S_READONLY, true);
            if (getString("STATUS").equals("有效"))
            {
                setFieldFlag("CUSTNAME", GWConstant.S_READONLY, true);
                setFieldFlag("CONTACTTELPHONE", GWConstant.S_READONLY, true);
                setFieldFlag("LOCATION", GWConstant.S_READONLY, true);
                setFieldFlag("ADDRESS", GWConstant.S_READONLY, true);
                setFieldFlag("ZIPCODE", GWConstant.S_READONLY, true);
                setFieldFlag("CUSTCONTACTER", GWConstant.S_READONLY, true);
                setFieldFlag("DESIGNATIONS", GWConstant.S_READONLY, true);
                setFieldFlag("POSITION", GWConstant.S_READONLY, true);
                setFieldFlag("DESCRIPTION", GWConstant.S_READONLY, true);
              //setFieldFlag("ARMYTYPE", GWConstant.S_READONLY, true);
                setFieldFlag("WHICHARMY", GWConstant.S_READONLY, true);
            }
            else
            {
                IJpoSet jpoSet = this.getJpoSet("CUSTLOCATIONINFO");
                if (jpoSet != null)
                {
                    jpoSet.setFlag(GWConstant.S_READONLY, true);
                }
            }
        }
        super.init();
    }
    
    public void changestatus(String newstatus, Date date, String memo,String oldStatus)
        throws MroException
    {
        if (!newstatus.equals(""))
        {
            setValue("STATUS", newstatus);
            setValue("statusdate", date, 11L);
            IJpoSet ahstatusset = getJpoSet("CUSTINFOSTATUSHISTORY");
            IJpo ahstatusnew = ahstatusset.addJpo();
            ahstatusnew.setValue("CUSTNUM", getString("CUSTNUM"), 11L);
            ahstatusnew.setValue("siteid", getString("siteid"), 11L);
            ahstatusnew.setValue("orgid", getString("orgid"), 11L);
            ahstatusnew.setValue("changedate", date, 11L);
            ahstatusnew.setValue("status", newstatus, 11L);
            ahstatusnew.setValue("oldstatus", oldStatus, 11L);
            ahstatusnew.setValue("memo", memo, 11L);
            ahstatusnew.setValue("changeby", getUserInfo().getPersonId(), 11L);
            ahstatusnew.setFlag(7L, true);
        }
        else
        {
            throw new AppException("custinfo", "cannotnull");
        }
    }
    
    @Override
    public void delete()
        throws MroException
    {
        String custnum = getString("CUSTNUM");
        //判断客户是否被出所和实时台帐引用，被引用后不可删除
        IJpoSet jposet = getUserServer().getJpoSet("ASSET", "ASSETLEVEL = 'ASSET' and (ownercustomer ='" + StringUtil.getSafeSqlStr(custnum) + "' or overhauler = '" + StringUtil.getSafeSqlStr(custnum) + "' or maker='"+StringUtil.getSafeSqlStr(custnum) +"')");
        if (!jposet.isEmpty())
        {
            throw new AppException("custinfo", "cannotdelete3");
        }
        IJpoSet assetJposet =
            getUserServer().getJpoSet("ASSETCS", "maker ='" + StringUtil.getSafeSqlStr(custnum) + "'");
        if (!assetJposet.isEmpty())
        {
            throw new AppException("custinfo", "cannotdelete4");
        }
        
//        IJpoSet appServJposet =
//            getUserServer().getJpoSet("APPLICATIONSERV", "CUSTNUM ='" + StringUtil.getSafeSqlStr(custnum) + "'");
//        if (!appServJposet.isEmpty())
//        {
//            throw new AppException("custinfo", "cannotdelete5");
//        }
        
        if (getString("STATUS").equals("有效"))
        {
            throw new AppException("custinfo", "cannotdelete");
        }
        IJpoSet custinfo = getUserServer().getJpoSet("CUSTINFO");
        custinfo.setUserWhere("WHICHARMY='" + StringUtil.getSafeSqlStr(custnum) + "'");
        custinfo.reset();
        if (!custinfo.isEmpty())
        {
            throw new AppException("custinfo", "cannotdelete2");
        }
        if (this.getJpoSet("CUSTLOCATIONINFO") != null)
        {
            this.getJpoSet("CUSTLOCATIONINFO").deleteAll();
        }
        if (this.getJpoSet("CUSTCONTACTORHISTORY") != null)
        {
            this.getJpoSet("CUSTCONTACTORHISTORY").deleteAll();
        }
        if (this.getJpoSet("CUSTINFOSTATUSHISTORY") != null)
        {
            this.getJpoSet("CUSTINFOSTATUSHISTORY").deleteAll();
        }
        super.delete();
    }
    
    @Override
    public IJpo duplicate(long flag)
        throws MroException
    {
        IJpo jpo = super.duplicate(flag);
        jpo.setValueNull("CUSTNUM");
        String copyby = getUserInfo().getPersonId();
        Date copydate = MroServer.getMroServer().getDate();
        jpo.setValue("CREATEBY", copyby, GWConstant.P_NOVALIDATION);
        jpo.setValue("CREATEDATE", copydate, GWConstant.P_NOVALIDATION);
        return jpo;
    }
}
