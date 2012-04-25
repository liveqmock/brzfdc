package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractResMsg;

import java.lang.reflect.Field;

public class QDJG02Res extends AbstractResMsg {

    /*
       本页第一笔资料的KEY 值	64	将该值送入下一笔请求的翻页控制信息，不足64 位右补空格，。
       本页最后一笔资料的KEY 值	64	 将该值送入下一笔请求的翻页控制信息，不足64 位右补空格
       "是否还有剩余数据"	1	1 表示没有下一页 0 或者null 表示有下一页
       明细笔数	7	输出明细笔数
       借方发生额 	15	输出借方发生额
       贷方发生额	15	输出贷方发生额
       交易类别	1	"1-现金 2-转账"
       交易流水号	19	输出交易流水号
       摘要代码	4	输出摘要代码
       备注	40	输出备注
       交易日期	8	输出交易日期
       交易时间	6	输出交易时间
     */

    private String thisFirstKey;       //   本页第一笔资料的KEY 值	64	将该值送入下一笔请求的翻页控制信息，不足64 位右补空格，。
    private String thisLastKey;        //   本页最后一笔资料的KEY 值	64	 将该值送入下一笔请求的翻页控制信息，不足64 位右补空格
    private String isLast;             //  1 表示没有下一页 0 或者null 表示有下一页
    private String detailCnt;          //  明细笔数   7
    private String debitAmt;           //   借方发生额 	15	输出借方发生额
    private String creditAmt;          //  贷方发生额	15	输出贷方发生额
    private String txnType;            // 交易类别	1	"1-现金 2-转账"
    private String txnSerialNo;        //  交易流水号	19	输出交易流水号

    private String summaryCode;        // 摘要代码	4	输出摘要代码
    private String remark;             // 备注	40	输出备注
    private String txnDate;            // 交易日期 8
    private String txnTime;            // 交易时间 6

    @Override
    public void assembleBodyFields(byte[] buffer) {
        int offset = 0;
        int[] fieldLengths = new int[]{64, 64, 1, 7, 15, 15, 1, 19, 4, 40, 8, 6};

        Class clazz = this.getClass();
        try {
            Field[] fields = clazz.getDeclaredFields();
            int pos = offset;
            for (int i = 0; i < fieldLengths.length; i++) {
                byte[] bytes = new byte[fieldLengths[i]];
                System.arraycopy(buffer, pos, bytes, 0, bytes.length);
                fields[i].setAccessible(true);
                String s = new String(bytes);
                System.out.println("【交易明细：域】" + fields[i].getName() + " : " + s);
                fields[i].set(this, s);
                pos += bytes.length;
            }
        } catch (Exception e) {
            throw new RuntimeException("报文正文解析处理有误！", e);
        }
    }

    // -----------------------------------------------

    public String getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(String debitAmt) {
        this.debitAmt = debitAmt;
    }

    public String getDetailCnt() {
        return detailCnt;
    }

    public void setDetailCnt(String detailCnt) {
        this.detailCnt = detailCnt;
    }

    public String getLast() {
        return isLast;
    }

    public void setLast(String last) {
        isLast = last;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSummaryCode() {
        return summaryCode;
    }

    public void setSummaryCode(String summaryCode) {
        this.summaryCode = summaryCode;
    }

    public String getThisFirstKey() {
        return thisFirstKey;
    }

    public void setThisFirstKey(String thisFirstKey) {
        this.thisFirstKey = thisFirstKey;
    }

    public String getThisLastKey() {
        return thisLastKey;
    }

    public void setThisLastKey(String thisLastKey) {
        this.thisLastKey = thisLastKey;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnSerialNo() {
        return txnSerialNo;
    }

    public void setTxnSerialNo(String txnSerialNo) {
        this.txnSerialNo = txnSerialNo;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
}
