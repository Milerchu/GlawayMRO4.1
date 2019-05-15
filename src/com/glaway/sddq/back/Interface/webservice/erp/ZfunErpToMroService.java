package com.glaway.sddq.back.Interface.webservice.erp;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.glaway.sddq.back.Interface.webservice.erp.jxtask.InfoBack;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.SqnInfoSet;
import com.glaway.sddq.back.Interface.webservice.erp.jxtask.TaskParameter;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroDbzkLineSet;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroJkLineSet;
import com.glaway.sddq.back.Interface.webservice.erp.sxtransfer.ZfunErptoMroResponse;

/**
 * 
 * ERP系统调用MRO系统的接口
 * 
 * @author hyhe
 * @version [版本号, 2018-3-15]
 * @since [产品/模块版本]
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ZfunErpToMroService {

	/**
	 * 接口编号：ERP_MRO_JXWORKORDERIF ERP检修生产订单生成MRO检修工单
	 * 
	 * @param data
	 * @return [参数说明]
	 * 
	 */
	@WebMethod
	@WebResult(name = "strjxtaskMsg")
	public InfoBack Ztfun_toMroCheckRepairOrder(@WebParam(name = "taskParameter") TaskParameter taskParameter);

	/**
	 * 
	 * <功能描述>MRO传递送修单数据到ERP
	 * 
	 * @param data
	 * @return [参数说明]
	 * 
	 */
	@WebMethod
	public ZfunErptoMroResponse Ztfun_ErptoMroSxs(@WebParam(name = "data") String data);

	/**
	 * 缴库单生成 <功能描述>
	 * 
	 * @param zfunerptomrojklineset
	 * @return [参数说明]
	 * 
	 */
	@WebMethod
	public InterfaceReponse Ztfun_ErptoMroJkd(@WebParam(name = "zfunerptomrojklineset") ZfunErptoMroJkLineSet zfunerptomrojklineset);
	
	/**
	 * ERP推送调拨转库单 <功能描述>
	 * 
	 * @param zfunerptomrodbzklineset
	 * @return [参数说明]
	 * 
	 */
	@WebMethod
	public InterfaceReponse Ztfun_ErptoMroDbzkd(@WebParam(name = "zfunerptomrodbzklineset") ZfunErptoMroDbzkLineSet zfunerptomrodbzklineset);
	
	/**
	 * 
	 * @Description ERP或者MES系统向MRO传递生产完工的序列号
	 * @param zfunerptomrojklineset
	 * @return
	 */
	@WebMethod
	@WebResult(name = "backmsg")
	public InfoBack Ztfun_ErptoMroSqn(@WebParam(name = "sqninfoSet") SqnInfoSet sqninfoSet);

}
