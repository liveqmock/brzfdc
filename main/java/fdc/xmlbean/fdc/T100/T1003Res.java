package fdc.xmlbean.fdc.T100;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.xmlbean.BaseBean;
import fdc.xmlbean.ResHead;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 */
@XStreamAlias("root")
public class T1003Res extends BaseBean {
    @XStreamAlias("Head")
    public ResHead head = new ResHead();

    @XStreamAlias("Param")
    public Param param = new Param();

    public static class Param {
        public String BankSerial = "";
    }
}
