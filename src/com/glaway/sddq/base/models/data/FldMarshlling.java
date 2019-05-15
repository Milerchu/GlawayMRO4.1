package com.glaway.sddq.base.models.data;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 轴式/列车编组字段类
 * 
 * @author zzx
 * @version GlawayMro4.0, 2018-11-6
 * @since GlawayMro4.0/系统
 */
public class FldMarshlling extends JpoField {
	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IJpoSet getList() throws MroException {

		IJpoSet productlineSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_ALNDOMAIN");
		String productline = this.getJpo().getString("PRODUCTLINE");// 获取车型大类
		if (!productline.isEmpty()) {
			if (productline.equals("机车")) {
				// IJpoSet modelpoSet = this.getJpo().getJpoSet();

				productlineSet
						.setUserWhere(" DOMAINID = 'MARSHALLING' and VALUE in ('B0-B0','C0-C0','2（B0-B0）','3（B0-B0）','B0-B0-B0') and description='机车'");
				productlineSet.reset();
			} else if (productline.equals("动车")) {
				productlineSet
						.setUserWhere(" DOMAINID = 'MARSHALLING' and VALUE in ('4M4T','6M2T','14M2T','8M8T','2M1T','2M2T') and description='动车'");
				productlineSet.reset();
			} else if (productline.equals("城轨")) {
				productlineSet
						.setUserWhere(" DOMAINID = 'MARSHALLING' and VALUE in ('4M2T','3M3T','6M0T','6M2T','3M2T','4M0T','2M2T','2M1T','3M0T','B0-B0') and description='城轨'");
				productlineSet.reset();
			}
		} else {
			productlineSet.setUserWhere("DOMAINID = 'MARSHALLING'");
		}

		return productlineSet;
	}
}
