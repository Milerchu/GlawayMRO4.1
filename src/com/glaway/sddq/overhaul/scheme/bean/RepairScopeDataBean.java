package com.glaway.sddq.overhaul.scheme.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.overhaul.scheme.data.SchemeJobTestRecord;

/**
 * 
 * 检修方案的检修范围 DataBean
 * 
 * @author hyhe
 * @version [版本号, 2018-1-12]
 * @since [产品/模块版本]
 */
public class RepairScopeDataBean extends DataBean {
	public void addEditRowCallBackOk() throws MroException, IOException {
		if ((this.getJpo().isNew() || this.getJpo().toBeUpdated())
				&& this.getJpo().getField("SOPNUM").isValueChanged()) {
			// 删除历史的标准作业指导书任务项
		/*	IJpoSet jposet = this.getJpo().getJpoSet("SCHEMEJOBBOOKTASK");
			for (int ind = 0; ind < jposet.count(); ind++) {
				IJpo schjpo = jposet.getJpo(ind);
				schjpo.getJpoSet("SCHEMEJOBTASKRECORD").deleteAll();
				schjpo.getJpoSet("SCHEMEJOBTESTRECORD").deleteAll();

				IJpoSet schjposet = schjpo.getJpoSet("SCHEMEJOBTESTRECORD");
				for (int sch = 0; sch < schjposet.count(); sch++) {
					IJpo jpo = schjposet.getJpo(sch);
					jpo.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
				}
			}*/
			//删除历史记录
			IJpoSet jposet = this.getJpo().getJpoSet("SCHEMEJOBTESTRECORD");
			for (int ind = 0; ind < jposet.count(); ind++) {
				IJpo schjpo = jposet.getJpo(ind);
				schjpo.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
			}
			this.getJpo().getJpoSet("SCHEMEJOBTASKRECORD").deleteAll();
			this.getJpo().getJpoSet("SCHEMEJOBTESTRECORD").deleteAll();
//			this.getJpo().getJpoSet("SCHEMEJOBBOOKTASK").deleteAll();
			this.getJpo().getJpoSet("SCHEMEOVERHAULMATERIAL").deleteAll();
			this.getJpo().getJpoSet("SCHEMEPERSONDEMAND").deleteAll();
			this.getJpo().getJpoSet("SCHEMETOOLREQUIREMENT").deleteAll();

			// 标准作业指导书编号
			String sopnum = this.getJpo().getString("SOPNUM");
			// 唯一编号（关联使用）
			String tasknum = this.getJpo().getString("TASKNUM");
			// 检修方案编号
			String schemenum = this.getJpo().getString("SCHEMENUM");

			// 标准作业指导书任务项
			//IJpoSet jobtaskSet = this.getJpo().getJpoSet("$JOBBOOKTASK",
					//"JOBBOOKTASK", "JPNUM='" + sopnum + "'");
			// 检修物料清单
			IJpoSet overHaulMaterialSet = this.getJpo().getJpoSet(
					"$OVERHAULMATERIAL", "OVERHAULMATERIAL",
					"TASKNUM='" + sopnum + "'");

			// 人员需求
			IJpoSet persondmandSet = this.getJpo().getJpoSet("$PERSONDEMAND",
					"PERSONDEMAND", "TASKNUM='" + sopnum + "'");

			// 工具需求
			IJpoSet toolsSet = this.getJpo().getJpoSet("$TOOLREQUIREMENT",
					"TOOLREQUIREMENT", "TASKNUM='" + sopnum + "'");
			//检查项
			IJpoSet jobtaskrecordSet = this.getJpo().getJpoSet("$JOBTASKRECORD",
					"JOBTASKRECORD", "JPNUM='" + sopnum + "'");
			//试验项目
			IJpoSet jobtestrecordSet = this.getJpo().getJpoSet("$JOBTESTRECORD",
					"JOBTESTRECORD", "JPNUM='" + sopnum + "'");
									
			String[] attrs = { "jpnum", "tasknum", "schemenum" };
			String[] vals = { sopnum, tasknum, schemenum };
			if(!jobtaskrecordSet.isEmpty()){
				String[] fromAttrs = { "CHECKITEM", "PROJECTSTEPS","SEQ","DESCRIPTION"};
				String[] toAttrs = { "CHECKITEM", "PROJECTSTEPS","SEQ","DESCRIPTION" };
				IJpoSet newjobtaskrecordSet = this.getJpo().getJpoSet(
						"SCHEMEJOBTASKRECORDTMP", "SCHEMEJOBTASKRECORD",
						"1=0");
				newjobtaskrecordSet.duplicate(jobtaskrecordSet,
						fromAttrs, toAttrs, GWConstant.P_NOVALIDATION);
				newjobtaskrecordSet.updateAll(attrs, vals);
			}
			if (!jobtestrecordSet.isEmpty()) {
				IJpoSet newjobtestrecordSet = this.getJpo().getJpoSet(
						"$SCHEMEJOBTESTRECORDTMP", "SCHEMEJOBTESTRECORD", "1=0");
				for (int index = 0; index < jobtestrecordSet.count(); index++) {
					IJpo jobtestrecord = jobtestrecordSet.getJpo(index);
					SchemeJobTestRecord newJpoTask = (SchemeJobTestRecord) newjobtestrecordSet
							.addJpo();
					newJpoTask.copyTask(jobtestrecord, this.getJpo());
				}
			}
			if (!overHaulMaterialSet.isEmpty()) {
				String[] fromAttrs = { "ITEMNUM", "DESCRIPTION", "AMOUNT",
						"MODEL", "UNIT","REMARK" };
				String[] toAttrs = { "ITEMNUM", "DESCRIPTION", "AMOUNT",
						"MODEL", "UNIT","REMARK" };
				IJpoSet newoverHaulMaterialSet = this.getJpo().getJpoSet(
						"SCHEMEOVERHAULMATERIALTMP", "SCHEMEOVERHAULMATERIAL",
						"1=0");
				newoverHaulMaterialSet.duplicate(overHaulMaterialSet,
						fromAttrs, toAttrs, GWConstant.P_NOVALIDATION);
				newoverHaulMaterialSet.updateAll(attrs, vals);
			}
			if (!persondmandSet.isEmpty()) {
				String[] fromAttrs = { "DESCRIPTION", "JOBNAME", "AMOUNT",
						"MEMO","REMARK" };
				String[] toAttrs = { "DESCRIPTION", "JOBNAME", "AMOUNT", "MEMO","REMARK" };
				IJpoSet newPersondmandSet = this.getJpo().getJpoSet(
						"PERSONDEMAND", "SCHEMEPERSONDEMAND", "1=0");
				newPersondmandSet.duplicate(persondmandSet, fromAttrs, toAttrs,
						GWConstant.P_NOVALIDATION);
				newPersondmandSet.updateAll(attrs, vals);
			}
			if (!toolsSet.isEmpty()) {
				String[] fromAttrs = { "DESCRIPTION", "ITEMNUM", "AMOUNT",
						"MODEL", "UNIT", "PORPERTYNUM" };
				String[] toAttrs = { "DESCRIPTION", "ITEMNUM", "AMOUNT",
						"MODEL", "UNIT", "PORPERTYNUM" };
				IJpoSet newtoolSet = this.getJpo().getJpoSet("TOOLREQUIREMENT",
						"SCHEMETOOLREQUIREMENT", "1=0");
				newtoolSet.duplicate(toolsSet, fromAttrs, toAttrs,
						GWConstant.P_NOVALIDATION);
				newtoolSet.updateAll(attrs, vals);
			}

		}
	}
	
}
