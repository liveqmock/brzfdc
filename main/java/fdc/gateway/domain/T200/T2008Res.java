package fdc.gateway.domain.T200;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.ResHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T2008Res extends BaseBean {
    @XStreamAlias("Head")
    public ResHead head = new ResHead();
}
