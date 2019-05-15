package com.glaway.sddq.material.locbin.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <仓位表层级字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldLayer extends JpoField {
	/**
	 * 设置仓位编码
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String shelvestype = this.getJpo().getString("shelvestype");
		Integer shelvesnum1 = this.getJpo().getInt("shelvesnum");
		Integer layer1 = this.getJpo().getInt("layer");
		Integer binrownum1 = this.getJpo().getInt("binrownum");
		Integer clonum1 = this.getJpo().getInt("bincolnum");
		String shelvesnum = "";
		String layer = "";
		String binrownum = "";
		String clonum = "";
		if (shelvesnum1 != 0) {
			if (shelvesnum1 < 10) {
				shelvesnum = "0" + shelvesnum1.toString();
			} else {
				shelvesnum = shelvesnum1.toString();
			}
		}
		if (layer1 != 0) {
			layer = layer1.toString();
		}
		if (binrownum1 != 0) {
			if (binrownum1 < 10) {
				binrownum = "0" + binrownum1.toString();
			} else {
				binrownum = binrownum1.toString();
			}
		}
		if (clonum1 != 0) {
			if (binrownum1 < 10) {
				clonum = "0" + clonum1.toString();
			} else {
				clonum = clonum1.toString();
			}
		}
		if (!shelvestype.equalsIgnoreCase("") && !layer.equalsIgnoreCase("")
				&& !shelvesnum.equalsIgnoreCase("")
				&& !binrownum.equalsIgnoreCase("")
				&& !clonum.equalsIgnoreCase("")) {
			String binnum = "GL-" + layer + "-" + shelvesnum + "-" + binrownum
					+ "-" + clonum + "";
			this.getJpo().setValue("binnum", binnum);
		} else {
			this.getJpo().setValue("binnum", "");
		}
	}

}
