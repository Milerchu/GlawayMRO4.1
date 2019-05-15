package com.glaway.sddq.tools;

/**
 * @ClassName SddqConstant
 * @Description 定义系统中的自定义常量
 * @author public2175
 * @Date 2018-7-31 下午4:18:00
 * @version 1.0.0
 */
public class SddqConstant {

	// 交车产品的交车状态
	public final static String JC_F_STATUS = "未上车";
	public final static String JC_T_STATUS = "已上车";

	// 交车工单的状态
	public final static String JC_STATUS_CG = "草稿";
	public final static String JC_STATUS_PG = "已派工";
	public final static String JC_STATUS_DJC = "待交车";
	public final static String JC_STATUS_WC = "已完成";

	// 检修工单的状态
	public final static String JX_STATUS_CG = "草稿";
	public final static String JX_STATUS_KG = "已开工";
	public final static String JX_STATUS_WC = "已完成";

	// 调度员
	public final static String JX_DDY_QD = "检修基地调度-青岛";

	// 检修工单的工序的报工状态
	public final static String JX_GX_STATUS_NO = "未报工";
	public final static String JX_GX_STATUS_YES = "报工成功";
	public final static String JX_GX_STATUS_ERROR = "报工失败";
	public final static String JX_GX_STATUS_QX = "取消报工";
	public final static String JX_GX_MSG_WARN = "报工警告";

	public final static String JX_GX_BG_YES = "I"; // 报工
	public final static String JX_GX_BG_NO = "D";// 取消报工

	public final static String DOWN_JX = "检修下车";

	// 返回信息
	public final static String ISNULL = "传递数据不能为空";
	public final static String SQN_ISNULL = "产品序列号不能为空";

	// 上下车周转件偶换件
	public final static String SXC_ZZ = "检修周转件";
	public final static String SXC_OH = "检修偶换件";
	public final static String SXC_OH_JC = "交车工单";

	public final static String SXC_GZ = "故障";
	public final static String SXC_GAIZ = "改造";
	public final static String SXC_YZ = "验证";
	public final static String SXC_JX = "检修";
	public final static String SXC_DCL = "待处理";
	// 待上车
	public final static String NO_UP_CAR_STATUS = "待上车";
	public final static String UP_CAR_STATUS = "已上车";

	// 为车厢节点设置一个默认的ITEMNUM
	public final static String CAR_ITEMNUM = "caritemnum";
	// 为车厢节点设置一个默认的ASSETLEVEL
	public final static String CAR_ASSETLEVEL = "CAR";

	// 产品SBOM的状态
	public final static String ASSET_MODEL_STATUS_KY = "可用";
	public final static String ASSET_MODEL_STATUS_BG = "变更";
	public final static String ASSET_MODEL_STATUS_SD = "锁定";

	// 整车SBOM的状态
	public final static String ASSET_CS_STATUS_CG = "草稿";
	public final static String ASSET_CS_STATUS_KY = "可用";
	public final static String ASSET_CS_STATUS_BG = "变更";
	public final static String ASSET_CS_STATUS_SD = "锁定";

	// 服务工单类型
	public final static String SO_TYPE_TSJC = "调试交车";
	public final static String SO_TYPE_HWDC = "货物点收";
	public final static String SO_TYPE_XCZB = "新车整备";
	public final static String SO_TYPE_JSZC = "现场技术支持";
	public final static String SO_TYPE_GCTC = "跟车添乘";

	// 初始配置的异常信息
	public final static String MSG_INFO_NOCHILREN = "在SAP中查询不到结构信息";
	public final static String MSG_INFO_NOSQN = "新品入库时，系统中查不到该序列号";
	// 初始配置的来源信息
	public final static String FROMSOURCE_MES = "MES推送（制造中心）";
	public final static String FROMSOURCE_XP = "入库新建";
	public final static String FROMSOURCE_ZC = "装车新建";

	// 工单状态
	public final static String WO_STATUS_CG = "草稿";
	public final static String WO_STATUS_YPG = "已派工";
	public final static String WO_STATUS_CXPG = "重新派工";
	public final static String WO_STATUS_CLZ = "处理中";
	public final static String WO_STATUS_DSH = "待审核";
	public final static String WO_STATUS_KGYSH = "库管员审核";
	public final static String WO_STATUS_ZZKGYSH = "专职库管员审核";
	public final static String WO_STATUS_ZZKGYBH = "专职库管员驳回";
	public final static String WO_STATUS_JSZGSH = "技术主管审核";
	public final static String WO_STATUS_SJPD = "审计判断";
	public final static String WO_STATUS_SJSH = "审计审核";
	public final static String WO_STATUS_SHBH = "审核驳回";
	public final static String WO_STATUS_GB = "关闭";
	public final static String WO_STATUS_KGYBH = "库管员驳回";
	public final static String WO_STATUS_JSZGBH = "技术主管驳回";
	public final static String WO_STATUS_GQ = "挂起";

	// 改造配置变更列表的状态
	public final static String GZPZ_STATUS_YCL = "更新成功";
	public final static String GZPZ_STATUS_CLSB = "更新失败";
	public final static String GZPZ_STATUS_SQNNULL = "产品序列号为空";

	// 故障记录处理方式
	public final static String FAIL_DEALMETHOD_HJCL = "换件处理";
	public final static String FAIL_DEALMETHOD_YJXF = "原件修复";
	public final static String FAIL_DEALMETHOD_FWCQ = "复位重启";
	public final static String FAIL_DEALMETHOD_GXRJ = "更新软件";
	public final static String FAIL_DEALMETHOD_JKSY = "监控使用";
	public final static String FAIL_DEALMETHOD_RJWT = "软件问题";
	public final static String FAIL_DEALMETHOD_CHCL = "串换处理";
	public final static String FAIL_DEALMETHOD_CHANDGH = "串换、故障更换处理";

	// 外网一级域名
	public final static String FIRST_DOMAINNAME = "mrotst";

	// 项目角色
	public final static String PRJ_ROLE_CPXSHJL = "产品线售后经理";
	public final static String PRJ_ROLE_SHXMJL = "售后项目经理";
	public final static String PRJ_ROLE_SHJHJL = "售后计划经理";
	public final static String PRJ_ROLE_SHJSJL = "售后技术经理";
	public final static String PRJ_ROLE_SHZLJL = "售后质量经理";
	public final static String PRJ_ROLE_SHPXJL = "售后培训经理";
	public final static String PRJ_ROLE_XCFWJL = "现场服务经理";
	public final static String PRJ_ROLE_XCFWGCS = "现场服务工程师";
	public final static String PRJ_ROLE_XCJXJL = "现场检修经理";
	public final static String PRJ_ROLE_XCJXGCS = "现场检修工程师";
	public final static String PRJ_ROLE_SHPJJL = "售后配件经理";

	// 配置变更申请单
	// 变更类型
	public final static String PZBG_TYPE_CS = "车上配置";
	public final static String PZBG_TYPE_KC = "库存配置";
	public final static String PZBG_TYPE_QT = "其他";

	// 下层结构信息
	public final static String PZBG_JG_WU = "无下层结构";
	public final static String PZBG_JG_ZQ = "下层结构准确";
	public final static String PZBG_JG_CW = "下层结构错误";

	// 状态
	public final static String PZBG_STATUS_CG = "草稿";
	public final static String PZBG_STATUS_DSH = "待审核";
	public final static String PZBG_STATUS_BHXG = "驳回修改";
	public final static String PZBG_STATUS_WC = "审核通过";
	public final static String PZBG_STATUS_QR = "已确认";
	// 配置变更申请中的配置管理员人员组
	public final static String PZBG_PZGLY = "100239";
	public final static String PZBG_JS = "技术主管";
	public final static String PZBG_BT = "配置变更时，产品序列号已存在";

	// 检修交货单状态
	public final static String JXJHD_DHQ = "待获取";
	public final static String JXJHD_YHQ = "已获取";
	public final static String JXJHD_YJH = "已交货";
	public final static String JXJHD_JHCG = "交货成功";

	// 检修工单完成之后，ERP入库操作
	public final static String JX_ERP_RK = "已入库";

	// 串换类型
	public final static String SWAP_SAMETRAIN = "同车互换";
	public final static String SWAP_DIFFTRAIN = "两车互换";

	// 故障品处置方式
	public final static String FAIL_DEALMODE_BASEREPAIR = "返回本部维修";
	public final static String FAIL_DEALMODE_PRODREPAIR = "返回厂商维修";
	public final static String FAIL_DEALMODE_RETENTION = "不返修";

}
