package com.glaway.sddq.tools.ConTask;

import java.util.Date;
import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.sddq.tools.EwsEmail;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 改造计划审核报警（12）
 * 
 * @author zzx
 * @version [版本号, 2018-8-30]
 * @since [产品/模块版本]
 */
public class TransplanshbjConTask extends BaseStatefulJob {
	public void execute() throws MroException {
		// TODO Auto-generated method stub
		super.execute();
		System.out.println("已经进入定时任务方法！！！");
		// zzx add start 20181028
		// 筛选出系统属性中名称为EWSMAILS.GZPLANSHBJ的数据
		IJpoSet syspropertiesSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_PROPERTIES");
		syspropertiesSet.setUserWhere("PROPNAME='EWSMAILS.GZPLANSHBJ'");
		syspropertiesSet.reset();
		String inputvalueTemp = "";
		String inputvalueNew = "";
		int times = 0;
		if (syspropertiesSet != null && syspropertiesSet.count() > 0) {
			String inputvalue = syspropertiesSet.getJpo(0).getString(
					"INPUTVALUE");// 当前输入值
			// 截取输入值的时间单位
			if (!inputvalue.isEmpty()) {
				inputvalueTemp = inputvalue.substring(inputvalue.length() - 1,
						inputvalue.length());

				// 截取第一位的数字
				inputvalueNew = inputvalue.substring(0, 1);
				// 截取到字符串转换成数字
				times = Integer.parseInt(inputvalueNew);
			}
		}
		// zzx add end
		IJpoSet transplanJpoSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSPLAN");
		transplanJpoSet.setUserWhere("STATUS='待审核'and plantype='改造'");
		transplanJpoSet.reset();

		// 2.循环过滤数据，取出JPO
		if (transplanJpoSet != null && transplanJpoSet.count() > 0) {
			for (int i = 0; i < transplanJpoSet.count(); i++) {
				// 取出计划创建日期
				Date plansetdate = transplanJpoSet.getJpo(i).getDate(
						"PLANSETDATE");
				String transplannum = transplanJpoSet.getJpo(i).getString(
						"TRANSPLANNUM");// 计划编号
				String transplanname = transplanJpoSet.getJpo(i).getString(
						"TRANSPLANNAME");// 计划名称
				// 取当前时间
				java.util.Date newdate = MroServer.getMroServer().getDate();
				String[] personcc = null;// 抄送人
				String bgsy = "报警事由";
				String message = "改造计划制定已超过2天，请尽快处理";
				String news = "相关信息";
				String result = "";
				String[] object = null;
				String projectnum = "";
				if (plansetdate != null) {
					long timetmp = (newdate.getTime() - plansetdate.getTime())
							/ (24 * 60 * 60 * 1000);// 天数差
					long timehours = (newdate.getTime() - plansetdate.getTime())
							/ (1000 * 60 * 60);// 小时差

					long timemins = (newdate.getTime() - plansetdate.getTime())
							/ (1000 * 60);// 分钟差
					if (inputvalueTemp.equals("d")) {
						if (timetmp > times) {

						}
					} else if (inputvalueTemp.equals("h")) {
						if (timehours > times) {

						}
					} else if (inputvalueTemp.equals("m")) {
						if (timemins > times) {

						}
					}
					IJpoSet transdistJpoSet = transplanJpoSet.getJpo(i)
							.getJpoSet("TRANSDIST");
					for (int j = 0; j < transdistJpoSet.count(); j++) {

						projectnum = transdistJpoSet.getJpo(j).getString(
								"PROJECTNUM");// 所属项目
					}
					// 拼接表格
					StringBuilder sb = new StringBuilder();
					sb.append("<table border=\"1\">");
					sb.append("<tr>");
					sb.append("<th colspan=\"2\">" + bgsy);
					sb.append("</th>");
					sb.append("</tr>");
					sb.append("<tr>");
					sb.append("<td colspan=\"2\" align=\"center\"> " + message);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<th colspan=\"2\">" + news);
					sb.append("</th>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>" + "计划编号：");
					sb.append("</td>");
					sb.append("<td>" + transplannum);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>" + "计划名称：");
					sb.append("</td>");
					sb.append("<td>" + transplanname);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>" + "改造计划天数：");
					sb.append("</td>");
					sb.append("<td>" + timetmp + "天未审核改造任务");
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<td>" + "计划链接：");
					sb.append("</td>");
					sb.append("<td>"
							+ "<a href=\"http://mro.csrzic.com/gwmro\">登陆系统</a>");
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("</table>");
					result = sb.toString();
					if (!projectnum.isEmpty()) {
						Vector<String> personall = new Vector();
						Vector<String> personVector = WorkorderUtil
								.getProjectRole(projectnum, "售后项目经理");
						for (int index = 0; index < personVector.size(); index++) {
							personall.add(personVector.get(index));
							}
						object = personall
								.toArray(new String[personall.size()]);
					}

						try {
						System.out.println("成功");
							EwsEmail.sends(bgsy, result, object, personcc);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
	}
}

