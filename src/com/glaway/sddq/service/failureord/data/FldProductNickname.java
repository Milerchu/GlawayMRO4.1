package com.glaway.sddq.service.failureord.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 故障工单-故障记录-故障设备简称字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月2日]
 * @since [产品/模块版本]
 */
public class FldProductNickname extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1622367884202107274L;

	@Override
	public IJpoSet getList() throws MroException {
		// 根据车型大类过滤设备简称
		IJpoSet domainSet = null;
		// 车型编号
		IJpo parent = getJpo().getParent();
		if (parent != null) {
			String cmodel = parent.getString("MODELS");
			if (!StringUtil.isNullOrEmpty(cmodel)) {

				// 车型大类
				String productline = this.getJpo().getString(
						"WORKORDER.MODELS.PRODUCTLINE");
				domainSet = getUserServer().getJpoSet(
						"SYS_SYNDOMAIN",
						"domainid='FAILPRODABBR' and INNERVALUE = '"
								+ productline + "'");
				domainSet.reset();
				return domainSet;
			} else {
				throw new MroException("车型数据不正确，请检查！");
			}
		}
		return domainSet;
	}
}
