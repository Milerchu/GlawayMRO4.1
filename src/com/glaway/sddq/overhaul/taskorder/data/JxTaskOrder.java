package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 检修工单jpo
 * 
 * @author hyhe
 * @version [版本号, 2018-1-24]
 * @since [产品/模块版本]
 */
public class JxTaskOrder extends Jpo implements IJpo {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化时，设置字段只读
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		if (SddqConstant.JX_STATUS_CG.equals(getString("STATUS"))) {
			this.getJpoSet("JXTASKSWAPHISTORY").setFlag(GWConstant.S_READONLY,
					true);
		}
		if (SddqConstant.JX_STATUS_WC.equals(getString("STATUS"))) {
			this.getJpoSet("JXTASKSWAPHISTORY").setFlag(GWConstant.S_READONLY,
					true);
			setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
			setFieldFlag("WHICHOFFICES", GWConstant.S_READONLY, true);
			setFieldFlag("WHICHSTATION", GWConstant.S_READONLY, true);
			setFieldFlag("REPAIRKILOMETER", GWConstant.S_READONLY, true);
			setFieldFlag("REPAIRFACTORY", GWConstant.S_READONLY, true);
			setFieldFlag("STOCK", GWConstant.S_READONLY, true);
			setFieldFlag("PROJECTNUM", GWConstant.S_READONLY, true);
			setFieldFlag("PLANSTARTTIME", GWConstant.S_READONLY, true);
			setFieldFlag("PLANENDTIME", GWConstant.S_READONLY, true);
			setFieldFlag("REALSTARTTIME", GWConstant.S_READONLY, true);
			setFieldFlag("REALENDTIME", GWConstant.S_READONLY, true);
			setFieldFlag("LIABLEPERSON", GWConstant.S_READONLY, true);
			setFieldFlag("REPAIRPROCESS", GWConstant.S_READONLY, true);
			this.getJpoSet("JXPRODUCT").setFlag(GWConstant.S_READONLY, true);
			setFieldFlag("REMARK", GWConstant.S_READONLY, true);
		}
		if (SddqConstant.JX_STATUS_KG.equals(getString("STATUS"))) {
			setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
			setFieldFlag("REPAIRPROCESS", GWConstant.S_READONLY, true);
		}

	}

	/**
	 * 删除关联子项
	 * 
	 * @throws MroException
	 */
	@Override
	public void delete() throws MroException {

		if (this.getString("STATUS").equals("草稿")) {
			if (this.getJpoSet("JXPRODUCT") != null) {
				IJpoSet repairJpoSet = this.getJpoSet("JXPRODUCT");
				for (int index = 0; index < repairJpoSet.count(); index++) {
					repairJpoSet.getJpo(index).getJpoSet("JXTASKITEM")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("EXCHANGERECORD")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("EXCHANGERECORD2")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("JXTASKLOSSPART")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("JXTASKTOOLS")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("JXTASKEXECPERSON")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("JXTASKRECORD")
							.deleteAll();
					repairJpoSet.getJpo(index).getJpoSet("JXTESTRECORD")
							.deleteAll();

					IJpoSet jxtaskrecordSet = repairJpoSet.getJpo().getJpoSet(
							"JXTASKRECORD");
					for (int ind = 0; ind < jxtaskrecordSet.count(); ind++) {
						jxtaskrecordSet.getJpo(ind).getJpoSet("PROBLEMMG")
								.deleteAll();
					}
					IJpoSet jxtestrecordSet = repairJpoSet.getJpo().getJpoSet(
							"JXTESTRECORD");
					for (int ind = 0; ind < jxtestrecordSet.count(); ind++) {
						jxtestrecordSet.getJpo(ind)
								.getJpoSet("JXTESTRECORDITEM").deleteAll();
						jxtestrecordSet.getJpo(ind).getJpoSet("PROBLEMMG")
								.deleteAll();
					}
				}
				this.getJpoSet("JXPRODUCT").deleteAll();
			}
			if (this.getJpoSet("MUSTCHANGEINFO") != null) {
				this.getJpoSet("MUSTCHANGEINFO").deleteAll();
			}
		} else {
			throw new AppException("jxtaskorder", "notdelete");
		}
		super.delete();
	}
}
