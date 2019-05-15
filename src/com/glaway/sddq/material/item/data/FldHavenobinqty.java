package com.glaway.sddq.material.item.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <物料管理是否未分配仓位数量绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class FldHavenobinqty extends JpoField {
/**
 * 初始化计算数量
 * @throws MroException
 */
	@Override
    public void init()
        throws MroException
    {
        super.init();
        if (getJpo() != null  && !StringUtil.isNullOrEmpty(this.getJpo().getAppName()))
        {
            setValue(getSumCurbaltotal() + "", GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
        }
    }
/**
 *     
 * <计算数量方法>
 * @return
 * @throws MroException [参数说明]
 *
 */
    private double getSumCurbaltotal()
        throws MroException
    {
        double total = 0;
        double have = 0;
        double locbin = 0;
        if (getJpo() != null)
        {
        	
             String itemnum = getJpo().getString("itemnum");
             String type = ItemUtil.getItemInfo(itemnum);
             String appname=this.getJpo().getAppName();
           if(appname!=null){
        	   if (ItemUtil.SQN_ITEM.equals(type))
               {//按批次管理但是周转件需要统计所有在库存中的相同物资编码的部件的数量 
               	if(appname.equalsIgnoreCase("INVENTORY")){//1020库存
               		IJpoSet assetSet = getJpo().getJpoSet("NOBINNUM");
                       total = assetSet.count();
                       if(total>0){
                       	have=1;
                       }
               	}
               	if(appname.equalsIgnoreCase("INV1030")){//1030库存
               		IJpoSet assetSet = getJpo().getJpoSet("NOBINNUM1030");
                       total = assetSet.count();
                       if(total>0){
                       	have=1;
                       }
               	}
               	if(appname.equalsIgnoreCase("GZINV")){//改造库存
               		IJpoSet assetSet = getJpo().getJpoSet("GZNOBINNUM");
                       total = assetSet.count();
                       if(total>0){
                       	have=1;
                       }
               	}
               	if(appname.equalsIgnoreCase("QTINV")){//其他待处理库存
               		IJpoSet assetSet = getJpo().getJpoSet("QTNOBINNUM");
                       total = assetSet.count();
                       if(total>0){
                       	have=1;
                       }
               	}
                   
               }
               else
               {
                   if (ItemUtil.LOT_I_ITEM.equals(type))
                   {
                       //按批次管理但是不是周转件只需要统计所有批次余量
                   	if(appname.equalsIgnoreCase("INVENTORY")){//1020库存
                   		IJpoSet invbSet = getJpo().getJpoSet("INVBLANCE");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("locbinitem");
                           total = invbSet.sum("PHYSCNTQTY");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                   	if(appname.equalsIgnoreCase("INV1030")){//1030库存
                   		IJpoSet invbSet = getJpo().getJpoSet("INVBLANCE1030");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("locbinitem1030");
                           total = invbSet.sum("PHYSCNTQTY");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                   	if(appname.equalsIgnoreCase("GZINV")){//改造库存
                   		IJpoSet invbSet = getJpo().getJpoSet("GZINVBLANCE");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("gzlocbinitem");
                           total = invbSet.sum("PHYSCNTQTY");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                   	if(appname.equalsIgnoreCase("QTINV")){//其他待处理库存
                   		IJpoSet invbSet = getJpo().getJpoSet("QTINVBLANCE");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("qtlocbinitem");
                           total = invbSet.sum("PHYSCNTQTY");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                       
                   }
                   else
                   {
                   	if(appname.equalsIgnoreCase("INVENTORY")){//1020库存
                   		IJpoSet invbSet = getJpo().getJpoSet("INVENTORY");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("locbinitem");
                           total = invbSet.sum("CURBAL");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                   	if(appname.equalsIgnoreCase("INV1030")){//1030库存
                   		IJpoSet invbSet = getJpo().getJpoSet("INV1030");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("locbinitem1030");
                           total = invbSet.sum("CURBAL");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                   	if(appname.equalsIgnoreCase("GZINV")){//改造库存
                   		IJpoSet invbSet = getJpo().getJpoSet("GZINV");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("gzlocbinitem");
                           total = invbSet.sum("CURBAL");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                   	if(appname.equalsIgnoreCase("QTINV")){//其他待处理库存
                   		IJpoSet invbSet = getJpo().getJpoSet("QTINV");
                           IJpoSet locbinitemSet = getJpo().getJpoSet("qtlocbinitem");
                           total = invbSet.sum("CURBAL");
                           locbin = locbinitemSet.sum("qty");
                           if(total-locbin>0){
                           	have=1;
                           }
                   	}
                       
                   }
               }
           }
        }
        return have;
    }
}
