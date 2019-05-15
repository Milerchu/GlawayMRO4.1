package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * <缴库单查找送修单信息>
 * 
 * @author 20167088
 * @version [版本号, 2018-7-18]
 * @since [产品/模块版本]
 */
public class FldSendNum extends JpoField {
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "transfernum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤送修单
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("transfer");

		String listSql = "";

		listSql = "type='SX'";

		setListWhere(listSql);
		return super.getList();
	}

	/**
	 * 赋值送修单信息到缴库单
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		IJpoSet transferLineSet = this.getJpo().getJpoSet("$TRANSFER",
				"TRANSFER",
				"TRANSFERNUM='" + this.getJpo().getString("SENDNUM") + "'");
		if (!transferLineSet.isEmpty()) {
			IJpo transfer = transferLineSet.getJpo(0);
			this.getJpo().setValue("SENDORG", transfer.getString("SENDORG"));
			this.getJpo().setValue("SENDDATE", transfer.getDate("SENDDATE"));
			this.getJpo().setValue("CUSTNUM", transfer.getString("CUSTNUM"));
			this.getJpo().setValue("sxtype", transfer.getString("sxtype"));
			this.getJpo()
					.setValue("CONTACTBY", transfer.getString("CONTACTBY"));
			this.getJpo().setValue("CONTACTPHONE",
					transfer.getString("CONTACTPHONE"));
			this.getJpo()
					.setValue("REPAIRORG", transfer.getString("REPAIRORG"));
			this.getJpo()
					.setValue("RECEIVEBY", transfer.getString("RECEIVEBY"));
			this.getJpo().setValue("RECEIVEDATE",
					transfer.getDate("RECEIVEDATE"));
			this.getJpo().setValue("RECEIVECHECKBY",
					transfer.getString("RECEIVECHECKBY"));
			this.getJpo().setValue("SCALENUM", transfer.getString("SCALENUM"));
			this.getJpo().setValue("COMPANY", transfer.getString("COMPANY"));
			this.getJpo().setValue("ISSUESTOREROOM",
					transfer.getString("ISSUESTOREROOM"), 112L); // 送修库房2018-08-07guan
			IJpoSet jpoSet = this.getJpo().getJpoSet("$LOCATIONS", "LOCATIONS",
					"LOCATION='" + transfer.getString("ISSUESTOREROOM") + "'");
			if (!jpoSet.isEmpty()) {
				IJpo jpo = jpoSet.getJpo(0);
				this.getJpo().setValue("keeper", jpo.getString("keeper"));
			}

		}
		super.action();
	}

}
