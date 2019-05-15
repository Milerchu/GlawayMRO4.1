package com.glaway.sddq.tools;

import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.h2.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 物料编码工具类
 * 
 * @author public2175
 * @version [版本号, 2018-6-20]
 * @since [产品/模块版本]
 */
public class ItemUtil {

	// 配件申请状态-申请人修改
	public final static String STATUS_SQRXG = "申请人修改";

	// 配件申请状态-申请人修改
	public final static String STATUS_FWZGSP = "服务主管审批";

	// 配件申请状态-申请人修改
	public final static String STATUS_JHJLSP = "计划经理审批";

	// 配件申请状态-申请人修改
	public final static String STATUS_CPXJLSP = "产品线经理审批";

	// 配件申请状态-申请人修改
	public final static String STATUS_PJZYZX = "配件专员执行";

	// 配件申请状态-申请人修改
	public final static String STATUS_JHJLXG = "计划经理修改";

	// 配件申请状态-申请人修改
	public final static String STATUS_JHZGZZPS = "计划主管组织评审";

	// 配件申请处置方式-内部协调
	public final static String TRANSTYPE_NBXT = "内部协调";

	// 配件申请处置方式-计划经理协调
	public final static String TRANSTYPE_JHJLXT = "计划经理协调";

	// 配件申请处置方式-下达计划
	public final static String TRANSTYPE_XDJH = "下达计划";

	// 配件申请处置方式-下达计划
	public final static String TRANSTYPE_ZXKDB = "中心库调拨";

	// 配件申请处置方式-返修后发运
	public final static String TRANSTYPE_FXHFY = "返修后发运";

	// 配件申请处置方式-现场调拨
	public final static String TRANSTYPE_XCDB = "现场调拨";

	// 送修单承修单位-制造
	public final static String REPAIRORG_ZZ = "13762560000048";

	// 送修单承修单位-集采
	public final static String REPAIRORG_JC = "13762560000071";

	// 送修单承修单位-宁波
	public final static String REPAIRORG_NB = "13762560000168";

	// 送修单送修库房-中心维修库
	public final static String ISSUESTOREROOM_ZXWXK = "Y1087";

	// 送修单接收库房-制造子维修库
	public final static String RECEIVESTOREROOM_ZZZWXK = "Y1088";

	// 送修单接收库房-物流子维修库
	public final static String RECEIVESTOREROOM_WLZWXK = "Y1089";

	// 送修单接收库房-宁波维修库
	public final static String RECEIVESTOREROOM_NBZWXK = "Y1090";

	// 装箱单移动类型-中心到现场
	public final static String TRANSFERMOVETYPE_ZTOX = "中心到现场";

	// 装箱单移动类型-现场到中心
	public final static String TRANSFERMOVETYPE_XTOZ = "现场到中心";

	// 装箱单移动类型-现场到现场
	public final static String TRANSFERMOVETYPE_XTOX = "现场到现场";

	// 装箱单移动类型-现场到现场
	public final static String TRANSFERMOVETYPE_ZTOZ = "中心到中心";

	// 不存在该物料
	public final static String NO_ITEM = "0";

	// 物料属于序列号追溯件(周转件)
	public final static String SQN_ITEM = "周转件";

	// I类耗损件(准周转件)
	public final static String LOT_I_ITEM = "准周转件";

	// II类耗损件(耗损件)
	public final static String LOT_II_ITEM = "耗损件";

	// 库房属性-1020
	public final static String ERPLOC_1020 = "1020";

	// 库房属性-1030
	public final static String ERPLOC_1030 = "1030";

	// 库房属性-其他待处理物资库
	public final static String ERPLOC_QTDCL = "其他待处理库";

	// 库房属性-其他改造物料库
	public final static String ERPLOC_QTGZ = "改造物料库";

	// 库房级别-中心库
	public final static String STOREROOMLEVEL_ZXK = "中心库";

	// 库房级别-现场库
	public final static String STOREROOMLEVEL_XCK = "现场库";

	// 库房级别-现场库
	public final static String STOREROOMLEVEL_FWK = "服务库";
	// 库房级别-区域库
	public final static String STOREROOMLEVEL_QYK = "区域库";

	// 库房级别-现场站点库
	public final static String STOREROOMLEVEL_XCZDK = "现场站点库";

	// 库房级别-待维修库
	public final static String STOREROOMLEVEL_DWXK = "待维修库";

	// 库房级别-物流库
	public final static String STOREROOMLEVEL_WLK = "物流库";

	// 库房类型-待处理
	public final static String LOCATIONTYPE_DCL = "待处理";

	// 库房类型-维修
	public final static String LOCATIONTYPE_WX = "维修";

	// 库房类型-常规
	public final static String LOCATIONTYPE_CG = "常规";

	// 库房类型-机车检修
	public final static String LOCATIONTYPE_JCJX = "机车检修";

	// 库房类型-动车检修
	public final static String LOCATIONTYPE_DCJX = "动车检修";

	// 库房类型-城轨检修
	public final static String LOCATIONTYPE_CGJX = "城轨检修";

	// 库房类型-新服务备品
	public final static String LOCATIONTYPE_XFWBP = "新服务备品";

	public final static String JXORFW_JX = "检修";

	public final static String JXORFW_FW = "服务";

	/**
	 * 获取该物料的追溯信息
	 * 
	 * @param item
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public static String getItemInfo(String itemnum) throws MroException {

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("SYS_ITEM",
				"itemnum='" + itemnum + "'");
		if (jposet.count() > 0) {
			IJpo jpo = jposet.getJpo();
			if (jpo.getBoolean("ISTURNOVERERP")) {
				return SQN_ITEM;
			} else {
				if (jpo.getBoolean("ISLOTERP")) {
					return LOT_I_ITEM;
				} else {
					return LOT_II_ITEM;
				}
			}
		} else {
			return NO_ITEM;
		}
	}

	public static String getItemLottype(String itemnum) throws MroException {

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("SYS_ITEM",
				"itemnum='" + itemnum + "'");
		if (jposet.count() > 0) {
			IJpo jpo = jposet.getJpo(0);
			return jpo.getString("LOTTYPE");
		} else {
			return NO_ITEM;
		}
	}

	/**
	 * 通过序列号到ASSET表中查看数据是否已经存在
	 * 
	 * @param sqn
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public static boolean checkSqnIsExist(String sqn) throws MroException {

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
				"sqn='" + sqn + "'");
		if (jposet != null && jposet.count() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 校验在MRO系统中是否存在该ITEMNUM的产品SBOM
	 * 
	 * @param itemnum
	 * @return [参数说明]
	 * @throws MroException
	 */
	public static boolean isExistItemNum(String itemnum) throws MroException {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP",
				"assettmplevel = 'ASSET' and itemnum='" + itemnum + "'");
		if (jposet.count() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否已经存在物料替换关系
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public static boolean isExistAltItem(JSONObject jsonObject)
			throws MroException {

		String itemnum = jsonObject.getString("itemnum");
		// String replace = jsonObject.getString("replace");
		String parent = jsonObject.getString("parent");
		// String priority = jsonObject.getString("priority");

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("sys_altitem",
				"altitemnum ='" + itemnum + "' and parent='" + parent + "'");

		if (jposet.count() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @Description 获取物料的信息
	 * @param itemnum
	 * @return
	 * @throws MroException
	 */
	public static IJpo getItem(String itemnum) throws MroException {

		if (!StringUtil.isNullOrEmpty(itemnum)) {
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("SYS_ITEM",
					"itemnum='" + itemnum + "'");
			if (jposet != null && jposet.count() > 0) {
				return jposet.getJpo(0);
			}
		}
		return null;
	}

	/**
	 * @Description 获取物料的可替换物料拼接字符串
	 * @param itemnum
	 * @return
	 * @throws MroException
	 */
	public static String getAltItemnums(String itemnum) throws MroException {
		String itemnums = "'" + itemnum + "',";
		// 物料表
		IJpoSet itemSet = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemSet.setQueryWhere("itemnum='" + itemnum + "'");
		itemSet.reset();
		if (!itemSet.isEmpty()) {
			// 可替换物料
			IJpoSet altitemSet = itemSet.getJpo(0).getJpoSet("ALTITEM");
			if (altitemSet != null && altitemSet.count() > 0) {

				for (int index = 0; index < altitemSet.count(); index++) {
					IJpo altitem = altitemSet.getJpo(index);
					itemnums += "'" + altitem.getString("ALTITEMNUM") + "',";
				}
			}
		}
		itemnums = itemnums.substring(0, itemnums.length() - 1);
		return itemnums;
	}

	/**
	 * 
	 * 获取替代物料的集合list
	 * 
	 * @param itemnum
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static List<String> getAltItemnumsList(String itemnum)
			throws MroException {
		List<String> itemnums = new ArrayList<String>();
		itemnums.add(itemnum);
		// 物料表
		IJpoSet itemSet = MroServer.getMroServer().getJpoSet("sys_item",
				MroServer.getMroServer().getSystemUserServer());
		itemSet.setQueryWhere("itemnum='" + itemnum + "'");
		itemSet.reset();
		if (!itemSet.isEmpty()) {
			// 可替换物料
			IJpoSet altitemSet = itemSet.getJpo(0).getJpoSet("ALTITEM");
			if (altitemSet != null && altitemSet.count() > 0) {

				for (int index = 0; index < altitemSet.count(); index++) {
					IJpo altitem = altitemSet.getJpo(index);
					itemnums.add(altitem.getString("ALTITEMNUM"));
				}
			}
		}
		return itemnums;
	}

	/**
	 * @Description 获取物料的可替换物料数组
	 * @param itemnum
	 * @return
	 * @throws MroException
	 */
	public static String[] getAltItemnumsAry(String itemnum)
			throws MroException {

		List<String> list = getAltItemnumsList(itemnum);
		return list.toArray(new String[list.size()]);

	}

	/**
	 * 
	 * 获取产品序列号的数据是否存在
	 * 
	 * @param sqn
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static boolean getAssetInfo(String sqn, String itemnum)
			throws MroException {

		boolean flag = false;
		if (!StringUtil.isNullOrEmpty(sqn)) {
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET");
			if (!StringUtil.isNullOrEmpty(itemnum)) {
				jposet.setQueryWhere("sqn='" + sqn + "' and itemnum='"
						+ itemnum + "'");
				jposet.reset();
			} else {
				jposet.setQueryWhere("sqn='" + sqn + "'");
				jposet.reset();
			}
			if (jposet.count() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 
	 * 处理400的物料编码
	 * 
	 * @param itemnum
	 * @return [参数说明]
	 * 
	 */
	public static String getItemnumFor400(String itemnum) {
		if (!StringUtils.isNullOrEmpty(itemnum)) {
			if (itemnum.startsWith("0")) {
				if (itemnum.indexOf("4") > 0) {
					itemnum = itemnum.substring(itemnum.indexOf("4"));
				} else {
					if (itemnum.length() > 8) {
						itemnum = itemnum.substring(8);
					}
				}
			}
		}
		return itemnum;
	}

	/**
	 * 
	 * 获取指定物料指定库房的可用数量
	 * 
	 * @param itemnum
	 * @param location
	 * @return [参数说明]
	 * @throws MroException
	 * 
	 */
	public static double getKyQty(IJpo jpo, String itemnum, String location)
			throws MroException {

		double kyQty = 0;
		double FCZTQTY = 0;
		IJpoSet transferlineset = MroServer.getMroServer().getJpoSet(
				"transferline", MroServer.getMroServer().getSystemUserServer());
		transferlineset
				.setQueryWhere("itemnum='"
						+ itemnum
						+ "' and ISSUESTOREROOM='"
						+ location
						+ "'  and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
		transferlineset.reset();
		if (transferlineset.count() > 0) {
			FCZTQTY = transferlineset.sum("ORDERQTY");
		}
		double curbal = jpo.getDouble("curbal");// 库存总数
		double sqzyqty = jpo.getDouble("sqzyqty");// 申请占用数量
		double DISPOSEQTY = jpo.getDouble("DISPOSEQTY");// 待处理数量
		double FAULTQTY = jpo.getDouble("FAULTQTY");// 故障数量
		double TESTINGQTY = jpo.getDouble("TESTINGQTY");// 待检测数量

		kyQty = curbal - FCZTQTY - sqzyqty - DISPOSEQTY - FAULTQTY - TESTINGQTY;

		return kyQty;

	}

	/**
	 * 
	 * 获取物料的默认技术参数
	 * 
	 * @param itemnum
	 * @return [参数说明]
	 * @throws MroException
	 * 
	 */
	public static IJpoSet getDefaultJscs(String itemnum) throws MroException {

		if (!StringUtils.isNullOrEmpty(itemnum)) {
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("JSCS",
					"itemnum='" + itemnum + "' and type='1'");
			if (jposet != null && jposet.count() > 0) {
				return jposet;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 
	 * 设置默认的技术参数
	 * 
	 * @param itemnum
	 * @param assetnum
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void setDefaultJscs(String itemnum, String assetnum)
			throws MroException {
		IJpoSet jposet = getDefaultJscs(itemnum);
		if (jposet != null) {
			IJpoSet jscsJposet = MroServer.getMroServer().getSysJpoSet("JSCS",
					"1=2");
			String[] attrs = { "assetnum", "type" };
			String[] vals = { assetnum, "2" };
			jscsJposet.duplicate(jposet, GWConstant.P_NOVALIDATION);
			jscsJposet.updateAll(attrs, vals, GWConstant.P_NOVALIDATION);
			jscsJposet.save();
		}
	}
}
