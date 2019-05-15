package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 检修方案试验项目Jpo
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-17]
 * @since  [产品/模块版本]
 */
public class SchemeJobTestRecord extends Jpo implements IJpo {

	 /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public void copyTask(IJpo jobtestrecord, IJpo jobtestrecordJpo) throws MroException {
    	//System.out.println(this.getName());
    	//检修范围标准作业指导书编号
    	String sopnum = jobtestrecordJpo.getString("SOPNUM");
    	//检修范围检修方案编号
    	String schemenum = jobtestrecordJpo.getString("SCHEMENUM");    	
    	//检修范围任务项编号
    	String tasknum = jobtestrecordJpo.getString("TASKNUM");
		//标准作业指导书试验项目唯一编号
    	String oldjobtestrecordnum = jobtestrecord.getString("JOBTESTRECORDNUM");
		
		String[] attrs = {"TESTPROJECT", "TESTCONTENT","SEQ","DESCRIPTION"};
 		setValue(jobtestrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
		setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
		setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);
		setValue("tasknum", tasknum, GWConstant.P_NOVALIDATION);
		String newschemejobtestrecordnum = this.getString("SCHEMEJOBTESTRECORDNUM");
		
		
		//复制标准作业指导书检测项目
		IJpoSet jobtestrecordingset = jobtestrecord.getJpoSet("$JOBTESTRECORDINGTMP",
				"JOBTESTRECORDING", "TASKNUM='" + oldjobtestrecordnum + "'");
		
		String[] testrecordingattrs = { "DESCRIPTION", "LOC","STANDARDVALUE","SEQ","UNIT","TESTPROJECT" };
		
		IJpoSet newJobTestrecordingSet = getJpoSet("SCHEMEJOBTESTRECORDINGTMP",
				"SCHEMEJOBTESTRECORDING", "1=0");
		for (int i = 0; i < jobtestrecordingset.count(); i++) {
			
			IJpo newJpo = newJobTestrecordingSet.addJpo();
			newJpo.setValue(jobtestrecordingset.getJpo(i), testrecordingattrs,
					testrecordingattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newschemejobtestrecordnum, GWConstant.P_NOVALIDATION);
		}
	}

	public void copyTaskForUpVersion(IJpo jobtestrecord, IJpo jobtestrecordJpo) throws MroException {
		//检修范围标准作业指导书编号
    	String sopnum = jobtestrecordJpo.getString("SOPNUM");
    	//检修范围检修方案编号
    	String schemenum = jobtestrecordJpo.getString("SCHEMENUM");    	
    	//检修范围任务项编号
    	String tasknum = jobtestrecordJpo.getString("TASKNUM");
		//标准作业指导书试验项目唯一编号
    	String oldjobtestrecordnum = jobtestrecord.getString("SCHEMEJOBTESTRECORDNUM");
		
		String[] attrs = {"TESTPROJECT", "TESTCONTENT","SEQ","DESCRIPTION"};
 		setValue(jobtestrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
		setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
		setValue("schemenum", schemenum, GWConstant.P_NOVALIDATION);
		setValue("tasknum", tasknum, GWConstant.P_NOVALIDATION);
		String newschemejobtestrecordnum = this.getString("SCHEMEJOBTESTRECORDNUM");
		
		
		//复制标准作业指导书检测项目
		IJpoSet jobtestrecordingset = jobtestrecord.getJpoSet("$JOBTESTRECORDINGTMP",
				"SCHEMEJOBTESTRECORDING", "TASKNUM='" + oldjobtestrecordnum + "'");
		
		String[] testrecordingattrs = { "DESCRIPTION", "LOC","STANDARDVALUE","SEQ","UNIT","TESTPROJECT" };
		
		IJpoSet newJobTestrecordingSet = getJpoSet("SCHEMEJOBTESTRECORDINGTMP",
				"SCHEMEJOBTESTRECORDING", "1=0");
		for (int i = 0; i < jobtestrecordingset.count(); i++) {
			
			IJpo newJpo = newJobTestrecordingSet.addJpo();
			newJpo.setValue(jobtestrecordingset.getJpo(i), testrecordingattrs,
					testrecordingattrs, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", sopnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newschemejobtestrecordnum, GWConstant.P_NOVALIDATION);
		}		
	}

	@Override
	public void delete() throws MroException {
		// TODO Auto-generated method stub
		super.delete();
		 if (this.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
         {
             this.getJpoSet("SCHEMEJOBTESTRECORDING").deleteAll();
         }
	}

	@Override
	public void undelete() throws MroException {
		// TODO Auto-generated method stub
		super.undelete();
		 if (this.getJpoSet("SCHEMEJOBTESTRECORDING") != null)
         {
             this.getJpoSet("SCHEMEJOBTESTRECORDING").undeleteAll();
         }
	}

	@Override
	public void init() throws MroException {
		// TODO Auto-generated method stub
		super.init();
		IJpo repairscheme =this.getParent().getParent();
		String status =repairscheme.getString("STATUS");
		if(!status.equals("草稿")){
			  this.getJpoSet("SCHEMEJOBTESTRECORDING").setFlag(GWConstant.S_READONLY, true);
		}
	}
}
