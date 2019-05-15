package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;
/**
 * 
 * 标准作业指导书试验项目Jpo
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-16]
 * @since  [产品/模块版本]
 */
public class JobTestRecord extends Jpo implements IJpo{
	
	private static final long serialVersionUID = 1L;
	
	
	public void jcopyTask(IJpo jobtestrecord, IJpo newjp) throws MroException {
		
		String jpnum = newjp.getString("JPNUM");
		String oldjobtestrecordnum=jobtestrecord.getString("JOBTESTRECORDNUM");
		String[] attrs = { "TESTPROJECT", "TESTCONTENT","SEQ","DESCRIPTION"};
		setValue(jobtestrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
		setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);		
		String newjobtestrecordnum = this.getString("JOBTESTRECORDNUM");

		//复制标准作业指导书检测项目
		IJpoSet jobtestrecordingset = jobtestrecord.getJpoSet(
				"$JOBTESTRECORDING", "JOBTESTRECORDING", "TASKNUM='"
						+ oldjobtestrecordnum + "'");
		String[] jobtestrecording = { "LOC", "STANDARDVALUE","SEQ","TESTPROJECT","UNIT" };
		IJpoSet newjobtestrecordingset = getJpoSet("JOBTESTRECORDING",
				"JOBTESTRECORDING", "1=0");
		for (int i = 0; i < jobtestrecordingset.count(); i++) {
			
			IJpo newJpo = newjobtestrecordingset.addJpo();
			newJpo.setValue(jobtestrecordingset.getJpo(i), jobtestrecording,
					jobtestrecording, GWConstant.P_NOVALIDATION);
			newJpo.setValue("JPNUM", jpnum, GWConstant.P_NOVALIDATION);
			newJpo.setValue("TASKNUM", newjobtestrecordnum, GWConstant.P_NOVALIDATION);
		}
	}


	@Override
	public void delete() throws MroException {
		super.delete();
		 if (this.getJpoSet("JOBTESTRECORDING") != null)
	        {
	            this.getJpoSet("JOBTESTRECORDING").deleteAll();
	        }
	}


	@Override
	public void undelete() throws MroException {
		// TODO Auto-generated method stub
		super.undelete();
		   if (this.getJpoSet("JOBTESTRECORDING") != null)
	        {
	            this.getJpoSet("JOBTESTRECORDING").undeleteAll();
	        }
	}
	
	

}
