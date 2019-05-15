package com.glaway.sddq.material.materborrow.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.proxy.ActionCustomClass;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.material.invtrans.common.CommonInventory;
import com.glaway.sddq.material.invtrans.common.InventoryQtyCommon;
/**
 * 
 * <配件借用流程关闭操作类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-26]
 * @since  [产品/模块版本]
 */
public class StatusClose implements ActionCustomClass {
	@Override
	public void executeCustomAction(IJpo jpo, String parameter)
			throws MroException {
		// TODO Auto-generated method stub
		if (jpo != null) {
			jpo.setValue("status", "关闭",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			String BORROWSTOREROOM=jpo.getString("BORROWSTOREROOM");//借出库房
			String RETURNSTOREROOM=jpo.getString("RETURNSTOREROOM");//借入库房
			IJpoSet materborrowlineset=jpo.getJpoSet("materborrowline");//获取借用行信息
			if(!materborrowlineset.isEmpty()){
				for(int i=0;i<materborrowlineset.count();i++){
					IJpo materborrowline=materborrowlineset.getJpo(i);
					String itemnum=materborrowline.getString("itemnum");
					String lotnum=materborrowline.getString("lotnum");
					String assetnum=materborrowline.getString("assetnum");
					String tasknum="";
					double qty=materborrowline.getDouble("qty");
					CommonInventory.OUTINVENTORY(lotnum, qty, RETURNSTOREROOM, itemnum, assetnum, tasknum);
					CommonInventory.ININVENTORY(lotnum, qty, BORROWSTOREROOM, itemnum, assetnum, tasknum);
					
					InventoryQtyCommon.SQZYQTY(itemnum, BORROWSTOREROOM);
					InventoryQtyCommon.FCZTQTY(itemnum, BORROWSTOREROOM);
					InventoryQtyCommon.JSZTQTY(itemnum, BORROWSTOREROOM);
					InventoryQtyCommon.KYQTY(itemnum, BORROWSTOREROOM);
					
					InventoryQtyCommon.SQZYQTY(itemnum, RETURNSTOREROOM);
					InventoryQtyCommon.FCZTQTY(itemnum, RETURNSTOREROOM);
					InventoryQtyCommon.JSZTQTY(itemnum, RETURNSTOREROOM);
					InventoryQtyCommon.KYQTY(itemnum, RETURNSTOREROOM);
				}
			}
			jpo.getJpoSet().save();
		}
	}
}
