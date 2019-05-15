package com.glaway.sddq.material.convertlocline.data;

import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <调拨转库单行序列号字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class FldSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	// @Override
	// public void action() throws MroException {
	// // TODO Auto-generated method stub
	// super.action();
	// String newsqn=this.getValue();
	// String ITEMNUM=this.getJpo().getParent().getString("ITEMNUM");
	// IJpoSet assetset=MroServer.getMroServer().getJpoSet("asset",
	// MroServer.getMroServer().getSystemUserServer());
	// assetset.setUserWhere("itemnum='"+ITEMNUM+"' and sqn='"+newsqn+"'");
	// assetset.reset();
	// if(!assetset.isEmpty()){
	// throw new MroException("序列号重复");
	// }
	// }

}
