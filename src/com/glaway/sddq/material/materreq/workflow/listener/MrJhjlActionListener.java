package com.glaway.sddq.material.materreq.workflow.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.glaway.mro.app.system.workflow.util.WfControlUtil;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
/**
 * 
 * <配件申请流程计划经理审批流程走向判断控制类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class MrJhjlActionListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		IJpo curJpo = WfControlUtil.getJpoByProInstId(execution
				.getProcessInstanceId());
		if (curJpo == null) {
			curJpo = WfControlUtil.getJpoByUid(execution);
		}
		if(curJpo != null){
			IJpoSet HMRLINETRANSLINESET=curJpo.getJpoSet("MRLINETRANSFER");
			if(HMRLINETRANSLINESET.isEmpty()){
				throw new MroException("计划经理未分配，不能通过，如需通过请分配物料");
			}else{
				boolean flg=true;
				IJpoSet qtyMRLINETRANSLINESET=curJpo.getJpoSet("MRLINETRANSLINE");//主管分配的
				qtyMRLINETRANSLINESET.setUserWhere("transtype='计划经理协调'");
				qtyMRLINETRANSLINESET.reset();
				for(int i=0;i<qtyMRLINETRANSLINESET.count();i++){
					IJpo qtyMRLINETRANSLINE=qtyMRLINETRANSLINESET.getJpo(i);
					String itemnum=qtyMRLINETRANSLINE.getString("itemnum");
					double zrqty=qtyMRLINETRANSLINE.getDouble("transferqty");
					IJpoSet qtyMRLINETRANSFERSET=curJpo.getJpoSet("MRLINETRANSFER");//计划经理分配的
					qtyMRLINETRANSFERSET.setUserWhere("selecttype='匹配' and itemnum='"+itemnum+"'");
					qtyMRLINETRANSFERSET.reset();
					if(qtyMRLINETRANSFERSET.isEmpty()){
						flg=false;
					}else{
						double sumjlqty=0.00;
						for(int j=0;j<qtyMRLINETRANSFERSET.count();j++){
							String transtype=qtyMRLINETRANSFERSET.getJpo(j).getString("transtype");
							if(transtype.equalsIgnoreCase("下达计划")){
								sumjlqty+=qtyMRLINETRANSFERSET.getJpo(j).getDouble("JHQTY");
							}else{
								sumjlqty+=qtyMRLINETRANSFERSET.getJpo(j).getDouble("transferqty");
							}
						}
						if(zrqty>sumjlqty){
							flg=false;
						}
					}
				}
				if(flg){
					IJpoSet MRLINETRANSLINESET=curJpo.getJpoSet("MRLINETRANSFER");
					MRLINETRANSLINESET.setUserWhere("TRANSTYPE='下达计划'");
					MRLINETRANSLINESET.reset();
					if (MRLINETRANSLINESET.count()>0) {
						execution.setVariable("approved", "2");
					} else {
						IJpoSet TMRLINETRANSLINESET=curJpo.getJpoSet("MRLINETRANSFER");
						TMRLINETRANSLINESET.setUserWhere("TRANSTYPE in ('中心库调拨','计划交付后发货','中心库调拨后下达计划')");
						TMRLINETRANSLINESET.reset();
						if(TMRLINETRANSLINESET.count()>0){
							execution.setVariable("approved", "1");
						}else{
							execution.setVariable("approved", "3");
						}
						
					}
				}else{
					throw new MroException("计划经理未完全分配物料，不能通过");
				}

			}
		}
	}

}
