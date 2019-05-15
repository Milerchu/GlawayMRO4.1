package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * 标准作业指导书试验项目JpoSet
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-16]
 * @since  [产品/模块版本]
 */
public class JobTestRecordSet extends JpoSet implements IJpoSet {

	private static final long serialVersionUID = 1L;

	public JobTestRecord getJpoInstance() {
		return new JobTestRecord();
	}
}
