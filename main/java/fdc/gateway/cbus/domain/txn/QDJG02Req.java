package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractReqMsg;
import org.apache.commons.lang.StringUtils;

public class QDJG02Req extends AbstractReqMsg {

    /*
    ��ҳ��һ�����ϵ�KEY ֵ	64	����һ�ʵķ��ر��Ļ�ȡ
    ��ҳ���һ�����ϵ�KEY ֵ	64	����һ�ʵķ��ر��Ļ�ȡ
    ��ҳ�����	1	1 ��ʾ�Ϸ� 2 ��ʾ�·�����̶�ʹ��2��
    �ʺ�                                                  	28	����룬�Ҳ��ո�
    TODO ��ʼ��ϸ��                                      	7	����룬�Ҳ��ո��״�Ϊ1������Ϊǰһ�����һ����ϸ��+1��
    ��ʼ����	8	����룬�Ҳ��ո�
    �Ķ��ʵ���־	1	�����ͣ����ո�
    ����	16	Ĭ��Ϊ�˺ţ�����룬�Ҳ��ո�
    ��ֹ����	8	����룬�Ҳ��ո�
    ����	1	1 �˻���ϸ��ѯ
     */

    private String preFirstKey = "";       //  ��ҳ��һ�����ϵ�KEY ֵ	64	����һ�ʵķ��ر��Ļ�ȡ
    private String preLastKey = "";        //  ��ҳ���һ�����ϵ�KEY ֵ	64	����һ�ʵķ��ر��Ļ�ȡ
    private String turnPageNo = "2";  //  ��ҳ����� ����1  �̶�ֵ2
    private String accountNo;         //  �ʺ�  28
    private String startDetailNo;     //  TODO ��ʼ��ϸ��  7
    private String startDate;         //  ��ʼ����  8
    private String chkActPrFlag = " "; // �Ķ��ʵ���־  1
    private String password;           // ����	16	Ĭ��Ϊ�˺ţ�����룬�Ҳ��ո�
    private String endDate;            // ��ֹ����	8	����룬�Ҳ��ո�
    private String function = "1";     // ����	1	1 �˻���ϸ��ѯ

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
