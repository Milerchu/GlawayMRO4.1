package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 配件申请单行Jpo
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-7]
 * @since [GlawayMro4.0/配件申请]
 */
public class MRLine extends Jpo implements IJpo, FixedLoggers {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
	}

	/**
	 * 设置行号及赋值配件申请编号
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		// 设置行号
		IJpoSet thisSet = getJpoSet();
		setValue("MRLINENUM", (int) thisSet.max("MRLINENUM") + 10);
		// 设置申请单号
		if (getParent() != null) {
			setValue("MRNUM", getParent().getString("MRNUM"));
		}
	}

	/**
	 * 级联删除相关数据
	 * 
	 * @param flag
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		getJpoSet("MRLINETRANSFER").deleteAll(
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		super.delete(flag);
	}
}
