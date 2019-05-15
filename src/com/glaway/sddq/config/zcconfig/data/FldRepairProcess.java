package com.glaway.sddq.config.zcconfig.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 修程字段类
 * 
 * @author hyhe
 * @version [版本号, 2018-4-24]
 * @since [产品/模块版本]
 */
public class FldRepairProcess extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		
		IJpoSet domainSet = null;
		String cmodel = this.getJpo().getString("CMODEL");
		if (!StringUtil.isNullOrEmpty(cmodel)) {
			String productline = this.getJpo().getString("MODELS.PRODUCTLINE");
			domainSet = getUserServer().getJpoSet("SYS_SYNDOMAIN", "domainid='NEWREPAIRPROCESS' and INNERVALUE = '"+productline+"'");
			domainSet.setOrderBy("SEQNO");
			domainSet.reset();
			return domainSet;
		}else{
            throw new AppException("repairscheme", "cmodelisnull");
		}
	}
}
