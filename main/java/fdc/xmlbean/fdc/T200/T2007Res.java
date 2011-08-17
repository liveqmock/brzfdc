package fdc.xmlbean.fdc.T200;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.ResHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T2007Res extends BaseBean {
    @XStreamAlias("Head")
    public ResHead head = new ResHead();
}
