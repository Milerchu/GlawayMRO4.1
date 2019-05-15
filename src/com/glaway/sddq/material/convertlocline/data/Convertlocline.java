package com.glaway.sddq.material.convertlocline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.Jpo;

/**
 * 
 * <调拨转库单行JPO>
 * 
 * @author public2795
 * @version [版本号, 2019-1-7]
 * @since [产品/模块版本]
 */
public class Convertlocline extends Jpo {

	/**
	 * 判断行信息只读，必填
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		if (!this.isNew()) {
			if (!this.getJpoSet("locationIx").isEmpty()) {// 仓位判断
				boolean islocbin = this.getBoolean("locationix.islocbin");
				if (islocbin == true) {
					this.setFieldFlag("BINNUM", 64L, true);
				} else {
					this.setFieldFlag("BINNUM", 7L, true);

				}
			}
			if (!this.getJpoSet("itemnumix").isEmpty()) {
				boolean ISTURNOVER = this.getBoolean("itemnumix.ISTURNOVER");// 序列号判断
				boolean ISLOT = this.getBoolean("itemnumix.ISLOT");// 批次号判断
				if (ISTURNOVER == true) {
					this.setFieldFlag("SQN", 64L, true);
					this.setFieldFlag("LOTNUM", 7L, true);
					this.setFieldFlag("QTY", 7L, true);
				}

				if (ISLOT == true) {
					this.setFieldFlag("SQN", 7L, true);
					this.setFieldFlag("LOTNUM", 64L, true);
					this.setFieldFlag("QTY", 64L, true);

				}
				if (ISTURNOVER == false && ISLOT == false) {
					this.setFieldFlag("SQN", 7L, true);
					this.setFieldFlag("LOTNUM", 7L, true);
					this.setFieldFlag("QTY", 64L, true);

				}

			}
			if (getString("status").equals("部分接收")
					|| getString("status").equals("已接收")) {
				String[] attr = { "BINNUM", "SQN", "LOTNUM", "QTY" };
				this.setFieldFlag(attr, 7L, true);

			}

		}
	}
}
