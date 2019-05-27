package com.glaway.sddq.overhaul.taskorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.overhaul.taskorder.data.JxTestRecord;
/**
 * 
 * 检修部件列表DataBean
 * 
 * @author  chenbin
 * @version  [版本号, 2018-8-29]
 * @since  [产品/模块版本]
 */
public class ProductJpNumDateBean extends DataBean {

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		// TODO Auto-generated method stub
		if (this.getJpo().getField("JPNUM").isValueChanged()) {

			// this.getJpo().getJpoSet("JXTASKRECORD").deleteAll();
			// this.getJpo().getJpoSet("JXTESTRECORD").deleteAll();
			// IJpoSet jposet = this.getJpo().getJpoSet("JXTESTRECORD");
			// for (int ind = 0; ind < jposet.count(); ind++) {
			// IJpo schjpo = jposet.getJpo(ind);
			// schjpo.getJpoSet("JXTESTRECORDITEM").deleteAll();
			// }

			String jpnum = this.getJpo().getString("jpnum");
			String jxtasknum = this.getJpo().getString("JXTASKNUM");
			String sqn = this.getJpo().getString("SQN");
			String assetnum = this.getJpo().getString("ASSETNUM");
			String autonum = this.getJpo().getString("AUTONUM");
			
			//重新选择后删除原有数据
			IJpoSet alljobtaskrecordSet = this.getJpo().getJpoSet(
					"$All_JOBTASKRECORD", "JXTASKRECORD", "autonum='" + autonum	+"'");
			IJpoSet alljobtestrecordSet = this.getJpo().getJpoSet(
					"$All_JOBTESTRECORD", "JXTESTRECORD", "autonum='" + autonum +"'");
			alljobtaskrecordSet.deleteAll();
			alljobtestrecordSet.deleteAll();

			// 检查单记录
			IJpoSet jobTaskrecordSet = this.getJpo().getJpoSet(
					"$JOBTASKRECORD", "JOBTASKRECORD", "JPNUM='" + jpnum + "'");
			// 试验项记录
			IJpoSet jobTestrecordSet = this.getJpo().getJpoSet(
					"$JOBTESTRECORD", "JOBTESTRECORD", "JPNUM='" + jpnum + "'");
			//复制检查单记录
			String[] attrs = { "JXTASKNUM", "SQN", "ASSETNUM", "AUTONUM" };
			String[] vals = { jxtasknum, sqn, assetnum, autonum };
			if (!jobTaskrecordSet.isEmpty()) {
				String[] fromAttrs = { "CHECKITEM", "PROJECTSTEPS", "SEQ",
						"DESCRIPTION" };
				String[] toAttrs = { "CHECKITEM", "PROJECTSTEPS", "SEQ",
						"DESCRIPTION" };
				IJpoSet newjxtaskrecordSet = this.getJpo().getJpoSet(
						"$JXTASKRECORDTMP", "JXTASKRECORD", "1=0");
				newjxtaskrecordSet.duplicate(jobTaskrecordSet, fromAttrs,
						toAttrs, GWConstant.P_NOVALIDATION);
				newjxtaskrecordSet.updateAll(attrs, vals);
			}
			//复制试验项目记录
			if (!jobTestrecordSet.isEmpty()) {
				IJpoSet newjxtestrecordset = this.getJpo().getJpoSet(
						"$JXTESTRECORDTMP", "JXTESTRECORD", "1=0");
				for (int i = 0; i < jobTestrecordSet.count(); i++) {
					IJpo jobtestrecord = jobTestrecordSet.getJpo(i);
					JxTestRecord newJpoTask = (JxTestRecord) newjxtestrecordset
							.addJpo();
					newJpoTask.copyTask(jobtestrecord, this.getJpo());
				}
			}
		}
		this.getAppBean().save();
	}
}
