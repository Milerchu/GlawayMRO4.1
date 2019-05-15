package com.glaway.sddq.tools.ConTask;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 检查非序列号件assetpartnum是否为空定时任务定制类
 * 
 * @author zhuhao
 * @version [版本号, 2019年4月27日]
 * @since [产品/模块版本]
 */
public class CheckAssetPartNumCronTask extends BaseStatefulJob {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 8590812542692513941L;

	@Override
	public void execute() throws MroException {

		super.execute();

		// 非序列号件编号为空的jposet
		IJpoSet assetPartSet = MroServer.getMroServer().getSysJpoSet(
				"ASSETPART", "assetpartnum is null");
		if (assetPartSet != null && assetPartSet.count() > 0) {
			for (int idx = 0; idx < assetPartSet.count(); idx++) {
				IJpo assetPart = assetPartSet.getJpo(idx);
				// 重新编码
				assetPart.setValue("ASSETPARTNUM", WorkorderUtil.getSysAutoKey(
						"ASSETPARTNUM", "CRRC", "ELEC"));
			}
			assetPartSet.save();

		}
	}

}
