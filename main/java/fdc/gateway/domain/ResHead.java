package fdc.gateway.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-10
 * Time: ����4:15
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("root")
public class ResHead extends BaseBean{
    public String RetCode = "0000";
    public String RetMsg = "";
}
