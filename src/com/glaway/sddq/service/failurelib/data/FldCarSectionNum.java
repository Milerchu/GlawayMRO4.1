package com.glaway.sddq.service.failurelib.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 车厢字段类
 * 
 * @author chenbin
 * @version [版本号, 2018-7-24]
 * @since [产品/模块版本]
 */
public class FldCarSectionNum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "CARSECTIONNUM" },
				new String[] { "CARRIAGENUM" });
	}

	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		// 整车assetnum
		String assetnum = this.getJpo().getParent().getString("assetnum");

		if (StringUtil.isNullOrEmpty(assetnum)) {
			throw new AppException("workorder", "notcarno");
		}

		// 过滤当前车辆的车厢号
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
				"assetnum='" + assetnum + "' ");
		if (jposet != null && jposet.count() > 0) {
			String ancestor = jposet.getJpo(0).getString("ANCESTOR");
			this.setListWhere("ancestor='" + ancestor
					+ "' and assetlevel='CAR'");
		} else {
			this.setListWhere("1=2");
		}
		return super.getList();
	}
}
