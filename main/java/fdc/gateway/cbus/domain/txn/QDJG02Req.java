package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractReqMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG02Req extends AbstractReqMsg {

    /*
    上页第一笔资料的KEY 值	64	从上一笔的返回报文获取
    上页最后一笔资料的KEY 值	64	从上一笔的返回报文获取
    跳页方向标	1	1 表示上翻 2 表示下翻（请固定使用2）
    帐号                                                  	28	左对齐，右补空格
    TODO 起始明细号                                      	7	左对齐，右补空格（首次为1，其他为前一次最后一笔明细号+1）
    起始日期	8	左对齐，右补空格
    寄对帐单标志	1	不上送，补空格
    密码	16	默认为账号，左对齐，右补空格
    终止日期	8	左对齐，右补空格
    功能	1	1 账户明细查询
     */

    private String preFirstKey = "";       //  上页第一笔资料的KEY 值	64	从上一笔的返回报文获取
    private String preLastKey = "";        //  上页最后一笔资料的KEY 值	64	从上一笔的返回报文获取
    private String turnPageNo = "2";  //  跳页方向标 长度1  固定值2
    private String accountNo;         //  帐号  28
    private String startDetailNo;     //  TODO 起始明细号  7
    private String startDate;         //  起始日期  8
    private String chkActPrFlag = " "; // 寄对帐单标志  1
    private String password;           // 密码	16	默认为账号，左对齐，右补空格
    private String endDate;            // 终止日期	8	左对齐，右补空格
    private String function = "1";     // 功能	1	1 账户明细查询

    public String bodyToString() {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(StringUtils.rightPad(preFirstKey, 64, ' '));
        strBuilder.append(StringUtils.rightPad(preLastKey, 64, ' '));
        strBuilder.append(turnPageNo);
        strBuilder.append(StringUtils.rightPad(accountNo, 28, ' '));
        strBuilder.append(StringUtils.rightPad(startDetailNo, 7, ' '));
        strBuilder.append(StringUtils.rightPad(startDate, 8, ' '));
        strBuilder.append(chkActPrFlag);
        strBuilder.append(StringUtils.rightPad(password, 16, ' '));
        strBuilder.append(StringUtils.rightPad(endDate, 8, ' '));
        strBuilder.append(function);

        return strBuilder.toString();
    }

    // -----------------------------------------------

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getChkActPrFlag() {
        return chkActPrFlag;
    }

    public void setChkActPrFlag(String chkActPrFlag) {
        this.chkActPrFlag = chkActPrFlag;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreFirstKey() {
        return preFirstKey;
    }

    public void setPreFirstKey(String preFirstKey) {
        this.preFirstKey = preFirstKey;
    }

    public String getPreLastKey() {
        return preLastKey;
    }

    public void setPreLastKey(String preLastKey) {
        this.preLastKey = preLastKey;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDetailNo() {
        return startDetailNo;
    }

    public void setStartDetailNo(String startDetailNo) {
        this.startDetailNo = startDetailNo;
    }

    public String getTurnPageNo() {
        return turnPageNo;
    }

    public void setTurnPageNo(String turnPageNo) {
        this.turnPageNo = turnPageNo;
    }
}
