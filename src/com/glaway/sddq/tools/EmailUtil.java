package com.glaway.sddq.tools;

import java.util.Vector;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 邮件发送工具类
 * 
 * @author zzx
 * @version [版本号, 2018-9-4]
 * @since [产品/模块版本]
 */
public class EmailUtil {


	/**
	 * 
	 * 装箱单填写完成后提交。故障品现场发货（现场发回中心） 6
	 * 
	 * @author zzx
	 * @version [版本号, 2018-9-4]
	 * @since [产品/模块版本]
	 */
	public static String zxdendtx(String transfernum) throws MroException {

		System.out.println("成功");
		String bgsy = "货物已发出";
		String zxd = "装箱单编号为" + transfernum
				+ " 的装箱单已从现场发出，请注意跟踪该装箱单的情况，装箱单具体内容，请根据编号到绿荫服务平台中查看";
		String[] person = { "ZHOUZHIXIONHG", "USER001" };
		String[] personcc = null;
		try {
			EwsEmail.sendsTest(bgsy, zxd, person, personcc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 送修单打印完成，故障品本部送修，邮件发送提醒故障品已送修
	 * 
	 * 7
	 * 
	 * @author zzx
	 * @version [版本号, 2018-9-4]
	 * @since [产品/模块版本]
	 */
	public static String sxdendtx() throws MroException {
		System.out.println("成功");
		String bgsy = "货物已发出";
		String zxd = "送修单编号为" + 001
				+ " 的装箱单已从现场发出，请注意跟踪该装箱单的情况，装箱单具体内容，请根据编号到绿荫服务平台中查看";
		String[] person = { "ZHOUZHIXIONHG", "USER001" };
		String[] personcc = null;
		try {
			EwsEmail.sendsTest(bgsy, zxd, person, personcc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}

	/**
	 * 
	 * 送修单打印后一个工作日承修单位未接收（送修品/改造品接收延误 ） (需要定时方法)
	 * 
	 * 8
	 * 
	 * @author zzx
	 * @version [版本号, 2018-9-4]
	 * @since [产品/模块版本]
	 */
	public static String sxdcxcompanynotreceivelatetx() throws MroException {

		System.out.println("成功");
		String bgsy = "送修单已打印";
		String zxd = "送修单编号为" + 002 + " 的送修单已打印，承修单位请注意接收";
		String[] person = { "ZHOUZHIXIONHG", "USER001" };
		String[] personcc = null;
		try {
			EwsEmail.sendsTest(bgsy, zxd, person, personcc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


		/**
	 * 
	 * 维修缴库单打印后一个工作日售后未接收（修复品/改造品接收延误 ） (需要定时方法)
	 * 
	 * 9
	 * 
	 * @author zzx
	 * @version [版本号, 2018-9-4]
	 * @since [产品/模块版本]
	 */
	public static String jkdprintshnotreceivetx() throws MroException {
		System.out.println("成功");
		String bgsy = "缴库单已打印";
		String zxd = "缴库单编号为" + 002 + " 的缴库单已打印，售后单位请注意接收";
		String[] person = { "ZHOUZHIXIONHG", "USER001" };
		String[] personcc = null;
		try {
			EwsEmail.sendsTest(bgsy, zxd, person, personcc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * 改造物料发货通知(改造物料完成发货后，信息通报相关人员)13 (参数需要修改)
	 * 
	 * @author zzx
	 * @version [版本号, 2018-9-20]
	 * @since [产品/模块版本]
	 */
	public static String tranfersengptx(IJpo transferJpo)
			throws MroException {
		// 1.取出改造装箱单中状态不为未处理的数据
		IJpoSet transferJpoSet = transferJpo.getJpoSet();

		if (transferJpoSet != null && transferJpoSet.count() > 0) {

				// zzx add 9.26
			String gztransfernum = transferJpo.getString("TRANSFERNUM");// 装箱单编号
			String gzpackdate = transferJpo.getString("PACKDATE");// 装箱日期
			String gzreceiveaddress = transferJpo.getString("RECEIVEADDRESS");// 接收地址
			String gzsendby = transferJpo.getString("SENDBY");// 发运人
			String gzreceiveby = transferJpo.getString("RECEIVEBY");// 收货人
			String gzopenby = transferJpo.getString("OPENBY");// 接收人
			String projectnum = transferJpo.getString("PROJECTNUM");// 项目编号
			String transworkordernum = transferJpo
					.getString("TRANSWORKORDERNUM");// 改造工作令号

			String[] personcc = null;// 抄送人
			String itemnum = "";
			String itemnumname = "";
			String orderqty = "";
				// zzx end
				IJpoSet transferlineset = transferJpo.getJpoSet("transferline");
				for (int j = 0; j < transferlineset.count(); j++) {
				itemnum = transferlineset.getJpo(j).getString(
							"ITEMNUM");
				itemnumname = transferlineset.getJpo(j).getString(
							"ITEM.DESCRIPTION");
				orderqty = transferlineset.getJpo(j).getString(
							"ORDERQTY");
			}
			// 取出改造令号，带到改造通知单中查询办事处主任
			IJpoSet transnoticeJpoSet = MroServer.getMroServer().getSysJpoSet(
					"TRANSNOTICE");
			transnoticeJpoSet.setUserWhere("TRANSWORKORDERNUM='"
					+ transworkordernum + "'");

			transnoticeJpoSet.reset();


			String responsible = "";
			if (transnoticeJpoSet != null && transnoticeJpoSet.count() > 0) {
				// 取改造计划
				IJpoSet transplanJpoSet = transnoticeJpoSet.getJpo().getJpoSet(
						"TRANSPLAN");

				// 取改造车辆分布Jposet
				IJpoSet transdistJpoSet = transplanJpoSet.getJpo().getJpoSet(
						"TRANSDIST");

				for (int index = 0; index < transdistJpoSet.count(); index++) {
					responsible = transdistJpoSet.getJpo(index).getString(
							"RESPONSIBLE");// 办事处主任
				}
			}


			String bgsy = "发货通知";
			String message = "改造物料已发出，请注意查收";
			String news = "相关信息";

				StringBuilder sb = new StringBuilder();
				sb.append("<table border=\"1\">");
				sb.append("<tr>");
				sb.append("<th colspan=\"2\">" + bgsy);
				sb.append("</th>");
					sb.append("</tr>");
					sb.append("<tr>");
				sb.append("<td colspan=\"2\" align=\"center\">"
						+ message);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
					sb.append("<th colspan=\"2\">" + news);
					sb.append("</th>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "装箱单编号：");
					sb.append("</td>");
				sb.append("<td>" + gztransfernum);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "物料编码：");
					sb.append("</td>");
				sb.append("<td>" + itemnum);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "物料名称：");
					sb.append("</td>");
				sb.append("<td>" + itemnumname);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "调拨数量：");
					sb.append("</td>");
				sb.append("<td>" + orderqty);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "装箱日期：");
					sb.append("</td>");
				sb.append("<td>" + gzpackdate);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "接收地址：");
					sb.append("</td>");
				sb.append("<td>" + gzreceiveaddress);
					sb.append("</td>");
					sb.append("</tr>");



					sb.append("<tr>");
			sb.append("<td>" + "发运人：");
					sb.append("</td>");
				sb.append("<td>" + gzsendby);
					sb.append("</td>");
					sb.append("</tr>");

					sb.append("<tr>");
			sb.append("<td>" + "接收人：");
					sb.append("</td>");
				sb.append("<td>" + gzopenby);
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

				Vector<String> personall = new Vector();
				Vector<String> personVector = WorkorderUtil.getProjectRole(
					projectnum, "售后计划经理");
				for (int index = 0; index < personVector.size(); index++) {
					personall.add(personVector.get(index));
				}

				if (!gzopenby.isEmpty()) {
				personall.add(gzopenby);// 任务接收人
			}

			if (!responsible.isEmpty()) {
				// personall.add(responsible);// 办事处主任
				personall.add(responsible);// 办事处主任
			}
			if (!gzsendby.isEmpty()) {
				// personall.add(responsible);// 办事处主任
				personall.add(gzsendby);// 办事处主任
				}

				String[] object = personall
						.toArray(new String[personall.size()]);
					try {
				System.out.println("成功");
					EwsEmail.sends(bgsy, result, object, personcc);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			

		}


		return null;
	}

	/**
	 * 
	 * 提交故障分析报告提醒 17
	 * 
	 * @author zzx
	 * @version [版本号, 2018-9-25]
	 * @since [产品/模块版本]
	 */
	public static String gzreporttx(String ordernum, String displayname,
			Vector<String> personVector, String[] object,
			String servcompany)
			throws MroException {
		String bgsy = "报警事由";
		String message = "需要故障分析报告提醒";
		String news = "相关信息";
		String[] personcc = null;
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=\"1\">");
		sb.append("<tr>");
		sb.append("<th colspan=\"2\">" + bgsy);
		sb.append("</th>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan=\"2\" align=\"center\">" + message);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<th colspan=\"2\">" + news);
		sb.append("</th>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>" + "工单编号：");
		sb.append("</td>");
		sb.append("<td>" + ordernum);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>" + "现场处理人：");
		sb.append("</td>");
		sb.append("<td>" + displayname);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>" + "售后技术经理：");
		sb.append("</td>");
		sb.append("<td>" + personVector);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>" + "服务单位：");
		sb.append("</td>");
		sb.append("<td>" + servcompany);
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>" + "计划链接：");
		sb.append("</td>");
		sb.append("<td>" + "<a href=\"http://mro.csrzic.com/gwmro\">登陆系统</a>");
		sb.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");

		String result = sb.toString();

		try {
			System.out.println("成功");
			EwsEmail.sends(bgsy, result, object, personcc);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
}
