package com.glaway.sddq.tools;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 接口工具类
 * 
 * @author hyhe
 * @version [版本号, 2018-3-15]
 * @since [产品/模块版本]
 */
public class IFUtil
{
    
	public final static String TYPE_INPUT = "输入";
    
	public final static String TYPE_OUTPUT = "输出";
    
	// 状态
	public final static String STATUS_SUCCESS = "成功";
    
	public final static String STATUS_FAILURE = "失败";
    
    public final static String FLAG_YES = "1";
    
    public final static String FLAG_NO = "0";
    
	private static Properties userpwdProperties;

	public final static String CCPZ = "ERP_MRO_FACTORCFGIF";// ERP-MRO出厂配置

	public final static String MBOM = "PLM_MBOMTOMROIF";// MRO获取MBOM数据
	public final static String MBOM_U = "PLM_MBOMCHANGETOMROIF";// MBOM变更数据传递至MRO
	public final static String ERP_MRO_JHDIF = "ERP_MRO_JHDIF";// MRO获取ERP交货单
	public final static String ERP_MRO_TOEROCKIF = "ERP_MRO_TOEROCKIF";// MRO领料单触发ERP出库
	public final static String ERP_MRO_JXWORKORDERIF = "ERP_MRO_JXWORKORDERIF";// ERP检修生产订单生成MRO检修工单
	public final static String ERP_MRO_ERP_MRO_SQNIFNO = "ERP_MRO_SQNIFNO";// ERP或者MES系统传递生产完工的产品序列号信息到MRO系统
	public final static String MRO_ERP_GXBG = "MRO_ERO_GXBG";// MRO向ERP检修工序报工
	public final static String MRO_ERP_ZXD = "MRO_ERP_ZXD";// 装箱单点收触发ERP过账
	public final static String MRO_ERP_JKD = "MRO_ERP_JKD";//返修图号变更
	public final static String ERP_MRO_TOEROCKIF_101 = "ERP_MRO_TOEROCKIF_101";// 调拨转库单接收
	public final static String ERP_MRO_TOEROCKIF_201 = "ERP_MRO_TOEROCKIF_201";// MRO领料单触发ERP出库
	public final static String ERP_MRO_TOEROCKIF_201or551 = "ERP_MRO_TOEROCKIF_201or551";// 处置管理领料出库
	public final static String ERP_MRO_TOEROCKIF_202 = "ERP_MRO_TOEROCKIF_202";// MRO退料单触发ERP出库
	public final static String ERP_MRO_TOEROCKIF_261 = "ERP_MRO_TOEROCKIF_261";// MRO计划内领料单触发ERP出库
	public final static String ERP_MRO_TOEROCKIF_Z51 = "ERP_MRO_TOEROCKIF_Z51";// MRO计划外领料单触发ERP出库
	public final static String ERP_MRO_TOEROCKIF_Z52 = "ERP_MRO_TOEROCKIF_Z52";// MRO计划外退料单触发ERP出库
	public final static String ERP_MRO_TOEROCKIF_101JX = "ERP_MRO_TOEROCKIF_101JX";// 检修101过账

	public final static String MDM_MRO_ITEMIF = "MDM_MRO_ITEMIF";// MDM系统接口（物料）
	public final static String MDM_MRO_COMPANIESIF = "MDM_MRO_COMPANIESIF";// MDM系统接口（供应商）
	public final static String MDM_MRO_ORGIF = "MDM_MRO_ORGIF";// MDM系统接口（组织）
	public final static String MDM_MRO_PERSONIF = "MDM_MRO_PERSONIF";// MDM系统接口（人员）
	public final static String MDM_MRO_POSTIF = "MDM_MRO_POSTIF";// MDM系统接口（岗位）
	public final static String MDM_MRO_CUSTIF = "MDM_MRO_CUSTIF";// MDM系统接口（客户）

	public final static String JXMPR_PLANIN = "261";// 计划内领料
	public final static String JXMPR_PLANOUT = "Z51";// 计划外领料
	public final static String JXMPR_PLANOUTTL = "Z52";// 计划外退料
	public final static String PLM_MRO_SOFT = "PLM_MRO_SOFT";// PLM传输软件验证申请单数据向Mro

	public final static String SRM_MRO_SOFT = "SRM_MRO_SOFT";// SRM传输缴库单数据向Mro
	public final static String SRM_MROXSD_SOFT = "SRM_MROXSD_SOFT";// SRM传输送修单数据向Mro
    
    /**
	 * 通过接口获取数据之后，存放到中间表INTERFACEHISTORY中，返回num,数据处理完之后，可以通过num字段处理记录的状态标记等信息
	 * 
	 * @param ifnum
	 *            接口编号
	 * @param content
	 *            接口传递数据内容
	 * @param type
	 *            输入/输出
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
    public static String addIfHistory(String ifnum, String content, String type)
        throws MroException
    {
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("INTERFACEHISTORY", "1=2");
        MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
        MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
        IJpo jpo = jposet.addJpo();
        String num = jpo.getString("NUM");
        jpo.setValue("CONTENT", content);
        jpo.setValue("IFNUM", ifnum);
        jpo.setValue("TYPE", type);
        jposet.save();
        return num;
    }
    
    /**
	 * 更新接口历史表中处理的情况记录
	 * 
	 * @param num
	 * @param status
	 * @param flag
	 * @param errorMsg
	 *            [参数说明]
	 * @throws MroException
	 */
    public static void updateIfHistory(String num, String status, String flag, String errorMsg)
        throws MroException
    {
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("INTERFACEHISTORY", "NUM='" + num + "'");
        if(jposet != null && jposet.count() > 0){
        	IJpo jpo = jposet.getJpo();
        	if(jpo != null){
        		jpo.setValue("STATUS", status);
                jpo.setValue("FLAG", flag);
				jpo.setValue("ERRORMSG", errorMsg);// 输入/输出
                jpo.setValue("DEALTIME",  MroServer.getMroServer().getDate());
                jposet.save();
        	}
        }
    }
    // ----------------------------xlb2019-4-15-------------------
    /**
	 * 更新接口历史表中处理的情况记录
	 * 
	 * @param num
	 * @param transferlineid
	 *            [参数说明]
	 * @throws MroException
	 */
    public static void updatedescriptionIfHistory(String num, String transferlineid)
        throws MroException
    {
        IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("INTERFACEHISTORY", "NUM='" + num + "'");
        if(jposet != null && jposet.count() > 0){
        	IJpo jpo = jposet.getJpo();
        	if(jpo != null){
        		jpo.setValue("description", transferlineid);
                jposet.save();
        	}
        }
    }
 // ----------------------------xlb2019-4-15-------------------
    /**
	 * 通过接口编号获取接口的配置信息
	 * 
	 * @param ifnum
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
    public static IJpo getInterfaceInfo(String ifnum) throws MroException {
    	IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("USERINTERFACE", "IFNUM='" + ifnum + "'");
    	if(jposet.count() > 0){
    		return jposet.getJpo(0);
    	}else{
    		return null;
    	}
    }
    
	        /**
	 * 获取接口连接信息
	 * 
	 * @return [参数说明]
	 */
	public static String getIfServiceInfo(String key) {
		
		if(StringUtils.isEmpty(key)){
			return "";
		}
		try {
			if (userpwdProperties == null) {
				userpwdProperties = PropertiesLoaderUtils
						.loadAllProperties("interface.properties");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userpwdProperties.getProperty(key);
	}
	
	        /**
	 * 判断是否是整数
	 * 
	 * @param str
	 * @return [参数说明]
	 */
	public static boolean isInteger(String str){
		if(StringUtils.isNotEmpty(str)){
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			return pattern.matcher(str).matches();
		}else{
			return false;
		}
	}
	
	        /**
	 * 新增处理记录
	 * 
	 * @param ancestor
	 * @param msg
	 * @param type
	 * @throws MroException
	 *             [参数说明]
	 */
	public static void addDataHistory(String ancestor,String msg,String type) throws MroException{
		
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("assetdatahistory", "1=2");
        MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
        MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
        IJpo jpo = jposet.addJpo();
        jpo.setValue("ancestor", ancestor);
        jpo.setValue("msg", msg);
        jpo.setValue("type", type);
        jposet.save();
		
	}
    
}
