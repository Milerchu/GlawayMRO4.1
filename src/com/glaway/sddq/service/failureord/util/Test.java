package com.glaway.sddq.service.failureord.util;

import java.io.IOException;

import com.alibaba.druid.support.json.JSONUtils;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.ibm.json.java.JSONObject;

public class Test
{
    
    /**
     * <功能描述>
     * @param args [参数说明]
     * 
     */
    public static void main(String[] args)
    {
        JSONObject parameter1 = new JSONObject();
        parameter1.put("userID", "471205118");
        String cmodel = "1";
        String carno = "0634";
        JSONObject parameter2 = new JSONObject();
        parameter2.put("train_type", cmodel);
        parameter2.put("train_num", carno);
        parameter2.put("f_time", "20171211202259");
        parameter1.put("dataParameter", parameter2);
        JSONUtils.toJSONString(parameter1);
        System.out.println(JSONUtils.toJSONString(parameter1));
        System.out.println(parameter1.toString());
    }
    
    public static void test()
    {
        JSONObject dataparameter = new JSONObject();
        dataparameter.put("abc_id", "255");
        dataparameter.put("date_time", "171020132654");
        dataparameter.put("en_temp1", "29");
        dataparameter.put("kilometer", "1611014");
        dataparameter.put("latitude", "28.505");
        dataparameter.put("longitude", "115.946");
        dataparameter.put("odometer", "11498.1");
        dataparameter.put("real_cross", "12");
        dataparameter.put("real_speed", "116");
        dataparameter.put("tcu_time", "171020132654");
        dataparameter.put("total_weight", "984");
        dataparameter.put("time", "171020132654");
        dataparameter.put("train_fun", "1");
        dataparameter.put("train_id", "K1019");
        dataparameter.put("train_level", "11.4");
        dataparameter.put("train_num", "0534");
        dataparameter.put("train_op_mode", "00101");
        dataparameter.put("train_real_speed", "114");
        dataparameter.put("train_set_speed", "114");
        dataparameter.put("train_type", "2");
        System.out.println("数据："+dataparameter.toString());
        addJpo(dataparameter.toString());
    }
    
    public static void addJpo(String dataParameter)
    {
        JSONObject gzdata;
        try
        {
            gzdata = JSONObject.parse(dataParameter);
            String abc_id = (String)gzdata.get("abc_id");//-A/B/C车标示
            String date_time = (String)gzdata.get("date_time");//日期时间
            String en_temp1 = (String)gzdata.get("en_temp1");//环温1
            String kilometer = (String)gzdata.get("kilometer");//公里标
            String latitude = (String)gzdata.get("latitude");//度的整数部分
            String odometer = (String)gzdata.get("odometer");//运行里程
            String real_cross = (String)gzdata.get("real_cross");//实际交路号
            String real_speed = (String)gzdata.get("real_speed");//实速
            String tcu_time = (String)gzdata.get("tcu_time");//TCU实时时钟
            String longitude = (String)gzdata.get("longitude");//度的整数部分
            String time = (String)gzdata.get("time");//时间
            String total_weight = (String)gzdata.get("total_weight");//总重
            String train_fun = (String)gzdata.get("train_fun");//本/补、客/货
            String train_id = (String)gzdata.get("train_id");//车次标识符
            String train_level = (String)gzdata.get("train_level");//机车当前级位
            String train_num = (String)gzdata.get("train_num");//车号
            String train_op_mode = (String)gzdata.get("train_op_mode");//机车工况
            String train_real_speed = (String)gzdata.get("train_real_speed");//机车实际速度
            String train_set_speed = (String)gzdata.get("train_set_speed");//机车设定速度
            String train_type = (String)gzdata.get("train_type");//车型
            if (train_type != null && train_type.equals("1"))
            {
                train_type = "HXD1C机车";    
            }
            if (train_type != null && train_type.equals("2"))
            {
                train_type = "HXD1D机车";    
            }
            try
            {
                IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("BDSYNCINTERFACE", "1=2");
                IJpo jpo = jposet.addJpo();
                jpo.setValue("abc_id", abc_id, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("date_time", date_time, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("en_temp1", en_temp1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("kilometer", kilometer, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("latitude", latitude, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("longitude", longitude, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("odometer", odometer, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("real_cross", real_cross, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("real_speed", real_speed, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("time", time, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("total_weight", total_weight, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("tcu_time", tcu_time, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_fun", train_fun, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_id", train_id, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_level", train_level, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_num", train_num, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_op_mode", train_op_mode, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_real_speed", train_real_speed, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_set_speed", train_set_speed, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("ORGID", "CRRC", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("SITEID", "ELEC", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                jpo.setValue("train_type", train_type, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                
                jposet.save();
            }
            catch (MroException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void addMroDb(IJpo curjpo){
        IJpoSet bdsyncifaceset;
        try
        {
            bdsyncifaceset = MroServer.getMroServer().getJpoSet("BDSYNCINTERFACE", MroServer.getMroServer().getSystemUserServer());
            IJpo failurelib = curjpo;
            if(!bdsyncifaceset.isEmpty()){
                IJpo bdsynciface = bdsyncifaceset.getJpo();
                failurelib.setValue("ENVIRONMENTTMP", bdsynciface.getString("EN_TEMP1"));
                failurelib.setValue("LONGITUDE", bdsynciface.getString("LONGITUDE"));
                failurelib.setValue("LATITUDE", bdsynciface.getString("LATITUDE"));
                failurelib.setValue("FAILSPEED", bdsynciface.getString("REAL_SPEED"));
                failurelib.setValue("THELEVEL", bdsynciface.getString("TRAIN_LEVEL"));
                failurelib.setValue("OTPCONDITION", bdsynciface.getString("TRAIN_OP_MODE"));
                failurelib.setValue("KILOMETER", bdsynciface.getString("KILOMETER"));
                failurelib.setValue("TOTAL_WEIGHT", bdsynciface.getString("TOTAL_WEIGHT"));
            }
            bdsyncifaceset.save();
        }
        catch (MroException e){
            e.printStackTrace();
        }
    }
}
