package com.glaway.sddq.tools;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * @ClassName LocationUtil
 * @Description 通过站点去查找各类库房
 * @author public2175
 * @Date 2018-8-20 上午11:25:51
 * @version 1.0.0
 */
public class LocationUtil {

	/**
	 * @Description 在检修周转件中根据站点去找到上车件所在库房范围
	 * @param itemnum
	 * @return
	 * @throws MroException
	 */
	public static String getLocationForZjSc(String locsitenum)
			throws MroException {

		if (!StringUtil.isNullOrEmpty(locsitenum)) {
			IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
					"LOCATIONS");
			locationsSet.setQueryWhere("LOCSITE='" + locsitenum
					+ "' and ERPLOC='" + ItemUtil.ERPLOC_1030
					+ "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK
					+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_CG + "'");
			locationsSet.reset();
			if (locationsSet != null && locationsSet.count() > 0) {
				return locationsSet.getJpo(0).getString("LOCATION");
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * @Description 检修周转件的下车间默认选择库房
	 * @param locsitenum
	 * @return
	 * @throws MroException
	 */
	public static String getLocationForZjXc(String locsitenum)
			throws MroException {

		if (!StringUtil.isNullOrEmpty(locsitenum)) {

			IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
					"LOCATIONS");
			locationsSet.setQueryWhere("LOCSITE='" + locsitenum
					+ "' and ERPLOC='" + ItemUtil.ERPLOC_QTDCL
					+ "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK
					+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_CG
					+ "' and JXORFW='" + ItemUtil.JXORFW_JX + "'");
			locationsSet.reset();
			if (locationsSet != null && locationsSet.count() > 0) {
				return locationsSet.getJpo(0).getString("LOCATION");
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * @Description 偶换件上车件的默认选择库房
	 * @param locsitenum
	 * @return
	 * @throws MroException
	 */
	public static String getLocationForOhSc(String locsitenum,
			Boolean iscustitem) throws MroException {
		if (!StringUtil.isNullOrEmpty(locsitenum)) {
			// 判断是否选择客户料
			if (iscustitem != false) {
				IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS");
				locationsSet.setQueryWhere("LOCSITE='" + locsitenum
						+ "' and ERPLOC='" + ItemUtil.ERPLOC_QTDCL
						+ "' and  STOREROOMLEVEL in('"
						+ ItemUtil.STOREROOMLEVEL_XCZDK + "','"
						+ ItemUtil.STOREROOMLEVEL_XCK + "') and locationtype='"
						+ ItemUtil.LOCATIONTYPE_WX + "' and jxorfw='"
						+ ItemUtil.JXORFW_JX + "'");
				locationsSet.reset();
				if (locationsSet != null && locationsSet.count() > 0) {
					return locationsSet.getJpo(0).getString("LOCATION");
				} else {
					return "";
				}
			} else {
				IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS");
				locationsSet.setQueryWhere("LOCSITE='" + locsitenum
						+ "' and ERPLOC='" + ItemUtil.ERPLOC_1020
						+ "' and STOREROOMLEVEL='"
						+ ItemUtil.STOREROOMLEVEL_XCZDK
						+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_CG
						+ "' and JXORFW='" + ItemUtil.JXORFW_JX + "'");
				locationsSet.reset();
				if (locationsSet != null && locationsSet.count() > 0) {
					return locationsSet.getJpo(0).getString("LOCATION");
				} else {
					return "";
				}
			}
		} else {
			return "";
		}
	}


	/**
	 * @Description 偶换件下车件的默认选择库房
	 * @param locsitenum
	 * @return
	 * @throws MroException
	 */
	public static String getLocationForOhXc(String locsitenum,Boolean iscustitem)
			throws MroException {
		if (!StringUtil.isNullOrEmpty(locsitenum)) {
			// 判断是否选择客户料
			if (iscustitem != false) {
				IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS");
				locationsSet.setQueryWhere("LOCSITE='" + locsitenum
						+ "' and ERPLOC='" + ItemUtil.ERPLOC_QTDCL
						+ "' and  STOREROOMLEVEL in('"
						+ ItemUtil.STOREROOMLEVEL_XCZDK + "','"
						+ ItemUtil.STOREROOMLEVEL_XCK + "') and locationtype='"
						+ ItemUtil.LOCATIONTYPE_WX + "' and jxorfw='"
						+ ItemUtil.JXORFW_JX + "'");
				locationsSet.reset();
				if (locationsSet != null && locationsSet.count() > 0) {
					return locationsSet.getJpo(0).getString("LOCATION");
				} else {
					return "";
				}
			} else {
				IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
						"LOCATIONS");
				locationsSet.setQueryWhere("LOCSITE='" + locsitenum
						+ "' and ERPLOC='" + ItemUtil.ERPLOC_1020
						+ "' and STOREROOMLEVEL='"
						+ ItemUtil.STOREROOMLEVEL_XCZDK
						+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_WX
						+ "' and JXORFW='" + ItemUtil.JXORFW_JX + "'");
				locationsSet.reset();
				if (locationsSet != null && locationsSet.count() > 0) {
					return locationsSet.getJpo(0).getString("LOCATION");
				} else {
					return "";
				}
			}
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 耗损件下车件默认选择库房
	 * 
	 * @param locsitenum
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static String getLocationForHsXc(String locsitenum)
			throws MroException {

		if (!StringUtil.isNullOrEmpty(locsitenum)) {
			IJpoSet locationsSet = MroServer.getMroServer().getSysJpoSet(
					"LOCATIONS");
			locationsSet.setQueryWhere("LOCSITE='" + locsitenum
					+ "' and ERPLOC='" + ItemUtil.ERPLOC_QTDCL
					+ "' and STOREROOMLEVEL='" + ItemUtil.STOREROOMLEVEL_XCK
					+ "' and locationtype='" + ItemUtil.LOCATIONTYPE_CG
					+ "' and JXORFW='" + ItemUtil.JXORFW_JX + "'");
			locationsSet.reset();
			if (locationsSet != null && locationsSet.count() > 0) {
				return locationsSet.getJpo(0).getString("LOCATION");
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

}
