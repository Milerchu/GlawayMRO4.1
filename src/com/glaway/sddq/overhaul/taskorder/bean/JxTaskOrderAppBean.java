package com.glaway.sddq.overhaul.taskorder.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.back.Interface.webservice.erp.service.erptomrotestdevesb.gggz.JxWorkOrderCommon;
import com.glaway.sddq.material.invtrans.common.CommonInventory;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 检修工单AppBean
 * 
 * @author hyhe
 * @version [版本号, 2018-1-24]
 * @since [产品/模块版本]
 */
public class JxTaskOrderAppBean extends AppBean {

	/**
	 * 检修派工
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */
	public void DISPATCH() throws MroException, IOException {
		// IJpoSet jset = this.getAppBean().getJpo().getJpoSet("JXTASKITEM");
		// boolean flag = false;
		// for (int i = 0; i < jset.count(); i++)
		// {
		// IJpo jpo = jset.getJpo(i);
		// String liableperson = jpo.getString("LIABLEPERSON");
		// if (StringUtil.isStrEmpty(liableperson))
		// {
		// flag = true;
		// break;
		// }
		// }
		//
		// if (flag)
		// {
		// throw new AppException("jxtaskitem", "dispatch");
		// }
		// else
		// {
		// this.getAppBean().getJpo().setValue("STATUS", "已派工",
		// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		// this.getAppBean().SAVE();
		// }
		// 检修派工前，先完善 序列号信息，根据数量和填写的产品序列号去校验，并在产品列表中需关联存储ancestor以及assetnum字段
		this.getAppBean().SAVE();
		IJpoSet productJposet = this.getJpo().getJpoSet("JXPRODUCT");
		String carno = this.getJpo().getString("CARNO");
		String cmodel = this.getJpo().getString("CMODEL");
		String assetnum = this.getJpo().getString("assetnum");
		boolean nullFlag = false;
		boolean existFlag = false;
		String nosqn = "";
		if (SddqConstant.JX_STATUS_KG.equals(this.getJpo().getString("STATUS"))) {
			throw new AppException("jxtaskorder", "haskg");
		}
		if (SddqConstant.JX_STATUS_WC.equals(this.getJpo().getString("STATUS"))) {
			throw new AppException("jxtaskorder", "haswg");
		}
		if (productJposet != null && productJposet.count() > 0) {

			for (int index = 0; index < productJposet.count(); index++) {
				String sqn = productJposet.getJpo(index).getString("SQN");
				if (StringUtil.isStrEmpty(sqn)) {
					nullFlag = true;
					break;
				}
				if (!isExistSqn(sqn, assetnum, cmodel, carno)) {
					existFlag = true;
					nosqn = sqn;
					break;
				}
			}

			if (nullFlag) {
				throw new AppException("jxtaskorder", "sqnisnull");
			}

			if (existFlag) {
				throw new AppException("jxtaskorder", "sqnisnotcar",
						new String[] { nosqn });
			}

			// 对产品进行自动下车
			getOffFromCar(productJposet, assetnum);

		} else {
			// 请填写序列号信息
			throw new AppException("jxtaskorder", "addsqn");
		}

		this.getAppBean()
				.getJpo()
				.setValue("STATUS", SddqConstant.JX_STATUS_KG,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		String REPAIRPROCESS=this.getJpo().getString("REPAIRPROCESS");
//		Repairprocesscommon.CHANGESTATUS(assetnum, REPAIRPROCESS);//修程写入车公共方法
		this.getAppBean().SAVE();
	}

	/**
	 * 对产品进行下车
	 * 
	 * @throws MroException
	 */
	public void getOffFromCar(IJpoSet jposet, String ancestor)
			throws MroException {

		for (int i = 0; i < jposet.count(); i++) {

			IJpo jpo = jposet.getJpo(i);
			String sqn = jpo.getString("sqn");
			String assetnum = jpo.getString("assetnum");

			// 获取ASSET表中待拆卸的选中部件以及子部件
			IJpoSet removeMboset = MroServer.getMroServer().getSysJpoSet(
					"ASSET");
			removeMboset
					.setUserWhere("ANCESTOR='"
							+ ancestor
							+ "' and assetnum in (select assetnum from asset  start with assetnum ='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)");
			removeMboset.reset();

			// 保留结构，更新Asset表的parent以及ancestor
			if(removeMboset.isEmpty()){
				//无法查询到部件以及子部件
				throw new AppException("jxtaskorder", "nojxproduct");
			}else if(!removeMboset.isEmpty()) {
				for (int index = 0; index < removeMboset.count(); index++) {
					if (removeMboset.getJpo(index).getString("ASSETNUM")
							.equals(assetnum)) {
						removeMboset.getJpo(index).setValue("parent", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						removeMboset.getJpo(index).setValue("ASSETLEVEL",
								"ASSET",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					removeMboset.getJpo(index).setValue("ANCESTOR", assetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("LOCATION", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 对检修的柜子进行下车
					removeMboset.getJpo(index).setValue("TYPE", "3",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("DOWNTYPE",
							SddqConstant.DOWN_JX,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("STATUS",
							SddqConstant.SXC_JX,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				removeMboset.save();

				// 更新assettree结构(删除所有拆除节点及其子节点与祖先节点的节点关系)
				// IJpoSet removeTreeMboset = MroServer.getMroServer()
				// .getSysJpoSet("assettree");
				// removeTreeMboset.setUserWhere("ANCESTOR like '" + ancestor
				// + "%'  and assetnum	like '" + assetnum
				// + "%'  and  ANCESTOR not like '" + assetnum + "%'");
				// removeTreeMboset.reset();
				//
				// removeTreeMboset.deleteAll();
				// removeTreeMboset.save();
			}
		}
	}

	/**
	 * 判断该序列号是否存在
	 * 
	 * @param sqn
	 * @param assetnum
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public boolean isExistSqn(String sqn, String assetnum, String cmodel,
			String carno) throws MroException {

		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
				"ancestor='" + assetnum + "' and sqn ='" + sqn + "'");
		if (jposet != null && jposet.count() > 0) {
			return true;
		} else {
			IJpoSet assetset = MroServer.getMroServer().getSysJpoSet("ASSET",
					"cmodel='" + cmodel + "' and carno ='" + carno + "'");
			if (assetset != null && assetset.count() > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 确认完成
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */
	public void NOTARIZE() throws MroException, IOException {
		String status = this.getString("STATUS");
		if (status != null && status.equals(SddqConstant.JX_STATUS_CG)) {
			throw new AppException("jxtaskorder", "dispatch");
		}
		if (SddqConstant.JX_STATUS_WC.equals(this.getJpo().getString("STATUS"))) {
			throw new AppException("jxtaskorder", "haswg");
		}
		if (status != null) {

			IJpoSet jxproductsSet = this.getJpo().getJpoSet("JXPRODUCT");
			for (int ip = 0; ip < jxproductsSet.count(); ip++) {
				IJpo jxproducts = jxproductsSet.getJpo(ip);
				IJpoSet jxtaskitemSet = jxproducts.getJpoSet("JXTASKITEM");
				for (int jp = 0; jp < jxtaskitemSet.count(); jp++) {
					IJpo jxtaskitem = jxtaskitemSet.getJpo(jp);
					String jxstatus = jxtaskitem.getString("STATUS");
					if (SddqConstant.JX_GX_STATUS_NO.equals(jxstatus)||SddqConstant.JX_GX_STATUS_ERROR.equals(jxstatus)) {
						throw new AppException("jxtaskorder",
								"nonewspaperworker");
					}
				}
			}
			String cmodel = this.getString("CMODEL");// 车型
			String carno = this.getString("carno");// 车号
			String repairprocess = this.getString("REPAIRPROCESS");
			String jxtasknum = this.getString("JXTASKNUM");
			String assetnums = this.getString("ASSETNUM");
			String repairkilometer = this.getString("REPAIRKILOMETER");
			String repairfactory = this.getString("REPAIRFACTORY");

			// 上下车更新记录
			IJpoSet swapJpost = this.mroSession.getUserServer().getJpoSet(
					"EXCHANGERECORD",
					"TASKORDERNUM='" + jxtasknum
							+ "' and ISDO='0' and (TASKTYPE='"
							+ SddqConstant.SXC_ZZ + "' or (TASKTYPE='"
							+ SddqConstant.SXC_OH + "' or ISFAILURE!='是'))");
			if (swapJpost != null && swapJpost.count() > 0) {
				swapHistory(swapJpost);
			}
			// 耗损件上下车

			IJpo jxtask = this.getJpo();
			IJpoSet jxproductSet = this.getJpo().getJpoSet("JXPRODUCT");
			for (int i = 0; i < jxproductSet.count(); i++) {
				IJpo jxproduct=jxproductSet.getJpo(i);
				String autonum=jxproduct.getString("AUTONUM");				
				/*IJpoSet jxtasklosspartSet = jxproductSet.getJpo(i).getJpoSet(
						"JXTASKLOSSPART");*/			
				IJpoSet jxtasklosspartSet =MroServer.getMroServer().getSysJpoSet("JXTASKLOSSPART");
				jxtasklosspartSet.setQueryWhere("TASKNUM='"+autonum+"'");
				jxtasklosspartSet.reset();
				if (!jxtasklosspartSet.isEmpty()) {
					WorkorderUtil.consumeUpDown(jxtasklosspartSet, jxtask);
				}
			}

			// 生成交车工单
			// 首先判断该车是否已经生成过交车工单，没有的话则新建交车工单，如果存在交车工单则去在原有的工单基础上去增加交车产品列表。
			IJpoSet jxproductset = this.getAppBean().getJpo()
					.getJpoSet("JXPRODUCT");

			IJpoSet jctaskJposet = MroServer.getMroServer().getSysJpoSet(
					"JCTASKORDER");
			jctaskJposet.setUserWhere("cmodel='" + cmodel + "' and carno='"
					+ carno + "' and REPAIRPROCESS='" + repairprocess + "'");
			jctaskJposet.reset();
			IJpo jctaskorder = null;
			if (jctaskJposet != null && jctaskJposet.count() > 0) {
				jctaskorder = jctaskJposet.getJpo(0);
			} else {
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultOrg("CRRC");
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultSite("ELEC");
				jctaskorder = jctaskJposet.addJpo();
				jctaskorder.setValue("CMODEL", cmodel,
						GWConstant.P_NOVALIDATION);
				jctaskorder.setValue("REPAIRPROCESS", repairprocess,
						GWConstant.P_NOVALIDATION);
				jctaskorder.setValue("CARNO", carno, GWConstant.P_NOVALIDATION);
				jctaskorder.setValue("ASSETNUM", assetnums,
						GWConstant.P_NOVALIDATION);
			}
			if (jctaskorder != null) {
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultOrg("CRRC");
				MroServer.getMroServer().getSystemUserServer().getUserInfo()
						.setDefaultSite("ELEC");

				/*
				 * // 交车工单赋值 IJpoSet jcjobtestrecordset =
				 * MroServer.getMroServer() .getSysJpoSet("JCJOBTESTRECORD");
				 * 
				 * 
				 * // 复制检修方案的交车检查项，按照车型状态 String condition =
				 * " SCHEMENUM in(select  max(SCHEMENUM) from REPAIRSCHEME where STATUS='已发布' and CMODEL='"
				 * + cmodel + "' and REPAIRPROCESS ='" + repairprocess + "')";
				 * 
				 * jcjobtestrecordset.setUserWhere(condition);
				 * jcjobtestrecordset.reset();
				 */

				String jctasknum = jctaskorder.getString("JCTASKNUM");
				IJpoSet jcproductset = jctaskorder.getJpoSet("JCPRODUCT",
						"JCPRODUCT", "1=2");
				for (int i = 0; i < jxproductset.count(); i++) {
					IJpo jxproduct = jxproductset.getJpo(i);
					String sqn = jxproduct.getString("SQN");
					String itemnum = jxproduct.getString("ITEMNUM");
					String assetnum = jxproduct.getString("assetnum");
					IJpo jcproduct = jcproductset.addJpo();
					String jcnum = jcproduct.getString("jcnum");
					jcproduct.setValue("SQN", sqn, GWConstant.P_NOVALIDATION);
					jcproduct.setValue("ASSETNUM", assetnum,
							GWConstant.P_NOVALIDATION);
					jcproduct.setValue("JCTASKNUM", jctasknum,
							GWConstant.P_NOVALIDATION);
					jcproduct.setValue("JXTASKNUM", jxtasknum,
							GWConstant.P_NOVALIDATION);
					jcproduct.setValue("ITEMNUM", itemnum,
							GWConstant.P_NOVALIDATION);
				}
				/*
				 * if (jcjobtestrecordset != null && jcjobtestrecordset.count()
				 * > 0) { for (int index = 0; index <
				 * jcjobtestrecordset.count(); index++) {
				 * 
				 * IJpo jcjobtestrecord = jcjobtestrecordset .getJpo(index);
				 * String testproject = jcjobtestrecord
				 * .getString("TESTPROJECT"); String testcontent =
				 * jcjobtestrecord .getString("TESTCONTENT"); String description
				 * = jcjobtestrecord .getString("DESCRIPTION");
				 * 
				 * IJpoSet jxtestrecordset = jcproduct.getJpoSet(
				 * "$JXTESTRECORD_" + index, "JXTESTRECORD", "1=2"); IJpo
				 * jxtestrecord = jxtestrecordset.addJpo();
				 * 
				 * String jxtestnum = jxtestrecord .getString("JXTESTNUM");
				 * jxtestrecord.setValue("description", description,
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecord.setValue("TESTPROJECT", testproject,
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecord.setValue("TESTCONTENT", testcontent,
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecord.setValue("JCTASKNUM", jctasknum,
				 * GWConstant.P_NOVALIDATION); jxtestrecord.setValue("ASSETNUM",
				 * assetnum, GWConstant.P_NOVALIDATION);
				 * jxtestrecord.setValue("AUTONUM", jcnum,
				 * GWConstant.P_NOVALIDATION); jxtestrecord.setValue("SEQ",
				 * jcjobtestrecord.getString("SEQ"), GWConstant.P_NOVALIDATION);
				 * 
				 * IJpoSet jcjobtestrecordingSet = jcjobtestrecord
				 * .getJpoSet("JCJOBTESTRECORDING"); for (int j = 0; j <
				 * jcjobtestrecordingSet.count(); j++) { IJpo jcjobtestrecording
				 * = jcjobtestrecordingSet .getJpo(j); String loc =
				 * jcjobtestrecording .getString("LOC"); String standardvalue =
				 * jcjobtestrecording .getString("STANDARDVALUE");
				 * 
				 * IJpoSet jxtestrecorditemset = jxtestrecord
				 * .getJpoSet("$JXTESTRECORDITEM" + index + j,
				 * "JXTESTRECORDITEM", "1=2"); IJpo jxtestrecorditem =
				 * jxtestrecorditemset .addJpo();
				 * jxtestrecorditem.setValue("UNIT",
				 * jcjobtestrecording.getString("UNIT"),
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecorditem.setValue("TESTPROJECT", jcjobtestrecording
				 * .getString("TESTPROJECT"), GWConstant.P_NOVALIDATION);
				 * jxtestrecorditem.setValue("TESTLOCATION", loc,
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecorditem.setValue("STANDARDVALUE", standardvalue,
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecorditem.setValue("JXTESTNUM", jxtestnum,
				 * GWConstant.P_NOVALIDATION);
				 * jxtestrecorditem.setValue("JCTASKNUM", jctasknum,
				 * GWConstant.P_NOVALIDATION); jxtestrecorditem.setValue("SEQ",
				 * jcjobtestrecording.getString("SEQ"),
				 * GWConstant.P_NOVALIDATION); } } } }
				 */
				jctaskJposet.save();
				Date arrivaldate = MroServer.getMroServer().getDate();
				IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
						"ASSET",
						"CMODEL='" + cmodel + "' and CARNO='" + carno + "'");

				if (!assetSet.isEmpty()) {
					assetSet.getJpo().setValue("REPAIRPROCESS", repairprocess,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					assetSet.getJpo().setValue("OVERHAULER", repairfactory,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					assetSet.getJpo().setValue("REPAIRKILOMETER",
							repairkilometer,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					IJpoSet checkdynamicSet = assetSet.getJpo().getJpoSet(
							"CHECKDYNAMIC");
					IJpo checkdynamic = checkdynamicSet.addJpo();
					checkdynamic.setValue("REPAIRDATE", arrivaldate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					checkdynamic.setValue("REPAIRKILOMETER", repairkilometer,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					checkdynamic.setValue("REPAIRPROCESS", repairprocess,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					checkdynamic.setValue("OVERHAULER", repairfactory,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					checkdynamic.setValue("ASSETNUM", assetnums,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					checkdynamic.setValue("CHECKBY", getAppBean().getJpo()
							.getUserInfo().getPersonId(),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					assetSet.save();
				}
				this.getAppBean()
						.getJpo()
						.setValue("STATUS", "已完成",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getAppBean().SAVE();
			}
		}
	}

	/**
	 * 上下车记录更新
	 * 
	 * @param jposet
	 *            [参数说明]
	 * @throws MroException
	 */
	public void swapHistory(IJpoSet jposet) throws MroException {

		for (int i = 0; i < jposet.count(); i++) {

			IJpo jpo = jposet.getJpo(i);
			// 下车件的信息
			String assetnum = jpo.getString("ASSETNUM");
			String ancestor = jpo.getString("ASSET.ANCESTOR");
			String parent = jpo.getString("ASSET.PARENT");
			String faultposition=jpo.getString("FAULTPOSITION");

			// 获取ASSET表中待拆卸的选中部件以及子部件
			IJpoSet removeMboset = MroServer.getMroServer().getSysJpoSet(
					"ASSET");
			removeMboset
					.setUserWhere("ANCESTOR='"
							+ ancestor
							+ "' and assetnum in (select assetnum from asset  start with assetnum ='"
							+ assetnum
							+ "' connect by parent = PRIOR assetnum)");
			removeMboset.reset();

			// 保留结构，更新Asset表的parent以及ancestor
			if (!removeMboset.isEmpty()) {
				for (int index = 0; index < removeMboset.count(); index++) {
					if (removeMboset.getJpo(index).getString("ASSETNUM")
							.equals(assetnum)) {
						removeMboset.getJpo(index).setValue("parent", "",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						removeMboset.getJpo(index).setValue("ASSETLEVEL",
								"ASSET",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					removeMboset.getJpo(index).setValue("ANCESTOR", assetnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("LOCATION", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 检修的下车件默认要放到待处理物资库
					removeMboset.getJpo(index).setValue("TYPE", "3",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("DOWNTYPE",
							SddqConstant.DOWN_JX,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					removeMboset.getJpo(index).setValue("STATUS",
							SddqConstant.SXC_DCL,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				removeMboset.save();
			}
			// 上车件的信息
			String newassetnum = jpo.getString("NEWASSETNUM");
			// String newsqn = jpo.getString("NEWSQN");
			// String newitemnum = jpo.getString("NEWITEMNUM");

			IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet(
					"ASSET");
			installMboset
					.setUserWhere("assetnum in (select assetnum from asset  start with assetnum ='"
							+ newassetnum
							+ "' connect by parent = PRIOR assetnum)");
			installMboset.reset();

			// 给从库存中取出的部件及子部件设置ANCESTOR和parent
			if (!installMboset.isEmpty()) {

				for (int index = 0; index < installMboset.count(); index++) {

					if (installMboset.getJpo(index).getString("ASSETNUM")
							.equals(newassetnum)) {
						installMboset.getJpo(index).setValue("parent", parent,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						installMboset.getJpo(index).setValue("ASSETLEVEL",
								"SYSTEM",
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					installMboset.getJpo(index).setValue("ANCESTOR", ancestor,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("LOCATION", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("BINNUM", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("TYPE", "2",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("ISNEW", "0",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					installMboset.getJpo(index).setValue("RNUM", faultposition,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
				installMboset.save();
			}
			// 在检修领料单中的存放产品序列号件的表中更新status=1，标记已经上车
			IJpoSet mJpoSet = MroServer.getMroServer().getSysJpoSet(
					"MUSTCHANGEMPR",
					"assetnum='" + newassetnum + "' or sqn = '"
							+ jpo.getString("NEWSQN") + "'");
			if (mJpoSet != null && mJpoSet.count() > 0) {
				mJpoSet.getJpo(0).setValue("STATUS", "1",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			mJpoSet.save();
			jpo.setValue("ISDO", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			if (SddqConstant.SXC_OH.equals(jpo.getString("TASKTYPE"))) {
				// 出库
				CommonInventory.OUTINVENTORY("", 1, jpo.getString("newloc"),
						jpo.getString("newitemnum"),
						jpo.getString("newassetnum"),
						jpo.getString("TASKORDERNUM"));
				// 入库
				CommonInventory.ININVENTORY("", 1, jpo.getString("location"),
						jpo.getString("itemnum"), jpo.getString("assetnum"),
						jpo.getString("TASKORDERNUM"));
			} else if (SddqConstant.SXC_ZZ.equals(jpo.getString("TASKTYPE"))) {// 检修周转件下车
				CommonInventory.ININVENTORY("", 1, jpo.getString("location"),
						jpo.getString("itemnum"), jpo.getString("assetnum"),
						jpo.getString("taskordernum"));
			}
		}
		jposet.save();
	}

	@Override
	protected void afterSetValue(String attribute) throws MroException {
		super.afterSetValue(attribute);
		if (attribute.equals("JPNUM")) {
			try {
				this.getDataBean("1508746987303").reloadSelfAndSubs();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送生成领料单
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */

	public void SENDMPR() throws MroException, IOException {

		String jxtasknum = this.getString("JXTASKNUM");// 工单编号
		String projectnum = this.getString("PROJECTNUM");// 项目编号
		String whichoffice = this.getString("WHICHOFFICE");// 办事处
		// String stock = this.getString("STOCK");// 库存地点
		String PRODUCTIONORDERNUM = this.getString("PRODUCTIONORDERNUM");// ERP生产订单编号
		IJpoSet mprSet = MroServer.getMroServer().getSysJpoSet("MPR");
		mprSet.setQueryWhere("PRODUCTIONORDERNUM='"
				+ PRODUCTIONORDERNUM + "' and PLAN!='1'");
		mprSet.reset();
		if(mprSet.isEmpty()){
		IJpoSet mustchangeinfoset = this.getAppBean().getJpo()
				.getJpoSet("MUSTCHANGEINFO");// 必换件信息
		// IJpoSet mprset =
		// this.getAppBean().getJpo().getJpoSet("$MPR","MPR","1=2");
		// IJpoSet mprset = MroServer.getMroServer().getSysJpoSet("MPR");//领料单
		IJpoSet mprset = this.getJpo().getJpoSet("$MPR", "MPR", "1=2");
		IJpo mprsetaddJpo = mprset.addJpo();
		String mprnum = mprsetaddJpo.getString("MPRNUM");// 获取主表的单号
/*20181113肖林宝修改编号规则*/
		String loginid = this.getUserInfo().getLoginID();
		loginid=loginid.toUpperCase();
		IJpoSet deptset = MroServer.getMroServer().getJpoSet("SYS_DEPT", MroServer.getMroServer().getSystemUserServer());
		deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='" + loginid + "')");
		deptset.reset();
		if(!deptset.isEmpty()){
			String deptnum=deptset.getJpo(0).getString("deptnum");
			String retrun=WorkorderUtil.getAbbreviationByDeptnum(deptnum);
			if(retrun.equalsIgnoreCase("PJGL")){
				mprnum = "ZX-" + "JXLL" + "-" + mprnum;
			}else{
				mprnum = retrun+"-" + "JXLL" + "-" + mprnum;
			}
		}else{
			mprnum = "ZX-" + "JXLL" + "-" + mprnum;
		}
		mprsetaddJpo.setValue("mprnum", mprnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
/*20181113肖林宝修改编号规则*/
		mprsetaddJpo.setValue("PROJECTNUM", projectnum);
		mprsetaddJpo.setValue("GDTYPE", "检修");
		mprsetaddJpo.setValue("TASKNUM", jxtasknum);
		mprsetaddJpo.setValue("mprtype", "JX");
		mprsetaddJpo.setValue("PRODUCTIONORDERNUM", PRODUCTIONORDERNUM);// ERP生产订单编号
		mprsetaddJpo.setValue("WHICHOFFICE", whichoffice);// 办事处
		// mprsetaddJpo.setValue("MPRSTOREROOM", stock);// 库存地点
			if (!mustchangeinfoset.isEmpty()) {
				IJpoSet mprlineset = mprsetaddJpo.getJpoSet("MPRLINE");
				for (int i = 0; i < mustchangeinfoset.count(); i++) {
					IJpo mustchangeinfo = mustchangeinfoset.getJpo(i);
					String mobiletype = mustchangeinfo.getString("MOBILETYPE");// 移动类型
					String collecttiondate = mustchangeinfo
							.getString("COLLECTTIONDATE");// 过账日期
					String factory = mustchangeinfo.getString("FACTORY");// 工厂
					String distributor = mustchangeinfo
							.getString("DISTRIBUTOR.DISPLAYNAME");// 发料人
					String productionordernum = mustchangeinfo
							.getString("PRODUCTIONORDERNUM");// 生产订单号
					String stockaddress = mustchangeinfo
							.getString("STOCKADDRESS");// 库存地点
					String itemnum = mustchangeinfo.getString("ITEMNUM");// 物料编码
					Float amount = mustchangeinfo.getFloat("AMOUNT");// 点收数量
					String measurementunit = mustchangeinfo
							.getString("MEASUREMENTUNIT");// 计量单位
					String obligatenum = mustchangeinfo
							.getString("OBLIGATENUM");// 预留号
					String obligatelinenum = mustchangeinfo
							.getString("OBLIGATELINENUM");// 预留号
					String recordtype = mustchangeinfo.getString("RECORDTYPE");// 记录类型
					String lotnum = mustchangeinfo.getString("LOTNUM");// 批次号
					// 检修领料单主表
					// IJpoSet mprset =
					// MroServer.getMroServer().getSysJpoSet("MPR");//领料单
					// IJpo mprsetgetJpo = mprset.getJpo();
					// String mprnum =
					// mprsetgetJpo.getString("MPRNUM");//获取主表的单号
					// IJpoSet mustchangemprset
					// =this.getAppBean().getJpo().getJpoSet("");
					// IJpoSet mustchangemprset =
					// MroServer.getMroServer().getSysJpoSet("MUSTCHANGEMPR");//获取检修领料单子表必换件
					IJpo mprlinesetaddJpo = mprlineset.addJpo();
					mprlinesetaddJpo.setValue("MPRNUM", mprnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("MOBILETYPE", mobiletype,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("COLLECTTIONDATE",
							collecttiondate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("FACTORY", factory,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("DISTRIBUTOR", distributor,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("PRODUCTIONORDERNUM",
							productionordernum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("STOCKADDRESS", stockaddress,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("ITEMNUM", itemnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("qty", amount,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("curbal", 0,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("MEASUREMENTUNIT",
							measurementunit,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("OBLIGATENUM", obligatenum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("OBLIGATELINENUM",
							obligatelinenum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("RECORDTYPE", recordtype,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("LOTNUM", lotnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					mprlinesetaddJpo.setValue("tasknum", jxtasknum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				
				}
			} 
		}
		else {
			throw new AppException("jxtaskorder", "sendmpr");
		}
		this.getAppBean().SAVE();
	}

	/**
	 * <功能描述>检修完工触发ERP入库//
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 */
	public void ERPRK() throws MroException, IOException {
		String num = "";
		String num1 = "";
		String infacename = "ERP_MRO_TOEROCKIF_101JX";
		IJpo jpo = this.getJpo();
		if (jpo == null)
			return;
		if (!jpo.getString("status").equals("已完成"))
			throw new AppException("JXTASKORDE", "statuswarn");//
		if (jpo.getString("STOCK.STOREROOMPARENT").isEmpty()) {
			throw new AppException("JXTASKORDE", "locwarn");//

		}
		String movetype = "101";// 移动类型
		Date createdate = MroServer.getMroServer().getDate();// 点日期
		String person = this.getUserInfo().getDisplayName();// 点收人
		String factory = jpo.getString("factory");// 工厂
		String material = jpo.getString("JXCODE");// 物料编码
		String stge_loc = jpo.getString("STOCK.STOREROOMPARENT");// 库存地点
		if (StringUtil.isStrEmpty(stge_loc)) {
			stge_loc = jpo.getString("STOCK");
		}
		String jxtasknum = jpo.getString("jxtasknum");// 工单编号
		double amount = jpo.getDouble("AMOUNT");
		String dw = "EA";
		String productionordernum = jpo.getString("productionordernum");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String CREATEDATES = sdf.format(createdate);
		JxWorkOrderCommon JxWorkOrderCommon = new JxWorkOrderCommon();
		try {
			num = IFUtil.addIfHistory(infacename, "移动类型：" + movetype + " 点收日期："
					+ CREATEDATES + " 点收人：" + person + "工厂" + factory + "物料号："
					+ material + "库房：" + stge_loc + "数量：" + amount + "单位：" + dw
					+ "订单号：" + productionordernum + "", IFUtil.TYPE_INPUT);
			String[] str = JxWorkOrderCommon.jxworkorder(movetype, factory,
					CREATEDATES, person, material, stge_loc,
					productionordernum, amount, dw);
			num1 = IFUtil.addIfHistory(infacename, str[0] + str[1] + str[2]
					+ str[3], IFUtil.TYPE_OUTPUT);
			if (str[0].equals("E")) {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, str[1]);
				IFUtil.updateIfHistory(num1, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, str[1]);
				throw new MroException("", str[1]);//

			} else {
				this.setValue("VOUCHER", str[2]);// 凭证
				this.setValue("YEAR", str[3]);// 年度
				IFUtil.updateIfHistory(num1, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "");

				CommonInventory.ININVENTORY("", amount, jpo.getString("STOCK"),
						material, "", jxtasknum);
				this.getAppBean().setValue("STATUS", SddqConstant.JX_ERP_RK,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getAppBean().SAVE();
				showMsgbox("提示", "入库成功" + " 凭证" + str[2] + " 年度" + str[3]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();

			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					msg);
			throw new AppException("入库失败", "" + msg);//

		}
	}

}
