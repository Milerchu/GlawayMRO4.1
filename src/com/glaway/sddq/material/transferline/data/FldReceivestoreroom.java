package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <调拨单行接收库房字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldReceivestoreroom extends JpoField {
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
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "LOCATION" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 根据移动类型过滤接收库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		String TRANSFERMOVETYPE = this.getJpo().getParent()
				.getString("TRANSFERMOVETYPE");// 装箱单移动类型
		String mrnum = this.getJpo().getParent().getString("mrnum");
		String erploc = this.getJpo().getParent().getJpoSet("issuestoreroom")
				.getJpo().getString("erploc");
		setListObject("LOCATIONS");
		if (mrnum.isEmpty()) {
			if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL='中心库' and erploc='"
						+ erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc='"
						+ erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and erploc='"
						+ erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('中心库','现场库','区域库','现场站点库') and erploc='"
						+ erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
		} else {
			if (TRANSFERMOVETYPE.equalsIgnoreCase("中心到现场")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL='中心库' and location in (select storeroom from mrlinetransfer where mrnum='"
						+ mrnum + "') and erploc='" + erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到现场")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and location in (select storeroom from mrlinetransfer where mrnum='"
						+ mrnum + "') and erploc='" + erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
			if (TRANSFERMOVETYPE.equalsIgnoreCase("现场到中心")) {
				String Sql = "location not in (select location from locations where ISSAPSTORE=1) and STOREROOMLEVEL in('现场库','区域库','现场站点库') and location in (select storeroom from mrlinetransfer where mrnum='"
						+ mrnum + "') and erploc='" + erploc + "'";
				Sql=Sql+"and status='正常'";
				setListWhere(Sql);
			}
		}

		return super.getList();
	}

	/**
	 * 赋值接收库房地址
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		/* 选择库房代入默认地址 */
		IJpoSet locaddressSet = this.getJpo().getJpoSet("$LOCADDRESS",
				"locaddress",
				"LOCATION='" + this.getValue() + "' and isaddress='是'");
		if (!locaddressSet.isEmpty()) {
			IJpo locaddress = locaddressSet.getJpo(0);
			this.getJpo().setValue("RECEIVEADDRESS",
					locaddress.getString("address"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}
}
