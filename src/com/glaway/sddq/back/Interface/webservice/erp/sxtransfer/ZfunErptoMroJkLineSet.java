package com.glaway.sddq.back.Interface.webservice.erp.sxtransfer;

import java.util.List;

/**
 * 
 * <功能描述>缴库单实例类
 * 
 * @author 20167088
 * @version [版本号, 2018-7-18]
 * @since [产品/模块版本]
 */
public class ZfunErptoMroJkLineSet {
	private List<ZfunErptoMroResponses> LINE;

	public List<ZfunErptoMroResponses> getLINE() {
		return LINE;
	}

	public void setLINE(List<ZfunErptoMroResponses> lINE) {
		LINE = lINE;
	}
}
