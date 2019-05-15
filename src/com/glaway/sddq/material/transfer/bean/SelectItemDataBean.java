package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <改造装箱单选择物料弹出框Databean>
 * 
 * @author 20167088
 * @version [版本号, 2018-7-20]
 * @since [产品/模块版本]
 */
public class SelectItemDataBean extends DataBean {// com.glaway.sddq.material.transfer.bean.SelectItemDataBean
	/**
	 * 初始化物料数据
	 * 
	 * @throws MroException
	 */
	@Override
	public void initialize() throws MroException {
		String ISSUESTOREROOM = this.page.getAppBean().getJpo()
				.getString("ISSUESTOREROOM");
		String where = "";
		IJpoSet transferlineset = this.getAppBean().getJpo()
				.getJpoSet("transferline");
		String itemnum = null;

		for (int j = 0; j < transferlineset.count(); j++) {
			IJpo transferline = transferlineset.getJpo(j);

			if (itemnum == null) {
				itemnum = "'" + transferline.getString("ITEMNUM") + "'";
			} else {
				itemnum = itemnum + ",'" + transferline.getString("ITEMNUM")
						+ "'";
			}
		}

		if (itemnum != null) {
			where = " itemnum not in(" + itemnum + ")";
		}
		if (!StringUtil.isStrEmpty(ISSUESTOREROOM)) {
			if (!StringUtil.isStrEmpty(where)) {
				where = where + " and  location='" + ISSUESTOREROOM + "'";
			} else {
				where = " location='" + ISSUESTOREROOM + "'";
			}

		}
		if (!StringUtil.isStrEmpty(where)) {
			this.getJpoSet().setUserWhere(where);
		}
		super.initialize();
	}

	/**
	 * 赋值选择的信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws MroException
	 */
	@Override
	public int dialogok() throws IOException, MroException {
		if (this.getAppBean().getJpo().getString("status").equals("在途")
				|| this.getAppBean().getJpo().getString("status").equals("已接收")) {
			throw new MroException("提示", "在途/已接收状态无法新增行");
		} else {
			List<IJpo> vec = getJpoSet().getSelections();
			String TRANSFERNUM = this.getAppBean().getJpo()
					.getString("TRANSFERNUM");
			String ISSUESTOREROOM = this.getAppBean().getJpo()
					.getString("ISSUESTOREROOM");
			String RECEIVESTOREROOM = this.getAppBean().getJpo()
					.getString("RECEIVESTOREROOM");
			if (vec.size() > 0) {
				for (int i = 0; i < vec.size(); i++) {
					IJpo jpo = vec.get(i);
					IJpoSet jposet = this.getAppBean().getJpo()
							.getJpoSet("TRANSFERLINE");
					IJpo mobileauc = jposet.addJpo();
					mobileauc.setValue("ITEMNUM", jpo.getString("ITEMNUM"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mobileauc.setValue("TRANSFERNUM", TRANSFERNUM);
					mobileauc.setValue("ISSUESTOREROOM", ISSUESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mobileauc.setValue("RECEIVESTOREROOM", RECEIVESTOREROOM,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			return super.dialogok();
		}
	}
}
