package com.glaway.sddq.base.revisit.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.Jpo;

/**
 * @ClassName: ProblemMg
 * @Description: TODO
 * @author sqzhou
 * @date 2017-2-9 下午02:12:26
 * 
 */

public class ProblemMg extends Jpo implements IJpo {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();
		String status = this.getString("STATUS");
		if ("已关闭".equalsIgnoreCase(status)) {
			this.setFlag(7L, true);
		}
	}

	public void add() throws MroException {
		super.add();
		IJpo owner = getParent();// 获取父级MBO
		if (owner != null) {
			setValue("RECORDNUM", owner.getString("RECORDNUM"));
			setValue("HANDLER", owner.getString("PROJECTLEADER"));
			setValue("HANDLEQK", owner.getString("PROCESSRESULT"));
		}
	}
}
