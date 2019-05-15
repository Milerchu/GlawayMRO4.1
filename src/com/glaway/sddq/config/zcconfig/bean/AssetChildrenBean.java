package com.glaway.sddq.config.zcconfig.bean;

import io.netty.util.internal.StringUtil;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 
 * 选择产品dataBean
 * 
 * @author  hyhe
 * @version  [版本号, 2018-5-15]
 * @since  [产品/模块版本]
 */
public class AssetChildrenBean extends DataBean
{
    @Override
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        super.addEditRowCallBackOk();
        if (this.getAppName() != null && this.getAppName().equals("ZCCONFIG"))
        {
            if(this.getJpo().getField("SQN").isValueChanged()){
                
//                String pXassetnum = this.getJpo().getField("XASSETNUM").getPreviousMroType().getStringValue();
//                String pSqn = this.getJpo().getField("SQN").getPreviousMroType().getStringValue();
                String assetnum = this.getJpo().getString("assetnum");
                //然后将新的数据放到系统中
                String installNum = this.getJpo().getString("XASSETNUM");
                if(!StringUtil.isNullOrEmpty(installNum)){
                //首先现将之前选择的产品重现放到初始配置中去，type=0
                //为了避免多次重新选择产品序列号的问题，需要到数据库去查询当前节点的产品序列号是否为空
                IJpoSet dbJposet = MroServer.getMroServer().getSysJpoSet("ASSET", "assetnum='"+assetnum+"'");
                if(dbJposet != null && dbJposet.count() > 0 && dbJposet.getJpo(0) != null && !StringUtil.isNullOrEmpty(dbJposet.getJpo(0).getString("SQN"))){
                    downFromCar(assetnum);
                }

                //获取ASSET表中选中部件以及子部件,没有ANCESTOR
                IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet("ASSET");
                installMboset.setUserWhere("ancestor ='" + installNum + "'");
                installMboset.reset();
                //装机目标部件，目前只能装到车厢结构上
                String toAncestor = this.getJpo().getParent().getString("ancestor");
                String toParent = this.getJpo().getParent().getString("assetnum");
                //给从保存位置取出的部件及子部件设置ANCESTOR和parent
                if (installMboset != null && installMboset.count() > 0)
                {
                    for (int index = 0; index < installMboset.count(); index++)
                    {
                        if (installMboset.getJpo(index).getString("ASSETNUM").equals(installNum))
                        {
                            installMboset.getJpo(index).setValue("parent",
                                toParent,
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("assetlevel",
                                "SYSTEM",
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            
                            installMboset.getJpo(index).setValue("LINENUM",
                                this.getJpo().getString("LINENUM"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("RNUM",
                                this.getJpo().getString("RNUM"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("CONFIGNUM",
                                this.getJpo().getString("CONFIGNUM"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("LOC",
                                this.getJpo().getString("LOC"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("LOCDESC",
                                    this.getJpo().getString("LOCDESC"),
                                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("SOFTNAME",
                                this.getJpo().getString("SOFTNAME"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("SOFTVERSION",
                                this.getJpo().getString("SOFTVERSION"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            installMboset.getJpo(index).setValue("SOFTUPDATE",
                                this.getJpo().getDate("SOFTUPDATE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        }
                        installMboset.getJpo(index).setValue("ANCESTOR",
                            toAncestor,
                            GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        installMboset.getJpo(index).setValue("type", "2", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        installMboset.getJpo(index).setValue("LOCATION", "", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
//                        installMboset.getJpo(index).setValue("STATUS", SddqConstant.UP_CAR_STATUS, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    }
                    //              toParentAsset.setValue("children", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    installMboset.save();
                }
               
                if(dbJposet != null && dbJposet.count() > 0 && dbJposet.getJpo(0) != null && StringUtil.isNullOrEmpty(dbJposet.getJpo(0).getString("SQN"))){//删除更新产品序列号的本节点，避免assetnum重复
                    this.getJpo().delete(0);
                }else{
                    this.getJpo().getJpoSet().reset();
                }
                }
                this.getAppBean().SAVE();
                //重新加载节点的子层节点
                ((TreeBean)parent).reloadNodeWithoutReset();
            }
            
//            if (this.getJpo().isNew())
//            {
//                //在AssetTree表中建立关系
//                //1)获取当前要挂上去的父节点以及所有祖先节点
//                IJpoSet parentToJpoSet =
//                    MroServer.getMroServer().getSysJpoSet("ASSETTREE",
//                        "assetnum ='" + StringUtil.getSafeSqlStr(toParent) + "'");
//                parentToJpoSet.setOrderBy("ancestor asc");
//                
//                //2)获取当前节点以及子级节点
//                IJpoSet fromChildJpoSet =
//                    MroServer.getMroServer().getSysJpoSet("ASSETTREE",
//                        "ANCESTOR ='" + StringUtil.getSafeSqlStr(installNum) + "'");
//                fromChildJpoSet.setOrderBy("assetnum asc");
//                
//                IJpoSet newToJpoSet = MroServer.getMroServer().getSysJpoSet("ASSETTREE");
//                
//                if (!fromChildJpoSet.isEmpty() && !parentToJpoSet.isEmpty())
//                {
//                    for (int j = 0; j < parentToJpoSet.count(); j++)
//                    {
//                        IJpo parentJpo = parentToJpoSet.getJpo(j);
//                        
//                        for (int i = 0; i < fromChildJpoSet.count(); i++)
//                        {
//                            IJpo toJpo = fromChildJpoSet.getJpo(i);
//                            int level =
//                                Integer.valueOf(parentJpo.getString("HIERARCHYLEVELS"))
//                                    + Integer.valueOf(toJpo.getString("HIERARCHYLEVELS")) + 1;
//                            IJpo ancestor = newToJpoSet.addJpo();
//                            ancestor.setValue("ASSETNUM", toJpo.getString("ASSETNUM"));
//                            ancestor.setValue("HIERARCHYLEVELS", level);
//                            ancestor.setValue("SITEID", this.getJpo().getString("SITEID"));
//                            ancestor.setValue("ORGID", this.getJpo().getString("ORGID"));
//                            ancestor.setValue("ANCESTOR", parentJpo.getString("ANCESTOR"));
//                        }
//                    }
//                }
//                newToJpoSet.save();
//            }
        }
    }
    
    /**
     * 
     * @Description 将该产品下车
     * @param pXassetnum
     * @throws MroException 
     */
    private void downFromCar(String pXassetnum) throws MroException {
        
        //首先找到该节点的所有子级节点
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET");
        jposet.setUserWhere("assetnum in (select assetnum from asset  start with assetnum ='"+pXassetnum+"' connect by parent = PRIOR assetnum)");
        jposet.reset();
        //变更assetlevel 为ASSET
        if(jposet != null && jposet.count() > 0){
            for (int index=0;index < jposet.count();index++){
                IJpo jpo = jposet.getJpo(index);
                if(jpo.getString("ASSETNUM").equals(pXassetnum)){
                    jpo.setValue("assetlevel", "ASSET",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    jpo.setValue("parent", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
                jpo.setValue("ancestor", pXassetnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("type", "0",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("status", SddqConstant.NO_UP_CAR_STATUS,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            }
            jposet.save();
           }
    }

    /**
     * 新建窗口，点击取消按钮后，重新加载节点的子层节点
     * @throws MroException
     */
    @Override
    public void resetAndFixPos()
        throws MroException
    {
        super.resetAndFixPos();
        try
        {
            ((TreeBean)parent).reloadNodeWithoutReset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 重新刷新树
     * @throws MroException
     */
    @Override
    public synchronized void reset()
        throws MroException
    {
        super.reset();
        try
        {
            ((TreeBean)parent).reloadNodeWithoutReset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
