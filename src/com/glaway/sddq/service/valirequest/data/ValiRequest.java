package com.glaway.sddq.service.valirequest.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <验证申请单jpo类>
 * 
 * @author hzhu
 * @version [MRO4.1, 2018-5-27]
 * @since [MRO4.1/模块版本]
 */
public class ValiRequest extends Jpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -7543275527222534854L;

	@Override
	public void init() throws MroException {
		// 状态
		String status = getString("status");
		// 基本信息字段
		String[] fields = { "VALIREQUESTNUM", "APPPERSON", "APPDEPT",
				"VALIREASON", "SOFTLINK", "INSTALLPAGE", "INSTALLMD5",
				"BACKLOGSOFT", "VALISOFT", "TRANSTYPE", "VALIDATETIME",
				"VALICONTEXT", "VALIATTENTION", "PLANTOUSE",
				"PERVERISONSTATUS", "PERVERISONMEMO", "SENIGNEER", "CONFIRMBY",
				"CONFIRMDATE", "CONFIRMOPINION", "SPECIALREQ", "WORKORDERNUM",
				"EMERGENTSTEP", "SNUMBER", "PROJECTNUM" };
		// 验证内容
		IJpoSet transcontent = this.getJpoSet("VALICONTENT");
		// 验证产品范围
		IJpoSet transrange = this.getJpoSet("VALIPRORANGE");

		// 非新建状态编号不能更改
		if (!isNew()) {
			setFieldFlag("VALIREQUESTNUM", GWConstant.S_READONLY, true);
		}

		if (!"草稿".equals(status)) {
			setFieldFlag(fields, GWConstant.S_READONLY, true);
			transcontent.setFlag(GWConstant.S_READONLY, true);
			transrange.setFlag(GWConstant.S_READONLY, true);

			if ("待审核".equals(status) || "审核中".equals(status)) {
				transcontent.setFlag(GWConstant.S_READONLY, false);
				transrange.setFlag(GWConstant.S_READONLY, false);
			}

		}
	}
}
