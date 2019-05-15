package com.glaway.sddq.material.mrline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

public class FldItemnum extends JpoField {
//	@Override
//	public void init()
//			throws MroException
//	{
//		super.init();	
//		String[] thisAttrs = { this.getFieldName()};
//		String[] srcAttrs = { "itemnum"};
//		setLookupMap(thisAttrs, srcAttrs);
//	}
	@Override
	public IJpoSet getList()
			throws MroException
	{
		String[] thisAttrs = { this.getFieldName()};
		String[] srcAttrs = { "itemnum"};
		setLookupMap(thisAttrs, srcAttrs);
		String listSql = "";

			setListObject("sys_item");

				IJpoSet thisjposet=this.getJpo().getJpoSet();
				String itemnum = null;
			       for (int j = 0; j < thisjposet.count(); j++) {
			           IJpo mprline = thisjposet.getJpo(j);
			           String thisitemnum=mprline.getString("ITEMNUM");
			           if(!StringUtil.isStrEmpty(thisitemnum)){
			        	   if(StringUtil.isStrEmpty(itemnum)){
			        		   itemnum ="'" + thisitemnum + "'";  
			        	   }else{
			        		   itemnum = itemnum+ ",'" + thisitemnum + "'";  
			        	   }
			        	   
			           }
			       }

			       if (itemnum != null) {
			    	   listSql = " itemnum not in(" + itemnum + ")";
			       }else{
			    	   listSql ="1=1";
			       }
		setListWhere(listSql);

		return super.getList();
	}
}
