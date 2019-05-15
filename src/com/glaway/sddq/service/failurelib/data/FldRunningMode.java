package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 故障记录-运行模式 字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月4日]
 * @since [产品/模块版本]
 */
public class FldRunningMode extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet domainSet = null;
		// 车型
		String models = getJpo().getParent().getString("MODELS");
		if (StringUtil.isStrNotEmpty(models)) {
			// 根据车型大类过滤运行模式
			String productline = getJpo().getParent().getString(
					"MODELS.PRODUCTLINE");
			domainSet = getUserServer().getJpoSet(
					"SYS_SYNDOMAIN",
					"domainid='RUNNINGMODENEW' and INNERVALUE = '"
							+ productline + "'");
			domainSet.reset();
			return domainSet;
		} else {
			throw new AppException("repairscheme", "cmodelisnull");
		}
	}
}
