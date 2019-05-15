package com.glaway.sddq.overhaul.jhd.data;

import org.h2.util.StringUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 库房字段类
 * 
 * @author public2175
 * @version [版本号, 2019-1-21]
 * @since [产品/模块版本]
 */
public class FldLocation extends JpoField {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取父级的库房（ERP）对应的MRO库房
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("LOCATIONS");
		if (this.getJpo() != null && this.getJpo().getParent() != null) {
			String location = this.getJpo().getParent().getString("LGORT");
			if (!StringUtils.isNullOrEmpty(location)) {
				this.setListWhere("location = '" + location
						+ "' or STOREROOMPARENT = '" + location + "'");
			} else {
				this.setListWhere("1=2");
			}
		} else {
			this.setListWhere("1=2");
		}
		return super.getList();
	}

	/**
	 * 根据选择的库房和物料计算出
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		if (this.getJpo() != null && this.getJpo().getParent() != null) {
			String itemnum = this.getJpo().getParent().getString("ITEMNUM");
			if (!StringUtils.isNullOrEmpty(itemnum)) {
				itemnum = ItemUtil.getItemnumFor400(itemnum);
				IJpoSet jposet = MroServer.getMroServer().getSysJpoSet(
						"SYS_INVENTORY",
						"LOCATION='" + this.getValue() + "' and itemnum='"
								+ itemnum + "'");
				if (jposet != null && jposet.count() > 0) {
					
					double kyqty = ItemUtil.getKyQty(jposet.getJpo(0), itemnum, this.getValue());
					this.getField("LOCQTY").setValue(String.valueOf(kyqty),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				} else {
					double kyqty = 0;
					this.getField("LOCQTY").setValue(String.valueOf(kyqty),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
		}
		if (this.isValueChanged()) {
			this.getField("QTY").setValueNull(
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
}
