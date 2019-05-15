package com.glaway.sddq.overhaul.jctaskorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.invtrans.common.CommonInventory;
import com.glaway.sddq.tools.SddqConstant;

/**
 * @ClassName JcProductDataBean
 * @Description 交车产品DataBean
 * @author public2175
 * @Date 2018-7-30 下午7:24:03
 * @version 1.0.0
 */
public class JcProductDataBean extends DataBean {

    /**
     * @throws MroException
     * @Description 对一个产品进行单独交车工作
     * @throws MroException
     * @throws Exception
     */
    public void toJcForProduct() throws MroException {
        String cmodel = this.getString("cmodel");
        String carno = this.getString("carno");
        String carriagenum = this.getString("carriagenum");
        String status = this.getString("status");
        if (StringUtil.isStrEmpty(cmodel)) {
            throw new AppException("jctaskorder", "cmodelisnull");
        }
        if (StringUtil.isStrEmpty(carno)) {
            throw new AppException("jctaskorder", "carnoisnull");
        }

        if (StringUtil.isStrEmpty(carriagenum)) {
            throw new AppException("jctaskorder", "carriagenumisnull");
        }
        if (StringUtil.isEqual(status, SddqConstant.JC_T_STATUS)) {
            throw new AppException("jctaskorder", "hasjc");
        }

        String jctasknum = this.getString("JCTASKNUM");
        String assetnum = this.getString("ASSETNUM");
        String jcnum = this.getString("JCNUM");
        // 上下车更新记录
        IJpoSet swapJpost = MroServer.getMroServer().getSysJpoSet(
                "EXCHANGERECORD",
                "TASKORDERNUM='" + jctasknum + "' and ISDO='0' and TASKTYPE='交车工单' and ISFAILURE !='是' and autonum ='"
                        + jcnum + "'");
        if (swapJpost != null && swapJpost.count() > 0) {
            swapHistory(swapJpost);
        }
        IJpoSet jctaskorderSet = MroServer.getMroServer().getSysJpoSet("JCTASKORDER");
        jctaskorderSet.setQueryWhere("JCTASKNUM='" + jctasknum + "'");
        IJpo jctaskorder = jctaskorderSet.getJpo();
        String jcstatus = jctaskorder.getString("STATUS");
        if (StringUtil.isEqual(jcstatus, SddqConstant.JC_STATUS_CG)) {
            throw new AppException("jctaskorder", "version");
        }
        if (StringUtil.isEqual(jcstatus, SddqConstant.JC_STATUS_PG)) {
            throw new AppException("jctaskorder", "version");
        } else {
            // 交车
            zcToAsset(this.getJpo());
            try {
                this.getAppBean().SAVE();
            } catch (IOException e) {
                e.printStackTrace();
                this.getJpo().setValue("status", SddqConstant.JC_F_STATUS);
            }
        }
    }

    /**
     * @Description 交车
     * @param jcProductDataBean
     */
    public void zcToAsset(IJpo jpo) {
        // 根据选择的车型车号获取Ancestor
        try {
            String carno = jpo.getString("CARNO");
            String cmodel = jpo.getString("CMODEL");
            String carriagenum = jpo.getString("CARRIAGENUM");
            String sqn = jpo.getString("sqn");
            String jcassetnum = jpo.getString("assetnum");
            String parent = "";
            String ancestor = this.getString("carassetnum");
            String loc = jpo.getString("ASSET.LOCATION");
            if (StringUtil.isStrEmpty(ancestor)) {
                IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("ASSET",
                        "CARNO='" + carno + "' and CMODEL='" + cmodel + "'");
                ancestor = assetSet.getJpo(0).getString("ANCESTOR");
            }
            if (!StringUtil.isStrEmpty(ancestor)) {
                // 根据选择的车型车号车厢号获取 parent
                // String carriagenum
                // =assetSet.getJpo().getString("CARRIAGENUM");
                // String parent = assetSet.getJpo().getString("PARENT");
                IJpoSet carassetset = MroServer.getMroServer().getSysJpoSet("ASSET",
                        "ANCESTOR='" + ancestor + "' and CARRIAGENUM='" + carriagenum + "'");
                if (carassetset != null && carassetset.count() > 0) {
                    parent = carassetset.getJpo(0).getString("ASSETNUM");
                    System.out.println(parent);
                    IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet("ASSET",
                            "ancestor ='" + jcassetnum + "'");

                    // 给从库存中取出的部件及子部件设置ANCESTOR和parent
                    if (installMboset != null && installMboset.count() > 0) {
                        for (int index = 0; index < installMboset.count(); index++) {
                            if (installMboset.getJpo(index).getString("ASSETNUM").equals(jcassetnum)) {
                                installMboset.getJpo(index).setValue("parent", parent,
                                        GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                installMboset.getJpo(index).setValue("ASSETLEVEL", "SYSTEM",
                                        GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            }
                            installMboset.getJpo(index).setValue("ANCESTOR", ancestor,
                                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("LOCATION", "",
                                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("BINNUM", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("TYPE", "2", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        }
                        installMboset.save();

                        // 在AssetTree表中建立关系
                        // 1)获取当前要挂上去的父节点以及所有祖先节点
                        // IJpoSet parentToJpoSet = MroServer.getMroServer()
                        // .getSysJpoSet(
                        // "ASSETTREE",
                        // "assetnum ='"
                        // + StringUtil
                        // .getSafeSqlStr(parent)
                        // + "'");
                        // parentToJpoSet.setOrderBy("ancestor asc");
                        //
                        // // 2)获取当前节点以及子级节点
                        // IJpoSet fromChildJpoSet = MroServer
                        // .getMroServer()
                        // .getSysJpoSet(
                        // "ASSETTREE",
                        // "ANCESTOR ='"
                        // + StringUtil
                        // .getSafeSqlStr(ancestor)
                        // + "'");
                        // fromChildJpoSet.setOrderBy("assetnum asc");
                        //
                        // IJpoSet newToJpoSet = MroServer.getMroServer()
                        // .getSysJpoSet("ASSETTREE");
                        //
                        // if (!fromChildJpoSet.isEmpty()
                        // && !parentToJpoSet.isEmpty()) {
                        // for (int j = 0; j < parentToJpoSet.count(); j++) {
                        // IJpo parentJpo = parentToJpoSet.getJpo(j);
                        //
                        // for (int ij = 0; ij < fromChildJpoSet.count(); ij++) {
                        // IJpo toJpo = fromChildJpoSet.getJpo(ij);
                        // int level = Integer.valueOf(parentJpo
                        // .getString("HIERARCHYLEVELS"))
                        // + Integer
                        // .valueOf(toJpo
                        // .getString("HIERARCHYLEVELS"))
                        // + 1;
                        // IJpo ancestorJpo = newToJpoSet.addJpo();
                        // ancestorJpo.setValue("ASSETNUM",
                        // toJpo.getString("ASSETNUM"));
                        // ancestorJpo.setValue("HIERARCHYLEVELS",
                        // level);
                        // ancestorJpo.setValue("SITEID",
                        // jpo.getString("SITEID"));
                        // ancestorJpo.setValue("ORGID",
                        // jpo.getString("ORGID"));
                        // ancestorJpo.setValue("ANCESTOR",
                        // parentJpo.getString("ANCESTOR"));
                        // }
                        // }
                        // }
                        // newToJpoSet.save();
                    }
                    if(StringUtil.isStrNotEmpty(loc)){
                        CommonInventory.OUTINVENTORY("", 1, loc, jpo.getString("itemnum"), jcassetnum,
                                jpo.getString("JCTASKNUM"));// 自动出库（不需要传给ERP）
                    }
                    this.getJpo().setValue("status", SddqConstant.JC_T_STATUS);
                }
            }
        } catch (MroException e) {
            e.printStackTrace();
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
            // String sqn = jpo.getString("sqn");
            // String itemnum = jpo.getString("itemnum");
            String ancestor = jpo.getString("ASSET.ANCESTOR");
            String parent = jpo.getString("ASSET.PARENT");

            // 获取ASSET表中待拆卸的选中部件以及子部件
            IJpoSet removeMboset = MroServer.getMroServer().getSysJpoSet("ASSET");
            removeMboset.setUserWhere("ANCESTOR='" + ancestor
                    + "' and assetnum in (select assetnum from asset  start with assetnum ='" + assetnum
                    + "' connect by parent = PRIOR assetnum)");
            removeMboset.reset();

            // 保留结构，更新Asset表的parent以及ancestor
            if (!removeMboset.isEmpty()) {
                for (int index = 0; index < removeMboset.count(); index++) {
                    if (removeMboset.getJpo(index).getString("ASSETNUM").equals(assetnum)) {
                        removeMboset.getJpo(index).setValue("parent", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        removeMboset.getJpo(index).setValue("ASSETLEVEL", "ASSET",
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    }
                    removeMboset.getJpo(index).setValue("ANCESTOR", assetnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    removeMboset.getJpo(index).setValue("LOCATION", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);// 检修的下车件默认要放到待处理物资库
                    removeMboset.getJpo(index).setValue("TYPE", "3", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
                removeMboset.save();

                // 更新assettree结构(删除所有拆除节点及其子节点与祖先节点的节点关系)
                // IJpoSet removeTreeMboset = MroServer.getMroServer()
                // .getSysJpoSet("assettree");
                // removeTreeMboset.setUserWhere("ANCESTOR like '" + ancestor
                // + "%'  and assetnum like '" + assetnum
                // + "%'  and  ANCESTOR not like '" + assetnum + "%'");
                // removeTreeMboset.reset();
                //
                // removeTreeMboset.deleteAll();
                // removeTreeMboset.save();
            }

            // 上车件的信息
            String newassetnum = jpo.getString("NEWASSETNUM");
            // String newsqn = jpo.getString("NEWSQN");
            // String newitemnum = jpo.getString("NEWITEMNUM");

            IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet("ASSET");
            installMboset.setUserWhere("assetnum in (select assetnum from asset  start with assetnum ='" + newassetnum
                    + "' connect by parent = PRIOR assetnum)");
            installMboset.reset();

            // 给从库存中取出的部件及子部件设置ANCESTOR和parent
            if (!installMboset.isEmpty()) {
                for (int index = 0; index < installMboset.count(); index++) {

                    if (installMboset.getJpo(index).getString("ASSETNUM").equals(newassetnum)) {
                        installMboset.getJpo(index).setValue("parent", parent, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        installMboset.getJpo(index).setValue("ASSETLEVEL", "SYSTEM",
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    }
                    installMboset.getJpo(index).setValue("ANCESTOR", ancestor, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    installMboset.getJpo(index).setValue("LOCATION", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    installMboset.getJpo(index).setValue("BINNUM", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    installMboset.getJpo(index).setValue("TYPE", "2", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
                installMboset.save();

                // 在AssetTree表中建立关系
                // 1)获取当前要挂上去的父节点以及所有祖先节点
                // IJpoSet parentToJpoSet = MroServer.getMroServer().getSysJpoSet(
                // "ASSETTREE",
                // "assetnum ='" + StringUtil.getSafeSqlStr(parent) + "'");
                // parentToJpoSet.setOrderBy("ancestor asc");
                //
                // // 2)获取当前节点以及子级节点
                // IJpoSet fromChildJpoSet = MroServer.getMroServer()
                // .getSysJpoSet(
                // "ASSETTREE",
                // "ANCESTOR ='"
                // + StringUtil.getSafeSqlStr(newassetnum)
                // + "'");
                // fromChildJpoSet.setOrderBy("assetnum asc");
                //
                // IJpoSet newToJpoSet = MroServer.getMroServer().getSysJpoSet(
                // "ASSETTREE");
                //
                // if (!fromChildJpoSet.isEmpty() && !parentToJpoSet.isEmpty()) {
                // for (int j = 0; j < parentToJpoSet.count(); j++) {
                // IJpo parentJpo = parentToJpoSet.getJpo(j);
                //
                // for (int ij = 0; ij < fromChildJpoSet.count(); ij++) {
                // IJpo toJpo = fromChildJpoSet.getJpo(ij);
                // int level = Integer.valueOf(parentJpo
                // .getString("HIERARCHYLEVELS"))
                // + Integer.valueOf(toJpo
                // .getString("HIERARCHYLEVELS")) + 1;
                // IJpo ancestorJpo = newToJpoSet.addJpo();
                // ancestorJpo.setValue("ASSETNUM",
                // toJpo.getString("ASSETNUM"));
                // ancestorJpo.setValue("HIERARCHYLEVELS", level);
                // ancestorJpo.setValue("SITEID",
                // jpo.getString("SITEID"));
                // ancestorJpo.setValue("ORGID",
                // jpo.getString("ORGID"));
                // ancestorJpo.setValue("ANCESTOR",
                // parentJpo.getString("ANCESTOR"));
                // }
                // }
                // }
                // newToJpoSet.save();
            }
            
        	// 出库
			CommonInventory.OUTINVENTORY("", 1, jpo.getString("newloc"),
					jpo.getString("newitemnum"), jpo.getString("newassetnum"),
					jpo.getString("TASKORDERNUM"));
			// 入库
			CommonInventory.ININVENTORY("", 1, jpo.getString("location"),
					jpo.getString("itemnum"), jpo.getString("assetnum"),
					jpo.getString("TASKORDERNUM"));
			
            jpo.setValue("ISDO", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            jposet.save();
        }
    }

}
