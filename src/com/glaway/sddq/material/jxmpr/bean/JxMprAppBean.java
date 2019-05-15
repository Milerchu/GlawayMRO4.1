package com.glaway.sddq.material.jxmpr.bean;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.StringUtil;

/**
 * 检修领料单AppBean
 * 
 * @author zzx
 * @version [版本号, 2018-7-19]
 * @since [物资/检修领料单]
 */
public class JxMprAppBean extends AppBean {
	/**
	 * 发送流程前判断行是否为空以及数量是否为空
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public int ROUTEWF() throws Exception {
		// TODO Auto-generated method stub
		boolean nullFlag = false;
		boolean nullFlagmove = false;
		IJpo mprJpo = getJpo();
		boolean plan = mprJpo.getBoolean("plan");
		String type = mprJpo.getString("type");
		IJpoSet mprlineset = mprJpo.getJpoSet("mprline");
		if (plan) {
			if (type.equalsIgnoreCase("领料")) {
				if (!mprlineset.isEmpty()) {
					for (int index = 0; index < mprlineset.count(); index++) {
						String qty = mprlineset.getJpo(index).getString("qty");
						String MOVEREASON = mprlineset.getJpo(index).getString(
								"MOVEREASON");
						if (StringUtil.isStrEmpty(qty)) {
							nullFlag = true;
							break;
						} else if (StringUtil.isStrEmpty(MOVEREASON)) {
							nullFlagmove = true;
							break;
						}
					}
					if (nullFlag) {
						throw new AppException("mprline", "qtyisnull");
					} else if (nullFlagmove) {
						throw new AppException("mprline", "movereason");
					}
				}
			} else {
				if (!mprlineset.isEmpty()) {
					for (int index = 0; index < mprlineset.count(); index++) {
						String qty = mprlineset.getJpo(index).getString("qty");
						if (StringUtil.isStrEmpty(qty)) {
							nullFlag = true;
							break;
						}
					}
					if (nullFlag) {
						throw new AppException("mprline", "qtyisnull");
					}
				}
			}
		} else {
			if (!mprlineset.isEmpty()) {
				for (int index = 0; index < mprlineset.count(); index++) {
					String qty = mprlineset.getJpo(index).getString("qty");
					if (StringUtil.isStrEmpty(qty)) {
						nullFlag = true;
						break;
					}
				}
				if (nullFlag) {
					throw new AppException("mprline", "qtyisnull");
				}
			}
		}
		if(type.equalsIgnoreCase("领料")){
			if(!mprlineset.isEmpty()){
				for(int i=0;i<mprlineset.count();i++){
					String itemnum=mprlineset.getJpo(i).getString("itemnum");
					double qty=mprlineset.getJpo(i).getDouble("qty");
					double curbal=mprlineset.getJpo(i).getDouble("curbal");
					double kyqty=curbal-qty;
					if(kyqty<0){
						throw new MroException("物料编码："+itemnum+"的领用数量大于库存余量");
					}
				}
			}
		}
		return super.ROUTEWF();
	}
}