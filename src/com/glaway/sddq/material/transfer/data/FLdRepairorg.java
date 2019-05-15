package com.glaway.sddq.material.transfer.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <送修单承修单位字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FLdRepairorg extends JpoField {
	/**
	 * 根据承修单位设置返修日期和接收库房
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		if (getJpo() != null
				&& !StringUtil.isStrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("SXTRANSFER")) {
				String reparirorg = this.getValue();
				Date SENDDATE = this.getJpo().getDate("SENDDATE");
				String ISSUESTOREROOM = this.getJpo().getString(
						"ISSUESTOREROOM");
				if (!ISSUESTOREROOM.isEmpty()) {
					if (SENDDATE != null) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						String PLANREPAURDATE = "";
						if (reparirorg.equalsIgnoreCase(ItemUtil.REPAIRORG_JC)) {// 承修单位是集采的，应修复日期在送修日期基础上加45天，且接收库房是物流子维修库
							PLANREPAURDATE = df.format(new Date(SENDDATE
									.getTime()
									+ ((long) 45 * 24 * 60 * 60 * 1000)));
							if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")) {
								this.getJpo().setValue("receivestoreroom",
										ItemUtil.RECEIVESTOREROOM_WLZWXK);
							}
							if (ISSUESTOREROOM.equalsIgnoreCase("GZ1080")) {
								this.getJpo().setValue("receivestoreroom",
										"GZ1082");
							}
							if (ISSUESTOREROOM.equalsIgnoreCase("QT1080")) {
								this.getJpo().setValue("receivestoreroom",
										"QT1082");
							}
							this.getJpo().setFieldFlag("SENDBY",
									GWConstant.S_REQUIRED, false);
							this.getJpo().setValue("SENDBY", "",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							this.getJpo().setFieldFlag("SENDBY",
									GWConstant.S_READONLY, true);
						} else {
							if (reparirorg
									.equalsIgnoreCase(ItemUtil.REPAIRORG_ZZ)) {// 修单位是制造的，应修复日期在送修日期基础上加7天，且接收库房是制造子维修库

								PLANREPAURDATE = df.format(new Date(SENDDATE
										.getTime()
										+ ((long) 7 * 24 * 60 * 60 * 1000)));
								if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")) {
									this.getJpo().setValue("receivestoreroom",
											ItemUtil.RECEIVESTOREROOM_ZZZWXK);
								}
								if (ISSUESTOREROOM.equalsIgnoreCase("GZ1080")) {
									this.getJpo().setValue("receivestoreroom",
											"GZ1081");
								}
								if (ISSUESTOREROOM.equalsIgnoreCase("QT1080")) {
									this.getJpo().setValue("receivestoreroom",
											"QT1081");
								}
								this.getJpo().setFieldFlag("SENDBY",
										GWConstant.S_REQUIRED, false);
								this.getJpo()
										.setValue(
												"SENDBY",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("SENDBY",
										GWConstant.S_READONLY, true);
							}
							if (reparirorg
									.equalsIgnoreCase(ItemUtil.REPAIRORG_NB)) {// 修单位是宁波的，应修复日期在送修日期基础上加45天，且接收库房是宁波分所
								PLANREPAURDATE = df.format(new Date(SENDDATE
										.getTime()
										+ ((long) 45 * 24 * 60 * 60 * 1000)));
								if (ISSUESTOREROOM.equalsIgnoreCase("Y1087")) {
									this.getJpo().setValue("receivestoreroom",
											ItemUtil.RECEIVESTOREROOM_NBZWXK);
									;
								}
								if (ISSUESTOREROOM.equalsIgnoreCase("GZ1080")) {
									this.getJpo().setValue("receivestoreroom",
											"GZ1083");
								}
								if (ISSUESTOREROOM.equalsIgnoreCase("QT1080")) {
									this.getJpo().setValue("receivestoreroom",
											"QT1083");
								}
								this.getJpo().setFieldFlag("SENDBY",
										GWConstant.S_READONLY, false);
								this.getJpo()
										.setValue(
												"SENDBY",
												"",
												GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								this.getJpo().setFieldFlag("SENDBY",
										GWConstant.S_REQUIRED, true);
							}
						}
						this.getJpo().setValue("PLANREPAURDATE",
								PLANREPAURDATE,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					} else {
						throw new MroException("transferline", "nosenddate");
					}
				} else {
					throw new MroException("transferline", "nostoreroom");
				}
			}
		}
	}

}
