package com.glaway.sddq.base.person.data;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 人员分类字段类
 * 
 * @author zzx
 * @version [版本号, 2018-8-15]
 * @since [产品/模块版本]
 */
public class FldPersonclassification extends JpoField {

	/**
	 * 唯一序列
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws MroException {
		super.init();
		String personclassification = this.getValue();
		if (personclassification.equals("劳务派遣")) {
			this.getJpo().setFieldFlag("COMPANY", GWConstant.S_REQUIRED, true);
		}

	}

	public void action() throws MroException {
		String personclassification = this.getValue();
		if (StringUtil.isStrNotEmpty(personclassification)) {
			if (personclassification.equals("劳务派遣")) {
				this.getJpo().setFieldFlag("COMPANY", GWConstant.S_REQUIRED,
						true);
			}

		}
	}

}
