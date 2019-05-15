package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修准作业指导书任务项 Jpo
 * 
 * @author bchen
 * @version [版本号, 2018-5-3]
 * @since [产品/模块版本]
 */
public class SchemeJobBookTask extends Jpo implements IJpo {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void delete(long flag) throws MroException {
		super.delete(flag);
		if (this.getJpoSet("SCHEMEJOBTASKRECORD") != null) {
			this.getJpoSet("SCHEMEJOBTASKRECORD").deleteAll(flag);
		}
		if (this.getJpoSet("SCHEMEJOBTESTRECORD") != null) {
			this.getJpoSet("SCHEMEJOBTESTRECORD").deleteAll(flag);
		}
		IJpoSet jposet = this.getJpoSet("SCHEMEJOBTESTRECORD");
		for (int index = 0; index < jposet.count(); index++) {
			IJpo jbjpo = jposet.getJpo(index);
			if (jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null) {
				jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
			}
		}

	}

	@Override
	public void undelete() throws MroException {
		// TODO Auto-generated method stub
		super.undelete();
		if (this.getJpoSet("SCHEMEJOBTASKRECORD") != null) {
			this.getJpoSet("SCHEMEJOBTASKRECORD").undeleteAll();
		}
		if (this.getJpoSet("SCHEMEJOBTESTRECORD") != null) {
			this.getJpoSet("SCHEMEJOBTESTRECORD").undeleteAll();
		}
		IJpoSet jposet = this.getJpoSet("SCHEMEJOBTESTRECORD");
		for (int index = 0; index < jposet.count(); index++) {
			IJpo jbjpo = jposet.getJpo(index);
			if (jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING") != null) {
				jbjpo.getJpoSet("SCHEMEJOBTESTRECORDING").undeleteAll();
			}
		}

	}

	/**
	 * 
	 * 复制任务项
	 * 
	 * @param jpotask
	 *            [参数说明]
	 * @throws MroException
	 * 
	 */
	/*public void copyTaskForUpVersion(IJpo jpotask, IJpo schemeJpo)
			throws MroException {
		// 标准作业指导书编号
		String sopnum = schemeJpo.getString("SOPNUM");
		// 唯一编号（关联使用）
		String tasknum = schemeJpo.getString("TASKNUM");
		// 检修方案编号
		String schemenum = schemeJpo.getString("SCHEMENUM");
		// 标准作业指导书任务项的编号
		String oldTaskNum = jpotask.getString("JOBTASKNUM");
		String newTaskNum = getString("JOBTASKNUM");

		String[] fromAttrs = { "JPNUM", "DESCRIPTION", "SEQ", "REMARK",
				"TASKTIME" };
		setValue(jpotask, fromAttrs, fromAttrs, GWConstant.P_NOVALIDATION);
		setValue("tasknum", tasknum, GWConstant.P_NOVALIDATION);
		setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);

		// 拷贝相关的检查项
		IJpoSet jobtaskrecordSet = jpotask.getJpoSet("$JOBTASKRECORDTMP",
				"JOBTASKRECORD", "TASKNUM='" + oldTaskNum + "'");
		String[] taskrecordattrs = { "DESCRIPTION", "CHECKITEM", "PROJECTSTEPS" };
		IJpoSet newJobTaskrecordSet = getJpoSet("SCHEMEJOBTASKRECORD",
				"SCHEMEJOBTASKRECORD", "1=0");

		for (int i = 0; i < jobtaskrecordSet.count(); i++) {
			IJpo newJpo = newJobTaskrecordSet.addJpo();
			newJpo.setValue(jobtaskrecordSet.getJpo(i), taskrecordattrs,
					taskrecordattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);
		}

		// 拷贝相关的试验项目
		IJpoSet jobtestrecordSet = jpotask.getJpoSet("$JOBTESTRECORDTMP",
				"JOBTESTRECORD", "TASKNUM='" + oldTaskNum + "'");
		String[] testrecordSetattrs = { "DESCRIPTION", "TESTCONTENT",
				"TESTPROJECT" };
		IJpoSet newJobTestrecordSet = getJpoSet("SCHEMEJOBTESTRECORD",
				"SCHEMEJOBTESTRECORD", "1=0");

		for (int i = 0; i < jobtestrecordSet.count(); i++) {
			IJpo newJpo = newJobTestrecordSet.addJpo();
			newJpo.setValue(jobtestrecordSet.getJpo(i), testrecordSetattrs,
					testrecordSetattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);

			// 拷贝试验项目关联的检测项目
			String oldjobtestrecordnum = jobtestrecordSet.getJpo(i).getString(
					"JOBTESTRECORDNUM");
			String newjobtestrecordnum = newJpo
					.getString("SCHEMEJOBTESTRECORDNUM");

			IJpoSet jobtestrecordingSet = jobtestrecordSet.getJpo(i).getJpoSet(
					"$JOBTESTRECORDINGTMP_" + i, "JOBTESTRECORDING",
					"TASKNUM='" + oldjobtestrecordnum + "'");
			String[] testrecordSetattrss = { "DESCRIPTION", "LOC",
					"STANDARDVALUE" };
			IJpoSet newJobTestrecordingSet = newJpo.getJpoSet(
					"SCHEMEJOBTESTRECORDING" + i, "SCHEMEJOBTESTRECORDING",
					"1=0");

			for (int j = 0; j < jobtestrecordingSet.count(); j++) {
				IJpo newJobJpo = newJobTestrecordingSet.addJpo();
				newJobJpo.setValue(jobtestrecordingSet.getJpo(j),
						testrecordSetattrss, testrecordSetattrss,
						GWConstant.P_NOVALIDATION);
				newJobJpo.setValue("TASKNUM", newjobtestrecordnum,
						GWConstant.P_NOVALIDATION);
				newJobJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
				newJpo.setValue("schemenum", schemenum,
						GWConstant.P_NOVALIDATION);
			}
		}
	}
*/
	/**
	 * 
	 * 复制任务项
	 * 
	 * @param jpotask
	 *            [参数说明]
	 * @throws MroException
	 * 
	 */
	/*public void copyTask(IJpo jpotask, IJpo schemeJpo) throws MroException {
		// 标准作业指导书编号
		String sopnum = schemeJpo.getString("SOPNUM");
		// 唯一编号（关联使用）
		String tasknum = schemeJpo.getString("TASKNUM");
		// 检修方案编号
		String schemenum = schemeJpo.getString("SCHEMENUM");
		// 标准作业指导书任务项的编号
		String oldTaskNum = jpotask.getString("TASKNUM");
		String newTaskNum = getString("JOBTASKNUM");

		String[] fromAttrs = { "JPNUM", "DESCRIPTION", "SEQ", "REMARK",
				"TASKTIME" };
		setValue(jpotask, fromAttrs, fromAttrs, GWConstant.P_NOVALIDATION);
		setValue("tasknum", tasknum, GWConstant.P_NOVALIDATION);
		setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);

		// 拷贝相关的检查项
		IJpoSet jobtaskrecordSet = jpotask.getJpoSet("$JOBTASKRECORDTMP",
				"JOBTASKRECORD", "TASKNUM='" + oldTaskNum + "'");
		String[] taskrecordattrs = { "DESCRIPTION", "CHECKITEM", "PROJECTSTEPS" };
		IJpoSet newJobTaskrecordSet = getJpoSet("SCHEMEJOBTASKRECORD",
				"SCHEMEJOBTASKRECORD", "1=0");

		for (int i = 0; i < jobtaskrecordSet.count(); i++) {
			IJpo newJpo = newJobTaskrecordSet.addJpo();
			newJpo.setValue(jobtaskrecordSet.getJpo(i), taskrecordattrs,
					taskrecordattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);
		}

		// 拷贝相关的试验项目
		IJpoSet jobtestrecordSet = jpotask.getJpoSet("$JOBTESTRECORDTMP",
				"JOBTESTRECORD", "TASKNUM='" + oldTaskNum + "'");
		String[] testrecordSetattrs = { "DESCRIPTION", "TESTCONTENT",
				"TESTPROJECT" };
		IJpoSet newJobTestrecordSet = getJpoSet("SCHEMEJOBTESTRECORD",
				"SCHEMEJOBTESTRECORD", "1=0");

		for (int i = 0; i < jobtestrecordSet.count(); i++) {
			IJpo newJpo = newJobTestrecordSet.addJpo();
			newJpo.setValue(jobtestrecordSet.getJpo(i), testrecordSetattrs,
					testrecordSetattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);

			// 拷贝试验项目关联的检测项目
			String oldjobtestrecordnum = jobtestrecordSet.getJpo(i).getString(
					"JOBTESTRECORDNUM");
			String newjobtestrecordnum = newJpo
					.getString("SCHEMEJOBTESTRECORDNUM");

			IJpoSet jobtestrecordingSet = jobtestrecordSet.getJpo(i).getJpoSet(
					"$JOBTESTRECORDINGTMP_" + i, "JOBTESTRECORDING",
					"TASKNUM='" + oldjobtestrecordnum + "'");
			String[] testrecordSetattrss = { "DESCRIPTION", "LOC",
					"STANDARDVALUE" };
			IJpoSet newJobTestrecordingSet = newJpo.getJpoSet(
					"SCHEMEJOBTESTRECORDING" + i, "SCHEMEJOBTESTRECORDING",
					"1=0");

			for (int j = 0; j < jobtestrecordingSet.count(); j++) {
				IJpo newJobJpo = newJobTestrecordingSet.addJpo();
				newJobJpo.setValue(jobtestrecordingSet.getJpo(j),
						testrecordSetattrss, testrecordSetattrss,
						GWConstant.P_NOVALIDATION);
				newJobJpo.setValue("TASKNUM", newjobtestrecordnum,
						GWConstant.P_NOVALIDATION);
				newJobJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
				newJpo.setValue("schemenum", schemenum,
						GWConstant.P_NOVALIDATION);
			}
		}
	}*/
}
