package com.glaway.sddq.material.materborrowline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <配件借用行序列号字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-24]
 * @since  [产品/模块版本]
 */
public class FldSeqnum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName(),"assetnum"};
		String[] srcAttrs = { "sqn","assetnum"};
		setLookupMap(thisAttrs, srcAttrs);
	}
	/**
	 * 过滤序列号数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("ASSET");
		String BORROWSTOREROOM=this.getJpo().getParent().getString("BORROWSTOREROOM");//借出库房
		String itemnum=this.getJpo().getString("itemnum");//借出的物料编码
		String listSql = "itemnum='"+itemnum+"' and LOCATION='"+BORROWSTOREROOM+"' and assetlevel='ASSET' and type in ('1','3') and status='可用'" +
				"and sqn not in (select sqn from asset where itemnum='"+itemnum+"' and LOCATION='"+BORROWSTOREROOM+"' and assetlevel='ASSET' and type in ('1','3') and sqn like 'LS%')";
		
		if (!listSql.equals("")) {
			setListWhere(listSql);
		}
		return super.getList();
	}
}
