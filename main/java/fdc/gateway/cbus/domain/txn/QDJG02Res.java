package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractResMsg;

import java.lang.reflect.Field;

public class QDJG02Res extends AbstractResMsg {

    /*
       ��ҳ��һ�����ϵ�KEY ֵ	64	����ֵ������һ������ķ�ҳ������Ϣ������64 λ�Ҳ��ո񣬡�
       ��ҳ���һ�����ϵ�KEY ֵ	64	 ����ֵ������һ������ķ�ҳ������Ϣ������64 λ�Ҳ��ո�
       "�Ƿ���ʣ������"	1	1 ��ʾû����һҳ 0 ����null ��ʾ����һҳ
       ��ϸ����	7	�����ϸ����
       �跽������ 	15	����跽������
       ����������	15	�������������
       �������	1	"1-�ֽ� 2-ת��"
       ������ˮ��	19	���������ˮ��
       ժҪ����	4	���ժҪ����
       ��ע	40	�����ע
       ��������	8	�����������
       ����ʱ��	6	�������ʱ��
     */

    private String thisFirstKey;       //   ��ҳ��һ�����ϵ�KEY ֵ	64	����ֵ������һ������ķ�ҳ������Ϣ������64 λ�Ҳ��ո񣬡�
    private String thisLastKey;        //   ��ҳ���һ�����ϵ�KEY ֵ	64	 ����ֵ������һ������ķ�ҳ������Ϣ������64 λ�Ҳ��ո�
    private String isLast;             //  1 ��ʾû����һҳ 0 ����null ��ʾ����һҳ
    private String detailCnt;          //  ��ϸ����   7
    private String debitAmt;           //   �跽������ 	15	����跽������
    private String creditAmt;          //  ����������	15	�������������
    private String txnType;            // �������	1	"1-�ֽ� 2-ת��"
    private String txnSerialNo;        //  ������ˮ��	19	���������ˮ��

    private String summaryCode;        // ժҪ����	4	���ժҪ����
    private String remark;             // ��ע	40	�����ע
    private String txnDate;            // �������� 8
    private String txnTime;            // ����ʱ�� 6

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
                System.out.println("��������ϸ����" + fields[i].getName() + " : " + s);
                fields[i].set(this, s);
                pos += bytes.length;
            }
        } catch (Exception e) {
            throw new RuntimeException("�������Ľ�����������", e);
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
