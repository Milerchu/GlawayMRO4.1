package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <装箱单选择配件申请单号字段类>
 * 
 * @author public2795
 * @version [版本号, 2018-8-13]
 * @since [产品/模块版本]
 */
public class FldMrnum extends JpoField {
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
		String[] srcAttrs = { "mrnum" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 根据移动类型过滤配件申请信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("mr");
		String transfermovetype = this.getJpo().getString("transfermovetype");
		String listSql = "";
		if (transfermovetype.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) {
			listSql = listSql
					+ "status not in ('关闭','未处理') "
					+ "and mrnum in(select mrnum from mr where exists (select mrnum from mrline where exists "
					+ "(select a.mrnum from mr a where a.mrtype='项目' and a.mrnum=mrline.mrnum) and zxqty<ENDQTY and  mrline.mrnum=mr.mrnum group by mrnum) "
					+ "or exists (select mrnum from mrlinetransfer where transtype not in('计划经理协调','下达计划','退回申请人') and zxqty<transferqty and exists "
					+ "(select location from locations where STOREROOMLEVEL='中心库' and locations.location=mrlinetransfer.storeroom) and  "
					+ "mrlinetransfer.mrnum=mr.mrnum group by mrnum) "
					+ "or exists (select mrnum from mrlinetransfer where transtype  in('下达计划') and zxqty<jhqty and  mrlinetransfer.mrnum=mr.mrnum group by mrnum))";
		}
		if (transfermovetype.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) {
			listSql = listSql
					+ "status not in ('关闭','未处理') and MRTYPE ='零星' "
					+ "and  mrnum in(select mrnum from mr where exists (select mrnum from mrlinetransfer where transtype not in('计划经理协调','下达计划','退回申请人') "
					+ "and zxqty<transferqty and exists (select location from locations where STOREROOMLEVEL in('区域库','现场库','现场站点库') "
					+ "and locations.location=mrlinetransfer.storeroom) and  mrlinetransfer.mrnum=mr.mrnum group by mrnum) )";
		}

		if (!listSql.equals("")) {
			setListWhere(listSql);
		}

		return super.getList();
	}

	/**
	 * 触发赋值相关信息到装箱单
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String mrnum = this.getValue();
		if (!mrnum.isEmpty()) {
			String receivestoreroom = this.getJpo().getJpoSet("mr").getJpo()
					.getString("receivestoreroom");
			String RECEIVEADDRESS = this.getJpo().getJpoSet("mr").getJpo()
					.getString("RECEIVEADDRESS");
			String PROJECT = this.getJpo().getJpoSet("mr").getJpo()
					.getString("PROJECT");
			String model = this.getJpo().getJpoSet("mr").getJpo()
					.getString("model");
			this.getJpo().setFieldFlag("receivestoreroom",
					GWConstant.S_READONLY, false);
			this.getJpo().setValue("receivestoreroom", receivestoreroom);
			this.getJpo().setValue("RECEIVEADDRESS", RECEIVEADDRESS,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("receivestoreroom",
					GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("PROJECTNUM", PROJECT);
			this.getJpo().setValue("model", model);
			this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
					true);
		}
	}

}
