package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 产品序列号字段类
 * 
 * @author hyhe
 * @version [版本号, 2017-11-16]
 * @since [产品/模块版本]
 */
public class FldSqn extends JpoField {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "ASSETNUM", "SQN", "ITEMNUM" },
				new String[] { "ASSETNUM", "SQN", "ITEMNUM" });
	}

	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		IJpo jpo = this.getJpo();
		String ancestor = jpo.getString("ANCESTOR");
		String assetnum = "";
		IJpoSet singleoverhaulSet = getJpo().getJpoSet();
		if (singleoverhaulSet != null && singleoverhaulSet.count() > 0) {
			for (int i = 0; i < singleoverhaulSet.count(); i++) {
				if (singleoverhaulSet.getJpo(i) != getJpo()) {
					String assetnums = singleoverhaulSet.getJpo(i).getString(
							"assetnum");
					assetnum += "'" + assetnums + "',";
				}

			}
			if (StringUtil.isStrNotEmpty(assetnum)) {
				assetnum = assetnum.substring(0, assetnum.length() - 1);
			}
		}
		String sql = "ancestor='" + StringUtil.getSafeSqlStr(ancestor)
				+ "' and assetnum !='" + ancestor
				+ "' and assetlevel='SYSTEM' and type = '2'";
		if (StringUtil.isStrNotEmpty(assetnum)) {
			sql += " and assetnum not in(" + assetnum + ")";
		}
		this.setListWhere(sql);

		return super.getList();
	}

}
