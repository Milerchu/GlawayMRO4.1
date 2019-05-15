package com.glaway.sddq.back.Interface.webservice.qms;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.Interface.webservice.InterfaceUtil;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.Result;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.qms.QmsToMroService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class QmsToMroServiceImp implements QmsToMroService {

	/**
	 * 现场信息存入故障信息表
	 * 
	 * @param qmsXml
	 * @return
	 */
	@Override
	public String toMroFailureLib(String qmsXml) {
		Document doc;
		Result result = new Result();
		String log = "";
		try {
			log = IFUtil.addIfHistory("QMS_MRO_FAILURE", qmsXml,
					IFUtil.TYPE_INPUT);
			doc = DocumentHelper.parseText(qmsXml);
			Element root = doc.getRootElement();
			// QMS_ID
			String qms_id = root.elementText("QMS_ID");
			// 故障发生时的供电区间
			String gdqj = root.elementText("gdqj");
			// 海拔--tms有
			String failaltitude = root.elementText("hb");
			// 路况
			String roadtype = root.elementText("lk");
			// 天气--tms有
			String failweather = root.elementText("tq");
			// 速度
			String failspeed = root.elementText("sd");
			// 工况
			String otpcondition = root.elementText("gk");
			// 故障发生时间
			String faulttime = root.elementText("gzfssj");
			// 故障现象
			String faultdesc = root.elementText("gzxx");
			// 故障后果
			String faultconseq = root.elementText("gzhg");
			// 客户定责
			String faultqualit = root.elementText("gzdx");
			// 处理措施
			String dealmeasure = root.elementText("clbz");
			// 环境温度
			String environmenttmp = root.elementText("wd");
			// 故障工单号
			String qms_num = root.elementText("bdbh");
			// 初步分析
			String prereasonalys = root.elementText("cbyyfx");

			IJpoSet exchangeSet = MroServer.getMroServer().getSysJpoSet(
					"exchangerecord", "QMS_NUM='" + qms_num + "'");

			// IJpoSet workorderSet =
			// MroServer.getMroServer().getSysJpoSet("WORKORDER",
			// "QMS_NUM='"+qms_num+"'");
			if (exchangeSet.count() < 1) {
				result.setMessage("找不到QMS编号为：" + qms_num + "的上下车记录");
				result.setStateCode("403");
				IFUtil.updateIfHistory(log, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "找不到QMS编号为：" + qms_num + "的上下车记录");

				return result.toXmlString();
			}
			IJpoSet failurelibSet = exchangeSet.getJpo()
					.getJpoSet("FAILURELIB");

			IJpo failureJpo = failurelibSet.getJpo();
			if (failureJpo != null) {
				failureJpo.setValue("GDQJ", gdqj, GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAILALTITUDE", failaltitude,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("ROADTYPE", roadtype,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAILWEATHER", failweather,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAILSPEED", failspeed,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("OTPCONDITION", otpcondition,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAULTTIME", faulttime,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAULTDESC", faultdesc,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAULTCONSEQ", faultconseq,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("FAULTQUALIT", faultqualit,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("DEALMEASURE", dealmeasure,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("ENVIRONMENTTMP", environmenttmp,
						GWConstant.P_NOVALIDATION);
				failureJpo.setValue("PREREASONALYS", prereasonalys,
						GWConstant.P_NOVALIDATION);

				IJpo tmsData = failureJpo.getJpoSet("TMSDATA").getJpo();
				if (tmsData != null) {

					tmsData.setValue("ELEVATION", failaltitude,
							GWConstant.P_NOVALIDATION);
					tmsData.setValue("TRAINSPPED", failspeed,
							GWConstant.P_NOVALIDATION);
					tmsData.setValue("WEATHER", failweather,
							GWConstant.P_NOVALIDATION);
				}

				failurelibSet.save();
				IFUtil.updateIfHistory(log, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "操作成功");
			} else {
				IFUtil.updateIfHistory(log, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "操作失败，故障信息记录不存在");
			}
		} catch (Exception e) {
			result.setMessage("操作失败：" + e.getMessage());
			result.setStateCode("500");
			try {
				IFUtil.updateIfHistory(log, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "操作失败：" + e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return result.toXmlString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toMroRepairInfo(String qmsXml) {
		Result result = new Result();
		String log = "";
		try {
			log = IFUtil.addIfHistory("QMS_MRO_REPAIR", qmsXml,
					IFUtil.TYPE_INPUT);
		} catch (MroException e1) {
			e1.printStackTrace();
		}

		try {
			Document doc = DocumentHelper.parseText(qmsXml);
			Element root = doc.getRootElement();
			// 表单编号
			String bdbh = root.elementText("bdbh");
			try {
				// 如果有已经存在的qms数据，删除后重新录入
				IJpoSet repairinfoSet = MroServer.getMroServer().getSysJpoSet(
						"QMSREPAIRINFO", "QMSREPAIRNUM='" + bdbh + "'");
				IJpo repairinfo = null;
				if (repairinfoSet != null && repairinfoSet.count() > 0) {

					repairinfo = repairinfoSet.getJpo(0);

					// 删除子表数据
					IJpoSet repairconfigSet = MroServer.getMroServer()
							.getSysJpoSet("QMSREPAIRCONFIG",
									"QMSREPAIRNUM='" + bdbh + "'");
					repairconfigSet.deleteAll();
					repairconfigSet.save();

				} else {

					throw new MroException("QMS维修主表不存在！");

				}

				repairinfo.setValue("QMSREPAIRNUM", bdbh);
				// 维修信息录入人
				String wxxxlrr = root.elementText("wxxxlrr");
				repairinfo.setValue("REPORTER", wxxxlrr);
				// 信息录入时间
				String xxlrsj = root.elementText("xxlrsj");
				repairinfo.setValue("REPORTTIME", xxlrsj);
				// 返修类型(维修子表)
				String fxlx = root.elementText("fxlx");
				repairinfo.setValue("REPAIRTYPE", fxlx);
				// 单据号？
				String djh = root.elementText("djh");
				repairinfo.setValue("ORDERNUM", djh);
				// 返修订单号
				String fxddh = root.elementText("fxddh");
				repairinfo.setValue("REPAIRORDERNUM", fxddh);
				// 返修结论
				String fxjl = root.elementText("fxjl");
				repairinfo.setValue("REPAIRCONCLUSION", fxjl);
				// 返修品接收日期
				String fxpjsrq = root.elementText("fxpjsrq");
				repairinfo.setValue("RECIVEDATE", fxpjsrq);
				// 检查员
				String jcy = root.elementText("jcy");
				repairinfo.setValue("CHECKER", jcy);
				// 维修处理人
				String gzclr = root.elementText("gzclr");
				repairinfo.setValue("REPAIRER", gzclr);
				// 修复品发出时间
				String xfpfcsj = root.elementText("xfpfcsj");
				repairinfo.setValue("SENDDATE", xfpfcsj);
				// 流程状态
				String lczt = root.elementText("lczt");
				repairinfo.setValue("STATUS", lczt);
				// 故障品序号
				String gzpxh = root.elementText("gzpxh");
				repairinfo.setValue("REPAIRMENTSN", gzpxh);
				// 送修单号
				String sxdh = root.elementText("sxdh");
				repairinfo.setValue("REPAIRNUM", sxdh);
				// 送修单行号
				String sxdhh = root.elementText("sxdhh");
				repairinfo.setValue("REPAIRROWNUM", sxdhh);
				// 判定结果
				String pdjg = root.elementText("pdjg");
				repairinfo.setValue("RESULT", pdjg);
				// 返修数量
				String fxsl = root.elementText("fxsl");
				repairinfo.setValue("REPAIRCOUNT", fxsl);
				// 报废数量
				String bfsl = root.elementText("bfsl");
				repairinfo.setValue("WASTECOUNT", bfsl);
				// 返售后数量
				String fshsl = root.elementText("fshsl");
				repairinfo.setValue("RETURNCOUNT", fshsl);
				// 故障模式
				String gzms = root.elementText("gzms");
				repairinfo.setValue("FAULTMODE", gzms);

				// 测试信息子节点
				Element csxx = root.element("csxxtable");
				if (csxx != null) {
					List<Element> csxxlist = csxx.elements();
					for (Element sub : csxxlist) {
						// 外观检查结果
						String csxxwgjc = sub.elementText("wgjc");
						repairinfo.setValue("APPEARRESULT", csxxwgjc);
						// 外观检查附件
						String csxxattachment3 = sub.elementText("attachment3");
						repairinfo
								.setValue("APPEARATTACHMENT", csxxattachment3);
						// 耐压测试结果
						String csxxnycs = sub.elementText("nycs");
						repairinfo.setValue("PRESSURERESULT", csxxnycs);
						// 功能测试结果
						String csxxgncs = sub.elementText("gncs");
						repairinfo.setValue("FUNCRESULT", csxxgncs);
						// 功能测试附件
						String csxxattachment5 = sub.elementText("attachment5");
						repairinfo.setValue("FUNCATTACHMENT", csxxattachment5);
						// 故障处理方案
						String csxxgzclfa = sub.elementText("gzclfa");
						repairinfo.setValue("DEALPLAN", csxxgzclfa);
					}
				}

				repairinfoSet.save();

			} catch (MroException e) {
				try {
					IFUtil.updateIfHistory(log, IFUtil.STATUS_FAILURE,
							IFUtil.FLAG_NO, "QMS维修主表信息保存失败");
				} catch (MroException e1) {
					e1.printStackTrace();
				}
				result.setMessage("QMS维修主表信息保存失败");
				result.setStateCode("402");
				e.printStackTrace();
				return result.toXmlString();
			}

			// 维修子表子节点
			Element wxpz = root.element("wxpztable");
			// if(wxpz==null){
			// result.setMessage("未能解析获得QMS维修配置信息");
			// result.setStateCode("405");
			// return result.toXmlString();
			// }
			if (wxpz != null) {
				List<Element> wxpzlist = wxpz.elements();
				try {
					IJpoSet repairconfigSet = MroServer.getMroServer()
							.getJpoSet(
									"QMSREPAIRCONFIG",
									MroServer.getMroServer()
											.getSystemUserServer());
					String QMS_NUM = "";
					for (Element sub : wxpzlist) {
						IJpo repairconfig = repairconfigSet.addJpo();

						// 故障子项名称
						String wxpzgzzxmc = sub.elementText("gzzxmc");
						repairconfig.setValue("FAULTITEMNAME", wxpzgzzxmc);
						// 故障子项图号(故障子项物料编码)
						String wxpzgzzxwlbm = sub.elementText("gzzxwlbm");
						repairconfig.setValue("FAULTITEMNUM", wxpzgzzxwlbm);
						// 故障子项序列号
						String wxpzgzzxxh = sub.elementText("gzzxxh");
						repairconfig.setValue("FAULTITEMSN", wxpzgzzxxh);
						// 故障子项位号
						String wxpzgzzxwh = sub.elementText("gzzxwh");
						repairconfig.setValue("FAULTITEMPOSITION", wxpzgzzxwh);
						// 故障子项软件版本号
						String wxpzgzzxrjbbh = sub.elementText("gzzxrjbbh");
						repairconfig
								.setValue("FAULTITEMVERSION", wxpzgzzxrjbbh);
						// 更换件物料描述
						String wxpzghgzzxmc = sub.elementText("ghgzzxmc");
						repairconfig.setValue("NEWITEMNAME", wxpzghgzzxmc);
						// 更换件序列号
						String wxpzgzzxghxh = sub.elementText("gzzxghxh");
						repairconfig.setValue("NEWITEMSN", wxpzgzzxghxh);
						// 表单编号
						String wxpzbdbh = sub.elementText("bdbh");
						repairconfig.setValue("QMSSERIERSNUM", wxpzbdbh);
						// 更换数量
						String wxpzghsl = sub.elementText("ghsl");
						repairconfig.setValue("EXCHANGECOUNT", wxpzghsl);
						// 所属事件单编号
						// String wxpzssxcgz = sub.elementText("ssxcgz");
						repairconfig.setValue("QMSREPAIRNUM", bdbh);
						// 更换件软件编号
						String wxpzghjrjbh = sub.elementText("ghjrjbh");
						repairconfig.setValue("NEWITEMSOFTNUM", wxpzghjrjbh);
						// 更换件软件版本号
						String wxpzghjrjbbh = sub.elementText("ghjrjbbh");
						repairconfig.setValue("NEWITEMVERSION", wxpzghjrjbbh);
						// 返修类型
						String wxpzfxlx = sub.elementText("fxlx");
						repairconfig.setValue("REPAIRTYPE", wxpzfxlx);

						QMS_NUM = bdbh;
					}
					repairconfigSet.save();
					// 更新配置
					InterfaceUtil.GxPzForQms(QMS_NUM);
				} catch (MroException e) {
					try {
						IFUtil.updateIfHistory(log, IFUtil.STATUS_FAILURE,
								IFUtil.FLAG_NO, "QMS维修配置子表保存失败");
					} catch (MroException e1) {
						e1.printStackTrace();
					}
					result.setMessage("QMS维修配置子表保存失败");
					result.setStateCode("403");
					e.printStackTrace();
					return result.toXmlString();
				}
			}

			try {
				IFUtil.updateIfHistory(log, IFUtil.STATUS_SUCCESS,
						IFUtil.FLAG_YES, "操作成功");
			} catch (MroException e) {
				e.printStackTrace();
			}
		} catch (DocumentException e) {
			try {
				IFUtil.updateIfHistory(log, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_NO, "XML解析失败");
			} catch (MroException e1) {
				e1.printStackTrace();
			}
			result.setMessage("XML解析失败");
			result.setStateCode("401");
			e.printStackTrace();
			return result.toXmlString();
		}
		return result.toXmlString();
	}

}
