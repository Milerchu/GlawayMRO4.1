package com.glaway.sddq.material.frepair.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

public class FldSqn extends JpoField {
	    
	    /**
	     * serialVersionUID
	     */
	    private static final long serialVersionUID = 6714808953825812433L;
	    
	    /**
	     * @throws MroException
	     */
	    @Override
	    public void init()
	        throws MroException
	    {
	        super.init();
	        String[] thisAttrs = {this.getFieldName(),"itemnum","assetnum"};
	        String[] srcAttrs = {"sqn","itemnum","assetnum"};
	        setLookupMap(thisAttrs, srcAttrs);
	    }
	    
	    /**
	     * 获取可选择列表数据
	     * @return
	     * @throws MroException
	     */
	    @Override
	    public IJpoSet getList()
	        throws MroException
	    {
	        setListObject("asset");
	      
	        String listSql = "";
	            listSql = " type='3' and status='故障' and sqn not in (select sqn from FREPAIR where status!='完成') and sqn not in (select sqn from asset where sqn like 'LS%')";
	            setListWhere(listSql);      
	        return super.getList();
	    }

		@Override
		public void action() throws MroException {
			super.action();
			String sqn=this.getValue();
			IJpoSet EXCHANGERECORDset =MroServer.getMroServer().getJpoSet("EXCHANGERECORD", MroServer.getMroServer().getSystemUserServer());
			EXCHANGERECORDset.setQueryWhere("taskordernum in (select ORDERNUM from workorder where status='处理中') and sqn='"+sqn+"'");
			EXCHANGERECORDset.reset();
			String EXCHANGERECORDID=EXCHANGERECORDset.getJpo(0).getString("EXCHANGERECORDID");
			String taskordernum=EXCHANGERECORDset.getJpo(0).getString("taskordernum");
			String FAILURENUM=EXCHANGERECORDset.getJpo(0).getString("FAILURENUM");
			IJpoSet FAILURELIBset =MroServer.getMroServer().getJpoSet("FAILURELIB", MroServer.getMroServer().getSystemUserServer());
			FAILURELIBset.setQueryWhere("FAILURENUM='"+FAILURENUM+"'");
			FAILURELIBset.reset();
			String REPAIRPROCESS=FAILURELIBset.getJpo(0).getString("REPAIRPROCESS");
			String CARNUM=FAILURELIBset.getJpo(0).getString("CARNUM");
			String CARSECTIONNUM=FAILURELIBset.getJpo(0).getString("CARSECTIONNUM");
			String FAILUREDESC=FAILURELIBset.getJpo(0).getString("FAILUREDESC");
			String FAULTCODE=FAILURELIBset.getJpo(0).getString("FAULTCODE");
			String CAUSECODE=FAILURELIBset.getJpo(0).getString("CAUSECODE");
			IJpoSet workorderset =MroServer.getMroServer().getJpoSet("workorder", MroServer.getMroServer().getSystemUserServer());
			workorderset.setQueryWhere("ORDERNUM='"+taskordernum+"'");
			workorderset.reset();
			String models=workorderset.getJpo(0).getString("models");
			this.getJpo().setValue("EXCHANGERECORDID", EXCHANGERECORDID);
			this.getJpo().setValue("tasknum", taskordernum);
			this.getJpo().setValue("FAILURENUM", FAILURENUM);
			
			this.getJpo().setValue("REPAIRPROCESS", REPAIRPROCESS);
			this.getJpo().setValue("CARNUM", CARNUM);
			this.getJpo().setValue("models", models);
			this.getJpo().setValue("CARSECTIONNUM", CARSECTIONNUM);
			this.getJpo().setValue("FAILUREDESC", FAILUREDESC);
			this.getJpo().setValue("FAULTCODE", FAULTCODE);
			this.getJpo().setValue("CAUSECODE", CAUSECODE);
		}
	    
	}
