package com.glaway.sddq.material.assetlifecommon;

import java.text.ParseException;

import com.glaway.mro.jpo.IJpo;
import com.glaway.sddq.material.caritemlifecommom.CommomCarItemLifeMethod;

/**
 * 
 * <一物一档公共类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-4-15]
 * @since  [产品/模块版本]
 */
public class CommomCarItemLife {
	private static CommomCarItemLife wfCtrl = null;;

	private CommomCarItemLife() {
	}

	public synchronized static CommomCarItemLife getInstance() {
		if (wfCtrl == null) {
			wfCtrl = new CommomCarItemLife();
		}
		return wfCtrl;
	}
	/**
	 * 
	 * <//一物一档上下车判断计算方法>
	 * @param asset
	 * @param type [asset表当前jpo,计算类型（上车、下车、入库、出库）]
	 * @throws ParseException 
	 *
	 */
	public static void UPORDOWNCAR(IJpo asset,String type,String ancestor) {
		if(type.equalsIgnoreCase("上车")){
			CommomCarItemLifeMethod.UPCAR(asset,ancestor);
		}
		if(type.equalsIgnoreCase("下车")){
			CommomCarItemLifeMethod.DOWNCAR(asset,ancestor);
		}
	}
	/**
	 * 
	 * <//一物一档出入库判断计算方法>
	 * @param asset
	 * @param type [asset表当前jpo,计算类型（上车、下车、入库、出库）]
	 * @throws ParseException 
	 *
	 */
	public static void INOROURLOCATION(IJpo asset,String type) {
		if(type.equalsIgnoreCase("入库")){
			CommomCarItemLifeMethod.INLOCATION(asset);
		}
		if(type.equalsIgnoreCase("出库")){
			CommomCarItemLifeMethod.OUTLOCATION(asset);
		}
	}
}
