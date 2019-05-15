package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 故障工单-故障品处置方式字段类
 * 
 * @author zhuhao
 * @version [版本号, 2018年12月14日]
 * @since [产品/模块版本]
 */
public class FldFaultcompdealmode extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		// 根据处理方式更换选项
		IJpoSet domainSet = null;
		String dealmethod = getJpo().getString("FAILURELIB.DEALMETHOD");
		String innervalue = "";
		if (StringUtil.isStrNotEmpty(dealmethod)) {
			if (SddqConstant.FAIL_DEALMETHOD_HJCL.equals(dealmethod)
					|| SddqConstant.FAIL_DEALMETHOD_YJXF.equals(dealmethod)) {
				innervalue = dealmethod;
			} else {
				innervalue = "其他";
			}

		} else {
			innervalue = "其他";
		}

		// 返回故障品处置方式同义词域
		domainSet = getUserServer().getJpoSet(
				"SYS_SYNDOMAIN",
				"domainid='FAULTDEALMODE' and INNERVALUE = '" + innervalue
						+ "'");
		domainSet.reset();
		return domainSet;
	}

}
