package com.glaway.sddq.material.locbin.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <仓位表货架类别字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldShelvestype extends JpoField {
	/**
	 * 设置仓位编码
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();

		if (this.getValue().equalsIgnoreCase("高位货架")) {
			this.getJpo().setValue("shelvesnum", "", GWConstant.P_NOACTION);
			this.getJpo().setValue("binrownum", "", GWConstant.P_NOACTION);
			this.getJpo().setValue("bincolnum", "", GWConstant.P_NOACTION);
			this.getJpo().setFieldFlag("layer", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("layer", "", GWConstant.P_NOACTION);
			this.getJpo().setFieldFlag("layer", GWConstant.S_READONLY, true);
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
				shelvesnum = shelvesnum1.toString();
			}
			if (layer1 != 0) {
				layer = layer1.toString();
			}
			if (binrownum1 != 0) {
				binrownum = binrownum1.toString();
			}
			if (clonum1 != 0) {
				clonum = clonum1.toString();
			}
			if (!shelvestype.equalsIgnoreCase("")
					&& !shelvesnum.equalsIgnoreCase("")
					&& !binrownum.equalsIgnoreCase("")
					&& !clonum.equalsIgnoreCase("")) {
				String binnum = "GW-1-" + shelvesnum + "-" + binrownum + "-"
						+ clonum + "";
				this.getJpo().setValue("binnum", binnum);

			} else {
				this.getJpo().setValue("binnum", "");
			}
		}
		if (this.getValue().equalsIgnoreCase("阁楼货架")) {
			this.getJpo().setValue("shelvesnum", "", GWConstant.P_NOACTION);
			this.getJpo().setValue("binrownum", "", GWConstant.P_NOACTION);
			this.getJpo().setValue("bincolnum", "", GWConstant.P_NOACTION);
			this.getJpo().setFieldFlag("layer", GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("layer", GWConstant.S_REQUIRED, true);
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
			if (!shelvestype.equalsIgnoreCase("")
					&& !layer.equalsIgnoreCase("")
					&& !shelvesnum.equalsIgnoreCase("")
					&& !binrownum.equalsIgnoreCase("")
					&& !clonum.equalsIgnoreCase("")) {
				String binnum = "GL-" + layer + "-" + shelvesnum + "-"
						+ binrownum + "-" + clonum + "";
				this.getJpo().setValue("binnum", binnum);
			} else {
				this.getJpo().setValue("binnum", "");
			}
		}
	}

}
