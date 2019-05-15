package com.glaway.sddq.overhaul.plan.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 车型每月的预测/计划数量DataBean
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-10]
 * @since  [产品/模块版本]
 */
public class RepairPlanToTalDataBean extends DataBean
{
    
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        // int sum = 0;
        if (this.getJpo().isNew() || this.getJpo().toBeUpdated())
        {
            /*int one = this.getJpo().getInt("ONE");
            int two = this.getJpo().getInt("TWO");
            int three = this.getJpo().getInt("THREE");
            int four = this.getJpo().getInt("FOUR");
            int five = this.getJpo().getInt("FIVE");
            int six = this.getJpo().getInt("SIX");
            int seven = this.getJpo().getInt("SEVEN");
            int eight = this.getJpo().getInt("EIGHT");
            int nine = this.getJpo().getInt("NINE");
            int ten = this.getJpo().getInt("TEN");
            int eleven = this.getJpo().getInt("ELEVEN");
            int twelve = this.getJpo().getInt("TWELVE");*/
            
            //  sum = one + two + three + four + five + six + seven + eight + nine + ten + eleven + twelve;
            //this.getAppBean().getJpo().setValue("TOTALNUM", sum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            int planamount = this.getJpo().getInt("PLANAMOUNT");
            String cmodel = this.getJpo().getString("cmodel");
            String repairprocess = this.getJpo().getString("REPAIRPROCESS");
            String jpnum = this.getJpo().getString("JPNUM");//唯一编号
            IJpoSet repairscopeSet = this.getJpo().getJpoSet("REPAIRSCOPE");//检修范围
            IJpoSet productSet = this.getJpo().getJpoSet("PRODUCT");//产品列表
            IJpoSet stockaruslistSet = this.getJpo().getJpoSet("STOCKARUSLIST");
            //当检修方案变更时
            /*  if (this.getJpo().getField("SCHEMENUM").isValueChanged())
              {*/
            productSet.deleteAll();
            stockaruslistSet.deleteAll();
            for (int index = 0; index < repairscopeSet.count(); index++)
            {
                IJpo repairScope = repairscopeSet.getJpo(index);
                String jxcode = repairScope.getString("JOBBOOK.JXCODE");	
                String jxdesc = repairScope.getString("JOBBOOK.DESCRIPTION");	
                String productcode = repairScope.getString("JOBBOOK.PRODUCTCODE");	
                String productdesc = repairScope.getString("JOBBOOK.PRODUCTDESC");	
                
                String itemnum = repairScope.getString("ITEMNUM");
                int amount = repairScope.getInt("AMOUNT");
                String jopnum = repairScope.getString("SOPNUM");//标准作业指导书编号
                int palnamounts = amount * planamount;
                //新建产品列表
                IJpo jpo = productSet.addJpo();
                jpo.setValue("CMODEL", cmodel, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("REPAIRPROCESS", repairprocess, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("JXCODE", jxcode, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("jxdesc", jxdesc, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("productcode", productcode, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("productdesc", productdesc, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("AMOUNT", amount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("PLANNUM", planamount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("PALNAMOUNT", palnamounts, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("JPNUM", jpnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                
                IJpoSet overhaulJposet = MroServer.getMroServer().getSysJpoSet("SCHEMEOVERHAULMATERIAL");
                overhaulJposet.setUserWhere("jpnum='" + jopnum + "'");
                overhaulJposet.reset();
                
                for (int j = 0; j < overhaulJposet.count(); j++)
                {
                    IJpo stlist = stockaruslistSet.addJpo();
                    IJpo ovmjpo = overhaulJposet.getJpo(j);
                    String jitemnum = ovmjpo.getString("ITEMNUM");
                    int jamount = ovmjpo.getInt("AMOUNT");
                    String remark =ovmjpo.getString("REMARK");
                    int f = jamount * amount;
                    int totalamount = f * planamount;
                    stlist.setValue("CMODEL", cmodel, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("REPAIRPROCESS", repairprocess, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("JXCODE", jxcode, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("jxdesc", jxdesc, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("STOCKITEMNUM", jitemnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("AMOUNT", f, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("TOTALAMOUNT", totalamount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("JPNUM", jpnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("REMARK", remark, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("PLANNUM", planamount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    stlist.setValue("SCHEMEAMOUNT", jamount, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
            }
        }
        this.getAppBean().SAVE();
    }
    
}

/* @Override
 public void initJpoSet()
     throws MroException
 {
     super.initJpoSet();
     try
     {
         if (getJpoSet().count() > 0)
         {
             if (getPage().getControlByXmlId("150846323799731") != null)
             {
                 getPage().getControlByXmlId("150846323799731").hide();
             }
         }
         else
         {
             if (getPage().getControlByXmlId("150846323799731") != null)
             {
                 getPage().getControlByXmlId("150846323799731").show();
             }
         }
     }
     catch (IOException e)
     {
         e.printStackTrace();
     }
 }*/

