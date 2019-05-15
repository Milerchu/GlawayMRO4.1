package com.glaway.sddq.material.itemreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 接收类型字段类
 * 
 * @author zzx
 * @version [版本号, 2018-6-8]
 * @since [物资/领料单]
 */
public class FldGdtype extends JpoField {
	/**
	 * 初始化选择框
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws MroException {
		super.init();
		String type = this.getJpo().getString("GDTYPE");
		if (type != null) {
			if (type.equals("服务")) {
				this.getField("TASKNUM").setUserLookup("MPRTASKFW");

			} else if (type.equals("故障")) {

				this.getField("TASKNUM").setUserLookup("MPRTASKGZ");

			} else if (type.equals("改造")) {

				this.getField("TASKNUM").setUserLookup("MPRTASKGAIZ");

			} else if (type.equals("验证")) {

				this.getField("TASKNUM").setUserLookup("MPRTASKYZ");

			} else if (type.equals("检修")) {

				this.getField("TASKNUM").setUserLookup("MPRTASKJIANX");
			} else {
				this.getField("TASKNUM").setUserLookup("MPRTASKJIANX");
			}
		}

	}

	public void action() throws MroException {
		super.action();
		String type = this.getJpo().getString("GDTYPE");
		// this.getField("TASKNUM").setValueNull();
		if (this.getJpo().getAppName().equals("ITEMREQ")) {
			if (type != null) {

				this.getJpo().setFieldFlag("TASKNUM", GWConstant.S_READONLY,
						false);

				if (type.equals("服务")) {
					this.getField("TASKNUM").setUserLookup("MPRTASKFW");

				} else if (type.equals("故障")) {

					this.getField("TASKNUM").setUserLookup("MPRTASKGZ");

				} else if (type.equals("改造")) {

					this.getField("TASKNUM").setUserLookup("MPRTASKGAIZ");

				} else if (type.equals("验证")) {

					this.getField("TASKNUM").setUserLookup("MPRTASKYZ");

				} else if (type.equals("检修")) {

					this.getField("TASKNUM").setUserLookup("MPRTASKJIANX");
				}
			}/*
			 * else{ if (type.equals("检修")){
			 * 
			 * this.getField("TASKNUM").setUserLookup("MPRTASKJIANX"); }
			 * 
			 * }
			 */
		} else {

			if (type != null) {
				if (type.equals("检修")) {

					this.getField("TASKNUM").setUserLookup("MPRTASKJIANX");
				}

			} else {

				this.getField("TASKNUM").setUserLookup("MPRTASKJIANX");
			}

		}
	}
}