package com.glaway.sddq.material.detectedmater.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 有效性检测清单定时生成任务
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-29]
 * @since  [产品/模块版本]
 */
public class DetectedMaterTask extends BaseStatefulJob
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void execute()
        throws MroException
    {
        // System.out.println("已经进入定时任务方法！！！");
        Date sysdate = MroServer.getMroServer().getDate();
        IJpoSet assetset = MroServer.getMroServer().getJpoSet("ASSET", MroServer.getMroServer().getSystemUserServer());
        //过滤满足条件
        assetset.setQueryWhere("location is not null");
        
        assetset.reset();
        try
        {
            if (!assetset.isEmpty())
            {
                
                List<Map<String, String>> listMap = new ArrayList<Map<String, String>>(); ///
                for (int i = 0; i < assetset.count(); i++)
                {
                    IJpo asset = assetset.getJpo(i);
                    Date productdate = asset.getDate("PRODUCTDATE");//生产日期
                    // String sqn = asset.getString("SQN");
                    String itemnum = asset.getString("ITEMNUM");
                    if (null != asset)
                    {
                        IJpoSet sysitemset =
                            MroServer.getMroServer().getJpoSet("sys_item",
                                MroServer.getMroServer().getSystemUserServer());
                        sysitemset.setQueryWhere("ITEMNUM = '" + itemnum + "'");
                        for (int k = 0; k < sysitemset.count(); k++)
                        {
                            IJpo sysitem = sysitemset.getJpo(k);
                            int cycle = sysitem.getInt("CYCLE");//有效周期
                            
                            //距离当前日期小于等于一个月才能生成检测单
                            if (!"1".equals(asset.getString("ASSETFLAG"))
                                && 30 >= daysBetween(sysdate, productdate, cycle))
                            {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("ITEMNUM", asset.getString("ITEMNUM"));
                                map.put("SQN", asset.getString("sqn"));
                                listMap.add(map);
                            }
                        }
                    }
                }
                if (listMap.size() > 0)
                {
                    MroServer.getMroServer().getCronTaskUserServer().getUserInfo().setDefaultOrg("CRRC");
                    MroServer.getMroServer().getCronTaskUserServer().getUserInfo().setDefaultSite("ELEC");
                    IJpoSet detectedset =
                        MroServer.getMroServer()
                            .getJpoSet("DETECTED", MroServer.getMroServer().getCronTaskUserServer());
                    IJpo detectedjpo = detectedset.addJpo(); ///主表记录
                    
                    IJpoSet detectedmaterset = detectedjpo.getJpoSet("DETECTEDMATER");
                    for (int i = 0; i < listMap.size(); i++)
                    {
                        Map<String, String> map = listMap.get(i);
                        String itemnum = map.get("ITEMNUM");
                        String sqn = map.get("SQN");
                        
                        IJpo detectedmaterjpo = detectedmaterset.addJpo();
                        detectedmaterjpo.setValue("DETECTEDNUM", detectedjpo.getString("DETECTEDNUM"));
                        detectedmaterjpo.setValue("ITEMNUM", itemnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        detectedmaterjpo.setValue("STATUS", "已锁定", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        detectedmaterjpo.setValue("ASSETNUM", sqn, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        
                        ///修改标识 防止重复生成
                        IJpoSet sysitemsettemp =
                            MroServer.getMroServer().getJpoSet("ASSET", MroServer.getMroServer().getSystemUserServer());
                        sysitemsettemp.setUserWhere("ITEMNUM='" + itemnum + "'");
                        sysitemsettemp.reset();
                        
                        sysitemsettemp.getJpo(0).setValue("ASSETFLAG", "Y", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        //sysitemsettemp.getJpo(0).setValue("STATUS", "已锁定", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        sysitemsettemp.save();
                    }
                    detectedset.save();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    //检测时间计算
    private int daysBetween(Date sysdate, Date productdate, int cycle)
        throws ParseException, MroException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sysdate = sdf.parse(sdf.format(sysdate));
        productdate = sdf.parse(sdf.format(productdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(sysdate);
        long time1 = cal.getTimeInMillis();
        
        cal.setTime(productdate);
        long time2 = cal.getTimeInMillis();
        //两个日期相差的天数
        long daysbtw = (time1 - time2) / (1000 * 3600 * 24);
        long j = cycle * 365;
        long i = j - daysbtw;
        return Integer.parseInt(String.valueOf(i));
    }
    
    /*    //生成检测部件清单
        private void scrapOrder(IJpo sysitem, IJpoSet sysitemset)
            throws MroException
        {
            
            IJpoSet detectedmaterset = MroServer.getMroServer().getSysJpoSet("DETECTEDMATER");
            IJpo detectedmater = detectedmaterset.addJpo();
            detectedmater.setValue("ITEMNUM", sysitem.getString("ITEMNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            detectedmater.setValue("STATUS", "已锁定", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            //防止重复生成检测单
            sysitem.setValue("DETECTEDMATERFLAG", "Y", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            detectedmaterset.save();
        }*/
}
