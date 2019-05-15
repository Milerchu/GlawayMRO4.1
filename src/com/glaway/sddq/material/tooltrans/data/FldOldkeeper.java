package com.glaway.sddq.material.tooltrans.data;

import java.io.IOException;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * <工具交接表现保管人字段类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldOldkeeper extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() throws MroException {

		// if(this.isValueChanged()){
		// 删除原有的数据
		IJpoSet devicSetTmp = this.getJpo().getJpoSet("DEVICETRANS");
		devicSetTmp.deleteAll();
		// 增加现有的数据
		IJpoSet devictransSet = MroServer.getMroServer().getJpoSet(
				"DEVICEINFO", MroServer.getMroServer().getSystemUserServer());
		String oldkeeper = getInputMroType().asString();

		String tooltransnum = this.getJpo().getString("tooltransnum");
		devictransSet.setUserWhere("KEEPER='" + oldkeeper + "'");
		devictransSet.reset();
		IJpoSet devicSet = this.getJpo().getJpoSet("DEVICETRANS");
		for (int j = 0; j < devictransSet.count(); j++) {
			IJpo devic = devictransSet.getJpo(j);
			IJpo detra = devicSet.addJpo();
			detra.setValue("TOOLNUM", devic.getString("TOOLNUM"));
			detra.setValue("ITEMNUM", devic.getString("ITEMNUM"));
			detra.setValue("ASSETNUM", devic.getString("ASSETNUM"));
			detra.setValue("TRANSQTY", devic.getString("QTY"));
			detra.setValue("OLDKEEPER", devic.getString("KEEPER"));
			detra.setValue("STATUS", devic.getString("STATUS"));
			detra.setValue("tooltransnum", tooltransnum);
		}
		devictransSet.save();
		// }
		super.action();
		// this.getJpo().getJpoSet("DEVICETRANS").reset();
		try {
			this.getJpo().getJpoSet().getUserServer().getMroSession()
					.getCurrentPage().getAppBean().reloadPage();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
