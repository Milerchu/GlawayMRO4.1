package com.glaway.mro.system.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.glaway.mro.util.StringUtil;

public final class AppProductMap
{   
    public static final AppProductMap INSTANCE = new AppProductMap();
    
    private HashMap<String, String> appProductMap = null;
    
    private HashMap<String, String> prodFeatureMap = null;
    
    private String[] featureNames = null;
    
    private AppProductMap()
    {
        
        appProductMap = new HashMap<String, String>();
        
        // 株洲所项目模块 --start
        appProductMap.put("SBOMCONFIG", "Glaway MRO FSS");	// 配置管理-部件SBOM管理
        appProductMap.put("ZCSBOM", "Glaway MRO FSS");		// 配置管理-整车SBOM管理
        appProductMap.put("SSCONFIG", "Glaway MRO FSS");	// 配置管理-初始配置管理
        appProductMap.put("ZCCONFIG", "Glaway MRO FSS");	// 配置管理-装车配置管理
        appProductMap.put("CXCONFIG", "Glaway MRO FSS");	// 配置管理-车下配置管理
        appProductMap.put("SOFTCONFIG", "Glaway MRO FSS");	// 配置管理-软件配置库
        
        appProductMap.put("PRJSETUP", "Glaway MRO FSS");	// 服务管理-项目立项
        appProductMap.put("MSTONETMP", "Glaway MRO FSS");	// 服务管理-里程碑模版
        appProductMap.put("SERVPLAN", "Glaway MRO FSS");	// 服务管理-服务计划
        appProductMap.put("SERVORDER", "Glaway MRO FSS");	// 服务管理-服务工单
        appProductMap.put("FAILUREORD", "Glaway MRO FSS");	// 服务管理-故障工单
        appProductMap.put("FAULTMANA", "Glaway MRO FSS");	// 服务管理-故障管理
        appProductMap.put("FLTCLSFR", "Glaway MRO FSS");	// 服务管理-故障字典
        appProductMap.put("TRCHECKORD", "Glaway MRO FSS");	// 服务管理-TR检查单
        appProductMap.put("TRNSNOTICE", "Glaway MRO FSS");	// 服务管理-改造通知单
        appProductMap.put("TRANSPLAN", "Glaway MRO FSS");	// 服务管理-改造计划
        appProductMap.put("TRANSORDER", "Glaway MRO FSS");	// 服务管理-改造工单
        appProductMap.put("VALIREBILL", "Glaway MRO FSS");	// 服务管理-验证申请单
        appProductMap.put("VALIPLAN", "Glaway MRO FSS");	// 服务管理-验证计划
        appProductMap.put("VALIORDER", "Glaway MRO FSS");	// 服务管理-验证工单
        appProductMap.put("TROUBLEMG", "Glaway MRO FSS");	// 服务管理-项目问题跟踪
        appProductMap.put("CHECKORDER", "Glaway MRO FSS");	// 服务管理-检查单
        
        appProductMap.put("JXRULE", "Glaway MRO FSS");		// 检修管理-检修规程管理
        appProductMap.put("JOBBOOK", "Glaway MRO FSS");		// 检修管理-标准作业指导书
        appProductMap.put("JXSCHEME", "Glaway MRO FSS");	// 检修管理-检修方案
        appProductMap.put("YEARPLAN", "Glaway MRO FSS");	// 检修管理-年度计划
        appProductMap.put("MONTHPLAN", "Glaway MRO FSS");	// 检修管理-月度计划
        appProductMap.put("JXTASKORDE", "Glaway MRO FSS");	// 检修管理-检修工单
        appProductMap.put("JCTASKORDE", "Glaway MRO FSS"); 	// 检修管理-交车工单
        appProductMap.put("JXPROBLEM", "Glaway MRO FSS");	// 检修管理-检修问题
        
        appProductMap.put("ASSETCAT", "Glaway MRO FSS");	// 物资管理-分类
        appProductMap.put("ITEM", "Glaway MRO FSS");		// 物资管理-物料管理
        appProductMap.put("MATERREQ", "Glaway MRO FSS");	// 物资管理-配件申请
        appProductMap.put("DEVICEEQU", "Glaway MRO FSS");	// 物资管理-工具设备管理        
        appProductMap.put("STOREROOM", "Glaway MRO FSS");	// 物资管理-库房管理
        appProductMap.put("TOOLAPPLY", "Glaway MRO FSS");	// 物资管理-工具设备申请
        appProductMap.put("TOOLINSP", "Glaway MRO FSS");	// 物资管理-工具送检单
        appProductMap.put("TOOLTRANS", "Glaway MRO FSS");	// 物资管理-工具交接单
        appProductMap.put("TRANSFER", "Glaway MRO FSS");	// 物资管理-调拨单
        appProductMap.put("ITEMREQ", "Glaway MRO FSS");		// 物资管理-领料单
        appProductMap.put("SXTRANSFER", "Glaway MRO FSS");	// 物资管理-送修单
        appProductMap.put("WDR", "Glaway MRO FSS");			// 物资管理-处置管理
        appProductMap.put("ZXTRANSFER", "Glaway MRO FSS");	// 物资管理-装箱单
        appProductMap.put("INVENTORY", "Glaway MRO FSS");	// 物资管理-库存管理
        appProductMap.put("JKTRANSFER", "Glaway MRO FSS");	// 物资管理-缴库单
        appProductMap.put("FOREIGNFH", "Glaway MRO FSS");	// 物资管理-海外配件发货
        appProductMap.put("FREASSET", "Glaway MRO FSS");	// 物资管理-故障件返修 
        appProductMap.put("CONVERTLOC", "Glaway MRO FSS");	// 物资管理-调拨转库单
        appProductMap.put("TRANSITEM", "Glaway MRO FSS");	// 物资管理-改造物料管理
        appProductMap.put("DETECTED", "Glaway MRO FSS");	// 物资管理-有效性检测管理
        appProductMap.put("PJBORROW", "Glaway MRO FSS");	// 物资管理-配件借用管理
        
        
        appProductMap.put("DEPT", "Glaway MRO FSS");		// 基础数据-部门管理
        appProductMap.put("POSTINFO", "Glaway MRO FSS");	// 基础数据-岗位信息
        appProductMap.put("PERSONINFO", "Glaway MRO FSS");	// 基础数据-人员信息
        appProductMap.put("CUSTINFOMG", "Glaway MRO FSS");	// 基础数据-客户资料管理
        appProductMap.put("REVISIT", "Glaway MRO FSS");		// 基础数据-客户走访管理
        appProductMap.put("MODEL", "Glaway MRO FSS");		// 基础数据-车型管理
        appProductMap.put("COMPANY", "Glaway MRO FSS");		// 基础数据-供应商管理
        
        appProductMap.put("IMPIFACE", "Glaway MRO FSS");	// 后台管理-数据导入
        appProductMap.put("USERIF", "Glaway MRO FSS");		// 后台管理-接口管理
        appProductMap.put("MSGCONFIG", "Glaway MRO FSS");	// 后台管理-消息配置
        appProductMap.put("MSGMANAGE", "Glaway MRO FSS");	// 后台管理-消息管理
        appProductMap.put("MROPRO", "Glaway MRO FSS");		// 后台管理-系统问题记录
        // 株洲所项目模块 --end
        
        // 首页挂在foundation上，license授权时，需要加大对此模块的访问授权
        appProductMap.put("STARTCNTR", "Glaway MRO Foundation");
        appProductMap.put("ROLE", "Glaway MRO Foundation");
        appProductMap.put("SECURGROUP", "Glaway MRO Foundation");
        appProductMap.put("ACTION", "Glaway MRO Foundation");
        appProductMap.put("CRAFT", "Glaway MRO Foundation");
        appProductMap.put("CONFIGUR", "Glaway MRO Foundation");
        appProductMap.put("ASSETCAT", "Glaway MRO Foundation");
        appProductMap.put("MULTISITE", "Glaway MRO Foundation");
        appProductMap.put("BBOARD", "Glaway MRO Foundation");
        appProductMap.put("SCTEMPLATE", "Glaway MRO Foundation");
        appProductMap.put("AUDITACT", "Glaway MRO Foundation");
        appProductMap.put("AUDITCFG", "Glaway MRO Foundation");
        appProductMap.put("DESIGNER", "Glaway MRO Foundation");
        appProductMap.put("LOGGING", "Glaway MRO Foundation");
        appProductMap.put("PROPMAINT", "Glaway MRO Foundation");
        appProductMap.put("PERSON", "Glaway MRO Foundation");
        appProductMap.put("WORKFLOW", "Glaway MRO Foundation");
        appProductMap.put("WFMANAGE", "Glaway MRO Foundation");
        appProductMap.put("USER", "Glaway MRO Foundation");
        appProductMap.put("DOMAINADM", "Glaway MRO Foundation");
        appProductMap.put("DEPT", "Glaway MRO Foundation");
        appProductMap.put("AUDITLOG", "Glaway MRO Foundation");
        appProductMap.put("PERSONGR", "Glaway MRO Foundation");
        appProductMap.put("CRONTASK", "Glaway MRO Foundation");
        appProductMap.put("UPDATEPASS", "Glaway MRO Foundation");
        appProductMap.put("REPORT", "Glaway MRO Foundation");
        appProductMap.put("REPORTCFG", "Glaway MRO Foundation");
        
        appProductMap.put("MIGRATE", "Glaway MRO Foundation");
        appProductMap.put("CRMANAGE", "Glaway MRO Foundation");
        appProductMap.put("KPIMANAGE", "Glaway MRO Foundation");
        appProductMap.put("IMPIFACE", "Glaway MRO Foundation");
        appProductMap.put("SYSLANG", "Glaway MRO Foundation");
        
        prodFeatureMap = new HashMap<String, String>();
        prodFeatureMap.put("Glaway MRO Foundation", "MRO");
        prodFeatureMap.put("Glaway MRO FSS", "MRO FSS");
        prodFeatureMap.put("Glaway MRO SPM", "MRO SPM");
        prodFeatureMap.put("Glaway MRO AMS", "MRO AMS");
        
        featureNames = new String[] {"MRO", "MRO FSS", "MRO SPM", "MRO AMS"};
    }
    
    public String getProductName(String appName)
    {
        if (StringUtil.isStrEmpty(appName))
        {
            return null;
        }
        return appProductMap.get(appName.toUpperCase());
    }
    
    public String getFeatureName(String productName)
    {
        if (StringUtil.isStrEmpty(productName))
        {
            return null;
        }
        return prodFeatureMap.get(productName);
    }
    
    public String getProductNameByFeature(String featureName)
    {
        if (StringUtil.isStrEmpty(featureName))
        {
            return null;
        }
        Iterator<Entry<String, String>> it = prodFeatureMap.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<String, String> e = it.next();
            if (StringUtil.isEqualIgnoreCase(e.getValue(), featureName))
            {
                return e.getKey();
            }
        }
        return null;
    }
    
    public String[] getFeatureNames()
    {
        return featureNames;
    }
}
