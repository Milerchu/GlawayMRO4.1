package com.glaway.sddq.service.trcheckorder.data;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.util.GWConstant;

/**
 * 售后tr检查单明细模板表序号字段类
 */
public class FldSequence extends JpoField {

	private static final long serialVersionUID = -6864734848858351000L;

	@Override
	public void validate() throws MroException {
		super.validate();
		MroType sequence = getInputMroType();
		IJpoSet checkdeModel = this.getJpo().getJpoSet();

		List<String> seq = new ArrayList<String>();
		for (int i = 0; i < checkdeModel.count(GWConstant.P_COUNT_DATABASE); i++) {
			seq.add(checkdeModel.getJpo(i).getString("SEQUENCE"));
		}
		if (seq.contains(sequence.toString())) {
			throw new MroException("序号重复");
		}
	}
}
