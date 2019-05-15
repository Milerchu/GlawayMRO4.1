package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 检测项序号字段类
 * 
 * @author chenbin
 * @version [版本号, 2018-8-17]
 * @since [产品/模块版本]
 */
public class FldJxTestRecordItemSeq extends JpoField {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws MroException {
        IJpo jpo = getJpo();
        if (jpo != null && jpo.getParent() != null && getJpo().isNew()) {
            if (StringUtil.isStrNotEmpty(getJpo().getParent().getString("JXTASKNUM"))) {
                IJpo indexJpo = getJpo().getParent();
                int maxseq = 0;
                if (indexJpo != null) {
                    IJpoSet indexColSet = getJobTestRecord();
                    if (indexColSet != null) {
                        maxseq = (int) indexColSet.max("SEQ");
                        indexColSet.destroy();
                    }
                }
                for (int i = 0, count = getJpo().getJpoSet().count(); i < count; i++) {
                    IJpo other = getJpo().getJpoSet().getJpo(i);
                    if (other != getJpo() && other != null) {
                        if (maxseq < other.getInt("SEQ")) {
                            maxseq = other.getInt("SEQ");
                        }
                    }
                }
                this.getJpo().setValue("seq", (maxseq + 1));
            } else if (StringUtil.isStrNotEmpty(getJpo().getParent().getString("JCTASKNUM"))) {
                IJpo indexJpo = getJpo().getParent();
                int maxseq = 0;
                if (indexJpo != null) {
                    IJpoSet indexColSet = getJcJobTestRecord();
                    if (indexColSet != null) {
                        maxseq = (int) indexColSet.max("SEQ");
                        indexColSet.destroy();
                    }
                }
                for (int i = 0, count = getJpo().getJpoSet().count(); i < count; i++) {
                    IJpo other = getJpo().getJpoSet().getJpo(i);
                    if (other != getJpo() && other != null) {
                        if (maxseq < other.getInt("SEQ")) {
                            maxseq = other.getInt("SEQ");
                        }
                    }
                }
                this.getJpo().setValue("seq", (maxseq + 1));
            }
        }
    }

    private IJpoSet getJobTestRecord() throws MroException {
        IJpoSet indexColSet = null;
        if (getJpo() != null) {
            String jxtestnum = getJpo().getParent().getString("JXTESTNUM");
            indexColSet = getUserServer().getJpoSet("JXTESTRECORDITEM",
                    "JXTESTNUM='" + jxtestnum + "' and " + getJpo().getIdColName() + "!=" + getJpo().getId());
            indexColSet.reset();
        }
        return indexColSet;
    }

    private IJpoSet getJcJobTestRecord() throws MroException {
        IJpoSet indexColSet = null;
        if (getJpo() != null) {
            String jxtestnum = getJpo().getParent().getString("JXTESTNUM");
            indexColSet = getUserServer().getJpoSet("JXTESTRECORDITEM",
                    "JXTESTNUM='" + jxtestnum + "' and " + getJpo().getIdColName() + "!=" + getJpo().getId());
            indexColSet.reset();
        }
        return indexColSet;
    }
}
