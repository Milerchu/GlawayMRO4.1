package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

public class JobTaskRecordSet extends JpoSet implements IJpoSet {

	private static final long serialVersionUID = 1L;

	public JobTaskRecord getJpoInstance() {
		return new JobTaskRecord();
	}
}
