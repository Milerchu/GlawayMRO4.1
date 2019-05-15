package com.glaway.sddq.tools.ConTask;

import java.util.Date;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.EwsEmail;

public class TestEws {
	public static void test() throws MroException {
		// TODO Auto-generated method stub
		System.out.println("已经进入定时任务方法！！！");
		// zzx add start 20181027

		// 筛选出系统属性中名称为ESMAILS.ZXD的数据
		IJpoSet syspropertiesSet = MroServer.getMroServer().getSysJpoSet(
				"SYS_PROPERTIES");
		syspropertiesSet.setUserWhere("PROPNAME='ESMAILS.ZXD'");
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
				// 截取到字符串转换车数字
				times = Integer.parseInt(inputvalueNew);
			}
		}
		// zzx add end
		IJpoSet transferJpoSet = MroServer.getMroServer().getSysJpoSet(
				"TRANSFER");
		transferJpoSet
				.setUserWhere("STATUS='在途'and TYPE='ZXD' and TRANSFERMOVETYPE in('中心到现场','现场到中心'，'现场到现场')");

		transferJpoSet.reset();

		// 2.循环过滤数据，取出每条的数据的发出日期
		if (transferJpoSet != null && transferJpoSet.count() > 0) {
			for (int i = 0; i < transferJpoSet.count(); i++) {
				if (transferJpoSet.getJpo(i) == null) {
					continue;
				}
				// 取出发出日期
				Date sendtime = transferJpoSet.getJpo(i).getDate("SENDTIME");
				// 取当前时间
				java.util.Date newdate = MroServer.getMroServer().getDate();
				// 取出接收人，装箱单编号，发货人，收货人，装箱日期，运单号，
				String receiveby = transferJpoSet.getJpo(i).getString(
						"RECEIVEBY");// 接收人
				String receiveby1 = transferJpoSet.getJpo(i).getString(
						"RECEIVEBY.DISPLAYNAME");// 接收人
				String transfernum = transferJpoSet.getJpo(i).getString(
						"TRANSFERNUM");// 装箱单编号
				String sendby = transferJpoSet.getJpo(i).getString("SENDBY");// 发货人

				String sendbyname = transferJpoSet.getJpo(i).getString(
						"SENDBY.DISPLAYNAME");// 发货人

				String packdate = transferJpoSet.getJpo(i)
						.getString("PACKDATE");// 装箱日期

				String couriernum = transferJpoSet.getJpo(i).getString(
						"COURIERNUM");// 运单号
				String bgsy = "报警事由";
				String message = "装箱单发出后超出7天仍未接收，请尽快处理";
				String news = "相关信息";
				long timetmp = (newdate.getTime() - sendtime.getTime())
						/ (24 * 60 * 60 * 1000);// 天数差

				long timehours = (newdate.getTime() - sendtime.getTime())
						/ (1000 * 60 * 60);// 小时差

				long timemins = (newdate.getTime() - sendtime.getTime())
						/ (1000 * 60);// 分钟差
				if (inputvalueTemp.equals("d")) {
					if (timetmp >= times) {

					}
				} else if (inputvalueTemp.equals("h")) {
					if (timehours >= times) {

					}
				} else if (inputvalueTemp.equals("m")) {
					if (timemins >= times) {

					}
				}

				System.out.println("装箱单发出后超出7天仍未接收，请尽快处理");
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
				sb.append("<td>" + "装箱单编号：");
				sb.append("</td>");
				sb.append("<td>" + transfernum);
				sb.append("</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>" + "发货人：");
				sb.append("</td>");
				sb.append("<td>" + sendbyname);
				sb.append("</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>" + "接收人：");
				sb.append("</td>");
				sb.append("<td>" + receiveby1);
				sb.append("</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>" + "装箱日期：");
				sb.append("</td>");
				sb.append("<td>" + packdate);
				sb.append("</td>");
				sb.append("</tr>");

				sb.append("<tr>");
				sb.append("<td>" + "运单编号：");
				sb.append("</td>");
				sb.append("<td>" + couriernum);
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

				String result = sb.toString();
				// zzx end
				try {
					System.out.println("成功");
					EwsEmail.send(bgsy, result, receiveby, "");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}
	}

	// 调用方法
	public static void main(String[] args) throws Exception {
		test();
	}
}
