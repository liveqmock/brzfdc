package fdc.gateway.xmlbean.fdc.T000;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.gateway.xmlbean.BaseBean;
import fdc.gateway.xmlbean.ResHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T0004Res extends BaseBean {
    @XStreamAlias("Head")
    public ResHead head = new ResHead();
}
