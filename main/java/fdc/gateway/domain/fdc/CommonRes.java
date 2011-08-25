package fdc.gateway.domain.fdc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.ResHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-25
 * Time: ионГ11:30
 * To change this template use File | Settings | File Templates.
 */
public class CommonRes extends BaseBean {
    @XStreamAlias("Head")
    public ResHead head = new ResHead();
}
