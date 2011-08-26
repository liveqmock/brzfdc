package fdc.gateway.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import fdc.gateway.utils.BiRtnCode;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-10
 * Time: ����4:15
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("root")
public class ResHead {
    public String RetCode = BiRtnCode.BI_RTN_CODE_SUCCESS.getCode();
    public String RetMsg = "";
}
