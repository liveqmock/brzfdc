package fdc.gateway.domain;

import fdc.utils.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-10
 * Time: ����4:15
 * To change this template use File | Settings | File Templates.
 */
/*
<OpCode>2002</OpCode>
<OpDate>20090325</OpDate>
<OpTime>215515</OpTime>
<BankCode>103</BankCode>
 */
public class ReqHead extends BaseBean{
    public String OpCode = "";
    public String OpDate = DateUtil.getDate8();
    public String OpTime = DateUtil.getTime6();
    public String BankCode = "";
}
