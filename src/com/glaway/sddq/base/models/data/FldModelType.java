package com.glaway.sddq.base.models.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 车型类别字段类
 * 
 * @author zzx
 * @version GlawayMro4.0, 2018-11-6
 * @since GlawayMro4.0/系统
 */
public class FldModelType extends JpoField {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet productlineSet = MroServer.getMroServer()
				.getSysJpoSet("SYS_ALNDOMAIN");
		String productline = this.getJpo().getString("PRODUCTLINE");// 获取车型大类
		if (!productline.isEmpty()) {
			if (productline.equals("机车")) {
				// IJpoSet modelpoSet = this.getJpo().getJpoSet();

				productlineSet
						.setUserWhere(" DOMAINID = 'MODELTYPENEW' and VALUE in ('JA','JB','JC','JD','JE')");
				productlineSet.reset();
			} else if (productline.equals("动车")) {
				productlineSet
						.setUserWhere(" DOMAINID = 'MODELTYPENEW' and VALUE in ('DA','DB','DC')");
				productlineSet.reset();
			} else if (productline.equals("城轨")) {
				productlineSet
						.setUserWhere(" DOMAINID = 'MODELTYPENEW' and VALUE in ('CA','CB','CC','CD','CE')");
				productlineSet.reset();
			}
		} else {
			productlineSet.setUserWhere("DOMAINID = 'MODELTYPENEW'");

		}
		return productlineSet;
	}
}
