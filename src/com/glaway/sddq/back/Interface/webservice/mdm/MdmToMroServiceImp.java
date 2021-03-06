package com.glaway.sddq.back.Interface.webservice.mdm;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.base.custinfo.data.CustInfo;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.JDBCUtil;
import com.glaway.sddq.tools.MdmReturnSave;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@WebService(endpointInterface = "com.glaway.sddq.back.Interface.webservice.mdm.MdmToMroService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MdmToMroServiceImp implements MdmToMroService {

	String returntf = "";

	/**
	 * 获取人员数据
	 * 
	 * @param mdmXml
	 * @return
	 */
	@Override
	public String toMroMdmPersonData(String mdmXml) {
		String num = "";
		IJpoSet personset = null;

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;

		try {
			num = IFUtil.addIfHistory("MDM_MRO_PERSONIF", mdmXml, IFUtil.TYPE_INPUT);

			//获取连接
			conn = JDBCUtil.getOrclConn();

			String personSql = "";
			String phoneSql = "";
			String emailSql = "";

			boolean results = false;

			Document doc = DocumentHelper.parseText(mdmXml);
			Element root = doc.getRootElement();
			List<Element> objectlist = root.elements("object");

			for (Element object : objectlist) {

				Element personBaseInfo = object.element("personBaseInfo");

				//判断personBaseInfo节点是否存在
				if (personBaseInfo != null) {
					String empcode = personBaseInfo.valueOf("emp_code");// 人员ID属性的值
					String empname = personBaseInfo.valueOf("empname");// 人员姓名属性的值
					String gender = "1".equals(personBaseInfo.valueOf("gender")) ? "男" : "女";// 人员性别的值
					String nation = personBaseInfo.valueOf("nation");// 人员民族的值
					String nativeplace = personBaseInfo.valueOf("nativeplace");// 人员籍贯的值
					String mobile = personBaseInfo.valueOf("mobile");// 电话
					String empmail = personBaseInfo.valueOf("emp_mail");// 电子邮箱

					String bgwdate = personBaseInfo.valueOf("bg_wdate");// 状态日期（特殊）
					// personBaseInfo.valueOf("address");//缺省地点(地点)UESER表
					String emp_type1 = personBaseInfo.valueOf("emp_type1");// 人员分类AB
					String emp_type2 = personBaseInfo.valueOf("emp_type2");// 人员分类

					String delcode = personBaseInfo.valueOf("del_code");//删除标记

					String status = "";
					if (empcode.startsWith("9") || empcode.startsWith("P")) {
						status = personBaseInfo.valueOf("emp_stat");// B类人员专用状态
					} else {
						status = personBaseInfo.valueOf("status");// 人员状态
					}

					String[] activeStatus = {"1","2","9","10","11","12"};
					String[] unactiveStatus = {"0","3","4","5","6","7","13","14","15","16","17"};
					if(StringUtil.isHaveStr(unactiveStatus,status)){
						status = "不活动";
					}else if(StringUtil.isHaveStr(activeStatus, status)){
						status = "活动";
					}
					String calzzmc = "";
					List<Element> personComputerList = object.elements("personComputerList");
					for (Element personComputer : personComputerList) {
						Element personCo = personComputer.element("personComputerInfo");
						calzzmc = personCo.valueOf("cal_zzmc");// 计算机水平
					}

					//更新or新增
					if(StringUtil.isStrEmpty(delcode)){

						/*对人员表操作*/
						personSql = "merge into sys_person p using (select ? personid,? displayname,? GENDER," +
								"               ? PERSONCLASSIFICATION,? NATION,? COMPUTERLV,? STATUS from dual) dat " +
								"                   on (dat.personid=p.personid)\n" +
								"when matched then\n" +
								"    update set p.displayname=dat.displayname,p.GENDER=dat.GENDER," +
								"               p.PERSONCLASSIFICATION=dat.PERSONCLASSIFICATION,p.NATION=dat.NATION," +
								"               p.COMPUTERLV=dat.COMPUTERLV,p.status=dat.STATUS\n" +
								"when not matched then\n" +
								"    insert (PERSONID,DISPLAYNAME,GENDER,PERSONCLASSIFICATION,NATION,COMPUTERLV,\n" +
								"            STATUS,SYS_PERSONID,ACCEPTINGWFMAIL,locale,LANGCODE,LOCTOSERVREQ," +
								"            statusdate,WFMAILELECTION,TRANSEMAILELECTION)\n" +
								"    values (dat.personid,dat.displayname,dat.GENDER,dat.PERSONCLASSIFICATION," +
								"            dat.NATION,dat.COMPUTERLV,dat.STATUS,sys_personseq.nextval,1,'zh_CN'," +
								"            'ZH',1,sysdate,'过程','从不')";

						ps1 = conn.prepareStatement(personSql);

						ps1.setString(1, empcode);//人员id
						ps1.setString(2, empname);//人员姓名
						ps1.setString(3, gender);//性别
						ps1.setString(4, emp_type2);//人员类型
						ps1.setString(5, nation);//国籍
						ps1.setString(6, calzzmc);//计算机水平
						ps1.setString(7, status);//状态

						ps1.addBatch();

						/*对电话表操作*/
						if(StringUtil.isStrNotEmpty(mobile)){

							phoneSql = "merge into sys_phone ph using (select personid from sys_person where personid=?) p" +
									" on ( p.personid=ph.personid )\n" +
									" when matched then\n" +
									"   update set phonenum=?" +
									" when not matched then "+
									" 	insert (ISPRIMARY,SYS_PHONE,phonenum,personid) " +
									"    values(1,sys_phoneseq.nextval,?,p.personid)";
							ps2 = conn.prepareStatement(phoneSql);

							ps2.setString(1, empcode);//人员id
							ps2.setString(2, mobile);//电话号码
							ps2.setString(3, mobile);//电话号码

							ps2.addBatch();

						}

						/*对邮箱表操作*/
						if(StringUtil.isStrNotEmpty(empmail)){
							emailSql = "merge into sys_email em using (select personid from sys_person where personid=?) p " +
									"    on ( p.personid=em.personid )\n" +
									"    when matched then\n" +
									"        update set emailaddress=?\n" +
									"    when not matched then\n" +
									"        insert (ISPRIMARY,SYS_EMAILID,emailaddress,personid)\n" +
									"         values(1,sys_emailseq.nextval,?,p.personid)";
							ps3 = conn.prepareStatement(emailSql);

							ps3.setString(1, empcode);//人员id
							ps3.setString(2, empmail);//邮箱
							ps3.setString(3, empmail);//邮箱

							ps3.addBatch();
						}

					}else{
					//删除

						/*删除人员表*/
						personSql = "merge into sys_person p using (select ? personid from dual) dat " +
								" on (dat.personid=p.personid)\n" +
								" when matched then\n" +
								"    update set p.displayname=p.displayname\n" +
								"    delete where p.personid=dat.personid";

						ps1 = conn.prepareStatement(personSql);

						ps1.setString(1, empcode);

						ps1.addBatch();

						/*删除电话表*/
						phoneSql = "merge into sys_phone ph using (select ? personid from dual) dat" +
								" on (dat.personid=ph.personid) "+
								" when matched then "+
								"   update set ph.phonenum=ph.phonenum "+
								" 	delete where ph.personid=dat.personid ";
						ps2 = conn.prepareStatement(phoneSql);

						ps2.setString(1, empcode);

						ps2.addBatch();

						/*删除邮箱表*/
						emailSql = "merge into sys_email e using (select ? personid from dual) dat" +
								" on (dat.personid=e.personid) "+
								" when matched then "+
								"   update set e.emailaddress=e.emailaddress "+
								" 	delete where e.personid=dat.personid ";
						ps3 = conn.prepareStatement(emailSql);

						ps3.setString(1, empcode);

						ps3.addBatch();


					}

					//执行批处理sql
					int[] rs1 = ps1.executeBatch();
					int[] rs2 = ps2.executeBatch();
					int[] rs3 = ps3.executeBatch();

					//执行成功
					if(JDBCUtil.isBatchSuccess(rs1) && JDBCUtil.isBatchSuccess(rs2)
							&& JDBCUtil.isBatchSuccess(rs3)){
						results = true;
					}

					//TODO 改成jdbc形式无法获取id,暂时取消推送
					// MsgUtil.addMsg("PERSONINFO", personsetJpo.getLong("SYS_PERSONID"), MsgUtil.PERSONADD,
					//		"MDM新推人员编号为:" + personsetJpo.getString("PERSONID"), MsgUtil.GWADMINPERSON);

				} else {
					/**
					 * 2.取personOrgInfo节点下面的所属部门和岗位代码的值
					 */

					Element jobcodetag = null;
					List<Element> personOrgList = object.elements("personOrgList");
					for (Element personOrg : personOrgList) {
						String jobcodeOne = "";
						String depacode = "";
						String depaname = "";
						String empcodeone = "";
						String delremark = "";
						Element emp = personOrg.element("personOrgInfo");
						jobcodetag = emp.element("job_code");
						jobcodeOne = emp.valueOf("job_code");// 岗位代码
						depacode = emp.valueOf("depa_code");// 部门编码
						depaname = emp.valueOf("depa_name");// 部门名称
						empcodeone = emp.valueOf("emp_code");// 人员编号
						// 删除标识
						delremark = emp.valueOf("del_code");// 删除标识

						//拼接sql
						personSql = "update sys_person set DEPARTMENT=? ";
						//A 类人员更新岗位
						if (!(empcodeone.startsWith("9") || empcodeone.startsWith("P"))) {
							if (jobcodetag != null && StringUtil.isStrNotEmpty(jobcodeOne)) {
								personSql += ",jobcode=? ";
							}
						}
						personSql += " where personid=?";

						ps1 = conn.prepareStatement(personSql);

						ps1.setString(1, depacode);//部门编号
						//A 类人员更新岗位
						if (!(empcodeone.startsWith("9") || empcodeone.startsWith("P"))) {
							if (jobcodetag != null && StringUtil.isStrNotEmpty(jobcodeOne)) {
								ps1.setString(2, jobcodeOne);//岗位编号
								ps1.setString(3, empcodeone);//人员编号
							}
						}else{
							ps1.setString(2, empcodeone);//人员编号
						}
						ps1.addBatch();

						//判断是否是主要岗位,设置部门主要负责人
						if (jobcodetag != null && StringUtil.isStrNotEmpty(jobcodeOne)) {

							String postSlctSql = "select director,detpnum from post where postnum=?";
							ps2 = conn.prepareStatement(postSlctSql);

							ps2.setString(1, jobcodeOne);

							rs = ps2.executeQuery();

							String deptnum = "";

							while (rs.next()){
								if("是".equals(rs.getString("director"))){
									deptnum = rs.getString("detpnum");
								}
							}

							String deptSql = "update sys_dept set owner=? where mdm_deptid=?";
							ps3 = conn.prepareStatement(deptSql);

							ps3.setString(1, empcodeone);
							ps3.setString(2, deptnum);

							ps3.addBatch();

						}

					}

					int[] rs1 = ps1.executeBatch();
					int[] rs2 = ps3.executeBatch();

					if(JDBCUtil.isBatchSuccess(rs1) && JDBCUtil.isBatchSuccess(rs2)){
						results = true;
					}

				}

			}


			//处理成功
			if(results){
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
				returntf = MdmReturnSave.getTrueMsg();
			}else{
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, "数据库操作失败");
				returntf = MdmReturnSave.getFalseMsg("数据库操作失败！");
			}
		} catch (Exception e) {

			String errorMsg = e.getMessage();
			returntf = MdmReturnSave.getFalseMsg(errorMsg);
			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, errorMsg);
			} catch (MroException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		} finally {

			//关闭连接
			JDBCUtil.close(conn, ps1, ps3);
			JDBCUtil.close(rs, ps2, conn);

		}
		return returntf;

	}

	/**
	 * 获取组织部门数据
	 * 
	 * @param mdmXml
	 * @return
	 */
	@Override
	public String toMroMdmDeptData(String mdmXml) {

		String num = "";
		IJpoSet deptset = null;
		IJpoSet deptupdateset = null;
		try {
			num = IFUtil.addIfHistory("MDM_MRO_ORGIF", mdmXml,
					IFUtil.TYPE_INPUT);

			deptset = MroServer.getMroServer().getSysJpoSet("SYS_DEPT");
			deptupdateset = MroServer.getMroServer().getSysJpoSet(
					"SYS_DEPT");

			Document doc = DocumentHelper.parseText(mdmXml);
			Element root = doc.getRootElement();
			List<Element> objectlist = root.elements("object");
			for (Element object : objectlist) {
				Element orgBaseInfo = object.element("orgBaseInfo");
				String org_id = orgBaseInfo.valueOf("org_id");// 组织部门编号
				String orgcode = orgBaseInfo.valueOf("org_code");// 组织部门编号
				String orgname = orgBaseInfo.valueOf("org_name");// 组织部门姓名
				String validate = orgBaseInfo.valueOf("vali_date");// 成立日期
				String begidate = orgBaseInfo.valueOf("begi_date");// 生效日期
				String busitype = orgBaseInfo.valueOf("busi_type");// 业务类型
				String orglay = orgBaseInfo.valueOf("org_lay");// 组织层级
				String orgaddr = orgBaseInfo.valueOf("org_addr");// 组织地址

				String porgcode = orgBaseInfo.valueOf("porg_code");// 上级id
				String postalcode = orgBaseInfo.valueOf("org_postalcode");

				/**
				 * 取XML删除标识
				 */
				String delcode = orgBaseInfo.valueOf("del_code");

				deptupdateset.setUserWhere("DEPTNUM='" + orgcode + "'");
				deptupdateset.reset();
				IJpo deptupdatesetJpo = null;
				if (deptupdateset.count() > 0) {
					deptupdatesetJpo = deptupdateset.getJpo();
					// deptsetJpo.setValue("DEPTNUM", orgcode);
					deptupdatesetJpo.setValue("MDM_DEPTID", org_id,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptupdatesetJpo.setValue("DESCRIPTION", orgname,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptupdatesetJpo.setValue("CREATEDATE", validate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptupdatesetJpo.setValue("ACTIVEDATE", begidate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					deptupdatesetJpo.setValue("ADDR", orgaddr,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptupdatesetJpo.setValue("PARENT", porgcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					deptupdatesetJpo.setValue("DEPTTYPE", busitype,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptupdatesetJpo.setValue("HIERARCHY", orglay,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptupdatesetJpo.setValue("ZIPCODE", postalcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);


					deptupdatesetJpo.setValue("DELREMARK", delcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					String delremark = deptupdatesetJpo.getString("DELREMARK");
					if (!StringUtil.isStrEmpty(delremark)) {
						if (delremark.equals("X")) {
							deptupdatesetJpo.delete();
						}
					}


					deptupdateset.save();
				} else {
					IJpo deptsetJpo = null;
					deptsetJpo = deptset.addJpo();

					String a = deptsetJpo.getString("SITEID");

					deptsetJpo.setValue("DEPTNUM", orgcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("MDM_DEPTID", org_id,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("DESCRIPTION", orgname,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("CREATEDATE", validate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("ACTIVEDATE", begidate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					deptsetJpo.setValue("ADDR", orgaddr,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("parent", porgcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					deptsetJpo.setValue("DEPTTYPE", busitype,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("HIERARCHY", orglay,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("ZIPCODE", postalcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					deptsetJpo.setValue("SITEID", "ELEC");
					deptsetJpo.setValue("ORGID", "CRRC");

					// zzx add 10.17
					deptsetJpo.setValue("DELREMARK", delcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					String delremark = deptsetJpo.getString("DELREMARK");
					if (!StringUtil.isStrEmpty(delremark)) {
						if (delremark.equals("X")) {
							deptsetJpo.delete();
						}
					}

					// zzx end
				}

			}
			deptset.save();
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"");
			returntf = MdmReturnSave.getTrueMsg();
			return returntf;

		} catch (Exception e) {
			String errorMsg = e.getMessage();
			returntf = MdmReturnSave.getFalseMsg(errorMsg);

			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return returntf;
		} finally {

			if(deptset != null){
				deptset.destroy();
			}
			if(deptupdateset != null){
				deptupdateset.destroy();
			}
		}

	}

	/**
	 * 获取岗位数据
	 * 
	 * @param mdmXml
	 * @return
	 */
	@Override
	public String toMroMdmPostData(String mdmXml) {
		String num = "";
		IJpoSet postset = null;
		IJpoSet postupdateset = null;
		try {
			num = IFUtil.addIfHistory("MDM_MRO_POSTIF", mdmXml,
					IFUtil.TYPE_INPUT);

			postset = MroServer.getMroServer().getSysJpoSet("POST");
			postupdateset = MroServer.getMroServer().getSysJpoSet(
					"POST");

			Document doc = DocumentHelper.parseText(mdmXml);
			Element root = doc.getRootElement();
			List<Element> objectlist = root.elements("object");
			for (Element object : objectlist) {
				Element jobBaseInfo = object.element("jobBaseInfo");
				String jobcode = jobBaseInfo.valueOf("job_code");// 岗位编码
				String jobname = jobBaseInfo.valueOf("job_name");// 组织部门姓名
				String pjobcode = jobBaseInfo.valueOf("pjob_code");// 上级岗位编码
				String orgid = jobBaseInfo.valueOf("org_id");// 所属组织编码
				String jobsupe = jobBaseInfo.valueOf("job_supe");// 主管岗位
				String begindate = jobBaseInfo.valueOf("begin_date");// 生效日期

				/**
				 * 取删除标识的
				 */
				String delcode = jobBaseInfo.valueOf("del_code");

				postupdateset.setUserWhere("POSTNUM='" + jobcode + "'");
				postupdateset.reset();

				if (postupdateset.count() > 0) {
					IJpo postupdatesetJpo = null;
					postupdatesetJpo = postupdateset.getJpo();

					postupdatesetJpo.setValue("POSTNAME", jobname, 11L);

					postupdatesetJpo.setValue("POSTCODE", pjobcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					postupdatesetJpo.setValue("DETPNUM", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					postupdatesetJpo.setValue("DIRECTOR", jobsupe,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					postupdatesetJpo.setValue("ACTIVEDATE", begindate, 11L);

					postupdatesetJpo.setValue("DELREMARK", delcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					String directorupdate = postupdatesetJpo
							.getString("DIRECTOR");
					if (directorupdate != null) {
						if (directorupdate.equals("1")) {

							postupdatesetJpo.setValue("DIRECTOR", "是",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							postupdatesetJpo.setValue("DIRECTOR", "否",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}

					String delremark = postupdatesetJpo.getString("DELREMARK");
					if (!StringUtil.isStrEmpty(delremark)) {
						if (delremark.equals("X")) {
							postupdatesetJpo.delete();
						}
					}

					postupdatesetJpo.getThisJpoSet().save();
				} else {
					IJpo postsetJpo = null;
					postsetJpo = postset.addJpo();
					postsetJpo.setValue("POSTNUM", jobcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					postsetJpo.setValue("POSTNAME", jobname,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					postsetJpo.setValue("POSTCODE", pjobcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					postsetJpo.setValue("DETPNUM", orgid,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					postsetJpo.setValue("DIRECTOR", jobsupe,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					postsetJpo.setValue("ACTIVEDATE", begindate,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					// 给删除标识赋值
					postsetJpo.setValue("DELREMARK", delcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					String director = postsetJpo.getString("DIRECTOR");

					if (director != null) {
						if (director.equals("1")) {

							postsetJpo.setValue("DIRECTOR", "是",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						} else {
							postsetJpo.setValue("DIRECTOR", "否",
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}

					String delremark = postsetJpo.getString("DELREMARK");
					if (!StringUtil.isStrEmpty(delremark)) {
						if (delremark.equals("X")) {
							postsetJpo.delete();
						}
					}

				}

			}
			postset.save();
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"");
			returntf = MdmReturnSave.getTrueMsg();
			return returntf;
		} catch (Exception e) {

			String errorMsg = e.getMessage();
			returntf = MdmReturnSave.getFalseMsg(errorMsg);
			// 处理失败
			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
			} catch (MroException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return returntf;
		} finally {
			if(postupdateset != null){
				postupdateset.destroy();
			}
			if(postset != null){
				postset.destroy();
			}
		}

	}

	/**
	 * 获取客户数据
	 * 
	 * @param mdmXml
	 * @return
	 */
	@Override
	public String toMroMdmCustinfoData(String mdmXml) {
		String num = "";
		IJpoSet custinfoset = null;
		IJpoSet custinfoupdateset = null;
		try {

			num = IFUtil.addIfHistory("MDM_MRO_CUSTIF", mdmXml,
					IFUtil.TYPE_INPUT);
			custinfoset = MroServer.getMroServer().getSysJpoSet(
			"CUSTINFO");
			custinfoupdateset = MroServer.getMroServer().getSysJpoSet(
					"CUSTINFO");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");

			Document doc = DocumentHelper.parseText(mdmXml);
			Element root = doc.getRootElement();
			List<Element> objectlist = root.elements("object");
			for (Element object : objectlist) {

				Element custinfo = object.element("customerBaseInfo");
				String cuscode = custinfo.valueOf("cus_code");// 客户编码
				String name1 = custinfo.valueOf("name1");// 客户姓名
				String country = custinfo.valueOf("country");// 国家
				String regio = custinfo.valueOf("regio");// 省
				String city01 = custinfo.valueOf("city01");// 市
				String postcode = custinfo.valueOf("post_code");// 邮编
				String faxnum = custinfo.valueOf("fax_num");// 传真
				String street = custinfo.valueOf("street");// 地址

				String legal = "";
				String busicont = "";
				String busiconmail = "";
				String conbirthdata = "";
				String congender = "";
				String connation = "";
				String busiconpho = "";
				String cusclass = "";

				/**
				 * 主表赋值。判断是否存在
				 */
				custinfoupdateset.setUserWhere("CUSTNUM='" + cuscode + "'");
				custinfoupdateset.reset();
				IJpo custinfoupdatesetJpo = null;
				CustInfo custinfosetJpo = null;
				if (custinfoupdateset.count() > 0) {

					custinfoupdatesetJpo = (CustInfo) custinfoupdateset
							.getJpo();
					custinfoupdatesetJpo.setValue("CUSTNAME", name1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("COUNTRY", country,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("STATEPROVINCE", regio,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("CITY", city01,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("ZIPCODE", postcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("FAX", faxnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("ADDRESS", street,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfoupdatesetJpo.setValue("LEGALREP", legal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

					custinfoupdateset.save();

				} else {
					/**
					 * 给客户表字段赋值
					 */
					custinfosetJpo = (CustInfo) custinfoset.addJpo();
					if (StringUtil.isStrNotEmpty(cuscode)) {
						custinfosetJpo.setValue("CUSTNUM", cuscode,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					custinfosetJpo.setValue("CUSTNAME", name1,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("COUNTRY", country,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("STATEPROVINCE", regio,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("CITY", city01,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("ZIPCODE", postcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("FAX", faxnum,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("ADDRESS", street,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					custinfosetJpo.setValue("LEGALREP", legal,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

				}

				List<Element> customerBodyList = object
						.elements("customerBodyList");
				for (Element customerBody : customerBodyList) {

					Element customerle = customerBody
							.element("customerBodyInfo");
					if (customerle != null) {
						legal = customerle.valueOf("legal");// 法人代表
						cusclass = customerle.valueOf("cus_class");// 客户分类
					}

					/**
					 * 判断customerContactList是否存在
					 */
					List<Element> customerContactList = customerBody
							.elements("customerContactList");
					if (customerContactList != null
							&& customerContactList.size() > 0) {
						IJpoSet customercontactset2 = custinfosetJpo.getJpoSet(
								"$CUSTOMERCONTACTOR", "CUSTOMERCONTACTOR",
								"1=2");
						// customercontactset2.deleteAll();
						for (Element customerContact : customerContactList) {
							Element customer = customerContact
									.element("customerContactInfo");
							busicont = customer.valueOf("busi_cont");// 客户联系人
							busiconmail = customer.valueOf("busi_conmail");// 客户联系人邮箱地址
							conbirthdata = customer.valueOf("conbirth_data");// 客户联系人出生日期
							congender = customer.valueOf("congender");// 客户联系人性别
							connation = customer.valueOf("connation");// 客户联系人民族
							busiconpho = customer.valueOf("busi_conpho");// 客户联系人电话
							customercontactset2.setUserWhere("CUSTNUM='"
									+ cuscode + "'");
							customercontactset2.reset();

							IJpo customercontactsetJpo = customercontactset2
									.addJpo();
							// IJpo customercontactsetJpo = jposet.addJpo();
							customercontactsetJpo.setValue("NAME", busicont,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							customercontactsetJpo.setValue("EMAILADDRESS",
									busiconmail,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							customercontactsetJpo.setValue("BIRTHDAY",
									conbirthdata,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							customercontactsetJpo.setValue("GENDER", congender,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							customercontactsetJpo.setValue("NATIONALITY",
									connation,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							customercontactsetJpo.setValue("TELEPHONE",
									busiconpho,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							customercontactsetJpo.setValue("CUSTNUM", cuscode,
									GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

						}

					}

				}

			}

			custinfoset.save();
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"");
			returntf = MdmReturnSave.getTrueMsg();
			return returntf;
		} catch (Exception e) {

			String errorMsg = e.getMessage();
			returntf = MdmReturnSave.getFalseMsg(errorMsg);

			// 处理失败
			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
			} catch (MroException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
			return returntf;
		} finally {
			if(custinfoupdateset != null){
				custinfoupdateset.destroy();
			}
			if(custinfoset != null){
				custinfoset.destroy();
			}
		}

	}

	/**
	 * 获取供应商数据
	 * 
	 * @param mdmXml
	 * @return
	 */
	@Override
	public String toMroMdmCompanyData(String mdmXml) {
		String num = "";
		IJpoSet companyset = null;
		IJpoSet companyupdateset = null;
		try {
			num = IFUtil.addIfHistory("MDM_MRO_COMPANIESIF", mdmXml,
					IFUtil.TYPE_INPUT);

			companyset = MroServer.getMroServer().getSysJpoSet(
			"SYS_COMPANIES");

			companyupdateset = MroServer.getMroServer().getSysJpoSet(
					"SYS_COMPANIES");

			Document doc = DocumentHelper.parseText(mdmXml);
			Element root = doc.getRootElement();
			List<Element> objectlist = root.elements("object");
			for (Element object : objectlist) {
				Element supplierBaseInfo = object.element("supplierBaseInfo");
				String supcode = supplierBaseInfo.valueOf("sup_code");// 供应商编码
				String name1 = supplierBaseInfo.valueOf("name1");// 供应商名称
				String street = supplierBaseInfo.valueOf("street");// 地址
				String busicont = null;
				String busiconnum = null;
				String busiconmail = null;

				List<Element> supplierBodyList = object
						.elements("supplierBodyList");
				for (Element supplierBody : supplierBodyList) {
					List<Element> supplierContactList = supplierBody
							.elements("supplierContactList");
					for (Element supplierContact : supplierContactList) {
						Element suContact = supplierContact
								.element("supplierContactInfo");
						busicont = suContact.valueOf("busi_cont");// 供应商联系人
						busiconnum = suContact.valueOf("busi_connum");// 供应商电话
						busiconmail = suContact.valueOf("busi_conmail");// 供应商邮箱

					}
				}
				companyupdateset.setUserWhere("COMPANY='" + supcode + "'");
				companyupdateset.reset();

				if (companyupdateset.count() > 0) {
					IJpo companysetJpo_tmp = null;
					companysetJpo_tmp = companyupdateset.getJpo();
					companysetJpo_tmp.setValue("ADDRESS3", busiconmail);
					companysetJpo_tmp.setValue("NAME", name1);
					companysetJpo_tmp.setValue("ADDRESS5", street);
					companyupdateset.save();
				} else {
					/**
					 * 给供应商表字段赋值
					 */
					IJpo companysetJpo = companyset.addJpo();
					companysetJpo.setValue("COMPANY", supcode,
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					companysetJpo.setValue("NAME", name1);
					companysetJpo.setValue("ADDRESS5", street);
				}

			}
			companyset.save();
			IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES,
					"");
			returntf = MdmReturnSave.getTrueMsg();
			return returntf;
		} catch (Exception e) {

			String errorMsg = e.getMessage();
			returntf = MdmReturnSave.getFalseMsg(errorMsg);

			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE,
						IFUtil.FLAG_YES, e.getMessage());
			} catch (MroException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();

			return returntf;
		} finally {
			if(companyupdateset != null){
				companyupdateset.destroy();
			}
			if(companyset != null){
				companyset.destroy();
			}
		}

	}

	/**
	 * 获取物料数据
	 * 
	 * @param mdmXml
	 * @return
	 */
	@Override
	public String toMroMdmItemData(String mdmXml) {
		String num = "";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			num = IFUtil.addIfHistory("MDM_MRO_ITEMIF", mdmXml, IFUtil.TYPE_INPUT);

			//获取数据库连接
			conn = JDBCUtil.getOrclConn();

			String sql = "merge into sys_item item " +
					"using (select ? itemnum, ? des, ? itemtype, ? orderunit, ? SPEC," +
					"? itemgroup,? producter from dual) dat " +
					"on (dat.itemnum=item.itemnum)\n " +
					"when matched then\n" +
					" update set item.description=dat.des,item.SPECIFICATION=dat.SPEC," +
					"item.ORDERUNIT=dat.orderunit,item.ITEMGROUP=dat.itemgroup,item.ITEMTYPE=dat.itemtype," +
					"item.producter=dat.producter \n" +
					"when not matched then\n" +
					" insert (SYS_ITEMID,itemnum,description,HARDRESISSUE,ISKIT,ITEMTYPE,ORDERUNIT," +
					"OUTSIDE,PRORATE,ROTATING,SPECIFICATION,STATUS,Statusdate,CAPITALIZED,IMPORTANT," +
					"ISCHECK,ISTURNOVER,ITEMGROUP,PLUSCISINHOUSECAL,PLUSCSOLUTION,PRODUCTER,SPAREPARTAUTOADD," +
					"ISNEW,TOOL,ISLOT,IMPORTANTERP,ISLOTERP,ISTURNOVERERP,ISIV) \n" +
					" values(Sys_Itemseq.Nextval,dat.itemnum,dat.des,0,0,dat.itemtype,dat.orderunit," +
					"0,0,0,dat.SPEC,'活动',sysdate,0,0,0,0,dat.itemgroup,0,0,dat.producter,0,1,0,0,0,0,0,0)";

			pstm = conn.prepareStatement(sql);

			Document doc = DocumentHelper.parseText(mdmXml);
			Element root = doc.getRootElement();
			List<Element> objectlist = root.elements("object");
			for (Element object : objectlist) {
				Element partBaseInfo = object.element("partBaseInfo");
				String matlcode = partBaseInfo.valueOf("matl_code");// 物料编码
				String matldesb = partBaseInfo.valueOf("matl_desb");// 物料描述
				String typespec = partBaseInfo.valueOf("type_spec");// 规格型号
				String baseunit = partBaseInfo.valueOf("base_unit");// 基本计量单位
				String matlgrp = partBaseInfo.valueOf("matl_grp");// 物料组
				String matltype = partBaseInfo.valueOf("matl_type");// 物料类型
				String brands = partBaseInfo.valueOf("brands");// 品牌
				// 可更换类别，是否序列号管理，是否追溯件，是否批次号管理，在MDM中找不到相对应的字段

				// 设置预编译参数的值
				pstm.setString(1, matlcode);//物料编码
				pstm.setString(2, matldesb);//物料描述
				pstm.setString(3, matltype);//物料类型
				pstm.setString(4, baseunit);//单位
				pstm.setString(5, typespec);//规格型号
				pstm.setString(6, matlgrp);//物料组
				pstm.setString(7, brands);//生产单位

				pstm.addBatch();

			}
			//执行sql
			int[] results = pstm.executeBatch();

			if(JDBCUtil.isBatchSuccess(results)){//操作成功
				IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
			}
			returntf = MdmReturnSave.getTrueMsg();

		} catch (Exception e) {

			String errorMsg = e.getMessage();
			returntf = MdmReturnSave.getFalseMsg(errorMsg);

			try {
				IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, errorMsg);
			} catch (MroException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
		} finally {
			//关闭数据库连接
			JDBCUtil.close(conn,pstm);
		}
		return returntf;

	}

}