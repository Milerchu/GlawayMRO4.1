package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 试验记录Jpo
 * 
 * @author bchen
 * @version [版本号, 2018-1-31]
 * @since [产品/模块版本]
 */
public class JxTestRecord extends Jpo implements IJpo {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void delete(long flag) throws MroException {
		super.delete(flag);
		if (this.getJpoSet("JXTESTRECORDITEM") != null) {
			this.getJpoSet("JXTESTRECORDITEM").deleteAll(flag);
		}
	}

	@Override
	public void undelete() throws MroException {
		super.undelete();
		if (this.getJpoSet("JXTESTRECORDITEM") != null) {
			this.getJpoSet("JXTESTRECORDITEM").undeleteAll();
		}
	}

	public void copyTask(IJpo jobtestrecord, IJpo jpo) throws MroException {
		// TODO Auto-generated method stub
		String jxtasknum = jpo.getString("JXTASKNUM");
		String assetnum = jpo.getString("ASSETNUM");
		String sqn = jpo.getString("SQN");
		String autonum = jpo.getString("AUTONUM");

		String oldjobtestrecordnum = jobtestrecord.getString("JOBTESTRECORDNUM");
	
		String[] attrs = { "TESTPROJECT", "TESTCONTENT","SEQ","DESCRIPTION" };
		setValue(jobtestrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
		setValue("JXTASKNUM", jxtasknum, GWConstant.P_NOVALIDATION);
		setValue("ASSETNUM", assetnum, GWConstant.P_NOVALIDATION);
		setValue("SQN", sqn, GWConstant.P_NOVALIDATION);
		setValue("AUTONUM", autonum, GWConstant.P_NOVALIDATION);
		String newjxtestnum = this.getString("JXTESTNUM");
		// 复制标准作业指导书检测项目
		IJpoSet jobtestrecordingset = jobtestrecord.getJpoSet(
				"$JOBTESTRECORDINGTMP", "JOBTESTRECORDING", "TASKNUM='"
						+ oldjobtestrecordnum + "'");

		String[] testrecordingattrs = { "LOC","STANDARDVALUE","SEQ","TESTPROJECT","UNIT" };

		IJpoSet newjxtestrecorditemSet = getJpoSet("JXTESTRECORDITEMTMP",
				"JXTESTRECORDITEM", "1=0");
		for (int i = 0; i < jobtestrecordingset.count(); i++) {

			IJpo newJpo = newjxtestrecorditemSet.addJpo();
			newJpo.setValue(jobtestrecordingset.getJpo(i), testrecordingattrs,
					testrecordingattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JXTESTNUM", newjxtestnum,
					GWConstant.P_NOVALIDATION);
			newJpo.setValue("ASSETNUM", assetnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("SQN", sqn, GWConstant.P_NOVALIDATION);
		}
	}

}
