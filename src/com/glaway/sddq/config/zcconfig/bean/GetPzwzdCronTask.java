package com.glaway.sddq.config.zcconfig.bean;

import io.netty.util.internal.StringUtil;

import java.text.NumberFormat;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * @ClassName GetPzwzdCronTask
 * @Description 更新车辆配置的完整度
 * @author public2175
 * @Date 2018-9-3 下午4:11:53
 * @version 1.0.0
 */
public class GetPzwzdCronTask extends BaseStatefulJob {

    /**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    public void execute() throws MroException {
        // 1、首先去ASSET表中去获取没有获取过出厂配置的数据
        IJpoSet assetJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET","assetlevel='ASSET' and type='2' and cmodel is not null and carno is not null");
        // 2、查找到数据之后，循环去ERP获取出厂配置数据
        if (assetJpoSet != null && assetJpoSet.count() > 0) {
            for (int index = 0; index < assetJpoSet.count(); index++) {
                IJpo asset = assetJpoSet.getJpo(index);
                if (asset != null) {
                    getPzwzd(asset);
                    getYJPzwzd(asset);
                }
            }
            assetJpoSet.save();
        }
    }
    
    /**
     * 
     * 计算一级部件的完整度
     * @param asset
     * @throws MroException [参数说明]
     *
     */
    private void getYJPzwzd(IJpo jpo) throws MroException {
    	
    	if (jpo.getField("ASSETLEVEL").getValue().equals("ASSET")) {
            String ancestor = jpo.getString("assetnum");
            // 获取所有子集（不包括车厢节点，assetlevel = 'SYSTEM'）
            // 获取车厢信息
            IJpoSet carJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET",
                    "ANCESTOR='" + ancestor + "' and assetlevel = 'CAR'");
            int yjCount = 0;
            int yjsqnisNullCount = 0;
            if (carJpoSet != null && carJpoSet.count() > 0) {
            	for(int index = 0;index < carJpoSet.count();index++){
            		//根据车厢信息查找车厢下的子级部件，如果直接子级是虚拟件，则继续往下一层查找
            		IJpo carJpo = carJpoSet.getJpo(index);
            		String assetnum = carJpo.getString("ASSETNUM");
            		IJpoSet sysJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET",
                             "ANCESTOR='" + ancestor + "' and parent = '"+assetnum+"'");

            		if(sysJpoSet != null && sysJpoSet.count() > 0){
            			for(int j =0;j<sysJpoSet.count();j++){
            				 // 判断该物料是否是虚拟件
                            IJpo itemJpo = ItemUtil.getItem(sysJpoSet.getJpo(j).getString("itemnum"));
                            if (itemJpo == null || (itemJpo != null && itemJpo.getBoolean("ISIV"))) {
                            	IJpoSet sysXjJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET",
                                        "ANCESTOR='" + ancestor + "' and parent = '"+sysJpoSet.getJpo(j).getString("ASSETNUM")+"'");
	                            	for(int i = 0;i<sysXjJpoSet.count();i++){
	                            		yjCount = yjCount +1;
	                                	if(StringUtil.isNullOrEmpty(sysXjJpoSet.getJpo(i).getString("SQN"))){
	                                		yjsqnisNullCount = yjsqnisNullCount+1;
	                                	}
	                                }
                            }else{
                            	yjCount = yjCount +1;
                            	if(StringUtil.isNullOrEmpty(sysJpoSet.getJpo(j).getString("SQN"))){
                            		yjsqnisNullCount = yjsqnisNullCount+1;
                            	}
                            }
            			}
            		}
            	}
            	 NumberFormat numformat = NumberFormat.getInstance();
                 numformat.setMaximumFractionDigits(2);
                 int sqnCount = yjCount - yjsqnisNullCount;
                 if (sqnCount > 0 && yjCount > 0) {
                     String count = numformat
                             .format(((float) (sqnCount) / (float) yjCount) * 100);
                     jpo.getField("YJPZWZD").setValue(count, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                 } else {
                     jpo.getField("YJPZWZD").setValue(0, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                 }
            } else {
                jpo.getField("YJPZWZD").setValue(0, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            }
        }
	}

	/**
     * @Description 获取配置完整度
     * @throws MroException
     */
    public void getPzwzd(IJpo jpo) throws MroException {

        if (jpo.getField("ASSETLEVEL").getValue().equals("ASSET")) {
            String ancestor = jpo.getString("assetnum");
            // 获取所有子集（不包括车厢节点，assetlevel = 'SYSTEM'）
            IJpoSet allJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET",
                    "ANCESTOR='" + ancestor + "' and assetlevel = 'SYSTEM'");
            if (allJpoSet != null && allJpoSet.count() > 0) {
                // 获取序列号为空的节点
                IJpoSet sqnIsNullJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET",
                        "ANCESTOR='" + ancestor + "' and assetlevel = 'SYSTEM' and sqn is null");
                if (sqnIsNullJpoSet != null && sqnIsNullJpoSet.count() > 0) {
                    int xncount = 0;
                    // 如果存在序列号不为空的数据，则判断不为空的数据中有多少是虚拟件,排除掉
                    for (int index = 0; index < sqnIsNullJpoSet.count(); index++) {
                        IJpo assetJpo = sqnIsNullJpoSet.getJpo(index);
                        if(ItemUtil.getItem(assetJpo.getString("itemnum")) == null || (ItemUtil.getItem(assetJpo.getString("itemnum")) != null
                                && ItemUtil.getItem(assetJpo.getString("itemnum")).getBoolean("ISIV"))){
                            xncount = xncount + 1;
                        }
                    }
                    NumberFormat numformat = NumberFormat.getInstance();
                    numformat.setMaximumFractionDigits(2);
                    if (xncount > 0) {
                        int allcount = allJpoSet.count() - xncount;
                        int sqnisnullCount = sqnIsNullJpoSet.count() - xncount;
                        if (sqnisnullCount > 0) {
                            String count = numformat
                                    .format(((float) (allcount - sqnisnullCount) / (float) allcount) * 100);
                            jpo.getField("PZWZD").setValue(count, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        } else {
                            jpo.getField("PZWZD").setValue(100, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        }
                    } else {
                        String count = numformat
                                .format(((float) (allJpoSet.count() - sqnIsNullJpoSet.count()) / (float) allJpoSet
                                        .count()) * 100);
                        jpo.getField("PZWZD").setValue(count, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    }
                } else {
                    jpo.getField("PZWZD").setValue(100, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
            } else {
                jpo.getField("PZWZD").setValue(0, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            }
        }
    }

}
