package com.glaway.sddq.overhaul.jctaskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
/**
 * 
 * 交车工单检修方案字段类
 * 
 * @author  chenbin
 * @version  [版本号, 2018-9-3]
 * @since  [产品/模块版本]
 */
public class FldJcSchemeNum extends JpoField {

    private static final long serialVersionUID = 1L;
    
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("REPAIRSCHEME");
        IJpo jpo = this.getJpo();
        String cmodel = jpo.getString("CMODEL");
        String repairProcess = jpo.getString("REPAIRPROCESS");
        this.setListWhere("CMODEL='"+StringUtil.getSafeSqlStr(cmodel)+"' and REPAIRPROCESS='"+StringUtil.getSafeSqlStr(repairProcess)+"' and STATUS='已发布'");
        return super.getList();
    }
    
    
    @Override
	public void action() throws MroException {
    	
		String repairmethod = this.getJpo().getString("REPAIRMETHOD");
		if (this.getJpo().getField("REPAIRMETHOD").isValueChanged()){
			IJpoSet jposet = this.getJpo().getJpoSet("JXTESTRECORD");
			for (int ind = 0; ind < jposet.count(); ind++) {
				IJpo schjpo = jposet.getJpo(ind);
				schjpo.getJpoSet("JXTESTRECORDITEM").deleteAll();
			}
			this.getJpo().getJpoSet("JXTESTRECORD").deleteAll();
			if(StringUtil.isStrNotEmpty(repairmethod)){
			String jctasknum = this.getJpo().getString("JCTASKNUM");
			IJpoSet jcjobtestrecordSet = MroServer.getMroServer().getSysJpoSet(
					"JCJOBTESTRECORD");
			jcjobtestrecordSet.setQueryWhere("SCHEMENUM='" + repairmethod + "'");
			if (!jcjobtestrecordSet.isEmpty()) {
			    IJpoSet jxtestrecordSet = this.getJpo()
						.getJpoSet("$JXTESTRECORD_",
								"JXTESTRECORD", "1=2");
				for (int index = 0; index < jcjobtestrecordSet.count(); index++) {
					IJpo jcjobtestrecord = jcjobtestrecordSet.getJpo(index);
					String testproject = jcjobtestrecord
							.getString("TESTPROJECT");
					String testcontent = jcjobtestrecord
							.getString("TESTCONTENT");
					String description = jcjobtestrecord.getString("DESCRIPTION");
						String seq = jcjobtestrecord.getString("SEQ");
						IJpo jxtestrecord = jxtestrecordSet.addJpo();
						String jxtestnum = jxtestrecord.getString("JXTESTNUM");
						jxtestrecord.setValue("SEQ", seq,
								GWConstant.P_NOVALIDATION);
						jxtestrecord.setValue("TESTPROJECT", testproject,
								GWConstant.P_NOVALIDATION);
						jxtestrecord.setValue("TESTCONTENT", testcontent,
								GWConstant.P_NOVALIDATION);
						jxtestrecord.setValue("DESCRIPTION", description,
								GWConstant.P_NOVALIDATION);
						jxtestrecord.setValue("JCTASKNUM", jctasknum,
								GWConstant.P_NOVALIDATION);
						IJpoSet jcjobtestrecordingSet = jcjobtestrecord
								.getJpoSet("JCJOBTESTRECORDING");
						for (int j = 0; j < jcjobtestrecordingSet.count(); j++) {
							IJpo jcjobtestrecording = jcjobtestrecordingSet
									.getJpo(j);
							String loc = jcjobtestrecording.getString("LOC");
							String standardvalue = jcjobtestrecording
									.getString("STANDARDVALUE");
							String seqs = jcjobtestrecording.getString("SEQ");
							String testprojects = jcjobtestrecording
									.getString("TESTPROJECT");
							String unit = jcjobtestrecording.getString("UNIT");

							IJpoSet jxtestrecorditemset = jxtestrecord
									.getJpoSet("$JXTESTRECORDITEM" + index + j,
											"JXTESTRECORDITEM", "1=2");
							IJpo jxtestrecorditem = jxtestrecorditemset
									.addJpo();
							jxtestrecorditem.setValue("UNIT", unit,
									GWConstant.P_NOVALIDATION);
							jxtestrecorditem.setValue("TESTPROJECT",
									testprojects, GWConstant.P_NOVALIDATION);
							jxtestrecorditem.setValue("TESTLOCATION", loc,
									GWConstant.P_NOVALIDATION);
							jxtestrecorditem.setValue("STANDARDVALUE",
									standardvalue, GWConstant.P_NOVALIDATION);
							jxtestrecorditem.setValue("JXTESTNUM", jxtestnum,
									GWConstant.P_NOVALIDATION);
							jxtestrecorditem.setValue("JCTASKNUM", jctasknum,
									GWConstant.P_NOVALIDATION);
							jxtestrecorditem.setValue("SEQ", seqs,
									GWConstant.P_NOVALIDATION);
						}
					}
			}			}
		}
	}
}
