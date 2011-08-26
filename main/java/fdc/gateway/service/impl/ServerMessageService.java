package fdc.gateway.service.impl;

import fdc.common.constant.AccountStatus;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0001Req;
import fdc.gateway.domain.T000.T0001Res;
import fdc.gateway.domain.T000.T0002Req;
import fdc.gateway.domain.T000.T0002Res;
import fdc.gateway.domain.T200.*;
import fdc.gateway.service.IMessageService;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.utils.BiRtnCode;
import fdc.gateway.utils.StringUtil;
import fdc.repository.model.RsAccount;
import fdc.repository.model.RsContract;
import fdc.service.account.AccountService;
import fdc.service.contract.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����2:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServerMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageService.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private ContractService contractService;

    @Override
    public synchronized String handleMessage(String message) {
        String responseMsg = null;
        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            // ��0001��  0002   2001 2002 2003 ��2006��    2007 2008
            case 1:
                T0001Req t0001Req = (T0001Req) BaseBean.toObject(T0001Req.class, message);
                logger.info(t0001Req.head.OpDate + t0001Req.head.OpTime + "==���ս��ף�" + t0001Req.head.OpCode);
                List<RsAccount> accountList = accountService.selectAccountByCodeName(t0001Req.param.Acct, t0001Req.param.AcctName);
                T0001Res t0001Res = new T0001Res();
                if (accountList.isEmpty()) {
                    t0001Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t0001Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                } else {
                    RsAccount account = accountList.get(0);
                    t0001Res.param.Balance = StringUtil.toBiformatAmt(account.getBalance());
                    t0001Res.param.UsableBalance = StringUtil.toBiformatAmt(account.getBalanceUsable());
                }
                responseMsg = t0001Res.toFDCDatagram();
                break;
            case 2:
                T0002Req t0002Req = (T0002Req) BaseBean.toObject(T0002Req.class, message);
                logger.info(t0002Req.head.OpDate + t0002Req.head.OpTime + "==���ս��ף�" + t0002Req.head.OpCode);
                T0002Res t0002Res = new T0002Res();
                t0002Res.param.DetailNum = "2";
                T0002Res.Param.Record record = T0002Res.getRecord();
                record.Date = SystemService.getSdfdate8();
                record.Time = SystemService.getSdftime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "1000";
                record.ContractNum = "100000000088";
                record.ToAcct = "555555555";
                record.ToAcctName = "����";
                record.ToBankName = "��������";
                record.Purpose = "����";

                t0002Res.param.recordList.add(record);

                record = T0002Res.getRecord();
                record.Date = SystemService.getSdfdate8();
                record.Time = SystemService.getSdftime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "2000";
                record.ContractNum = "100000000089";
                record.ToAcct = "55555666666";
                record.ToAcctName = "����";
                record.ToBankName = "��������";
                record.Purpose = "Ҳ����";

                t0002Res.param.recordList.add(record);
                responseMsg = t0002Res.toFDCDatagram();
                break;

            case 2001:
                T2001Req t2001Req = (T2001Req) BaseBean.toObject(T2001Req.class, message);
                logger.info(t2001Req.head.OpDate + t2001Req.head.OpTime + "==���ս��ף�" + t2001Req.head.OpCode);
                T2001Res t2001Res = new T2001Res();
                responseMsg = t2001Res.toFDCDatagram();
                break;
            case 2002:
                T2002Req t2002Req = (T2002Req) BaseBean.toObject(T2002Req.class, message);
                logger.info(t2002Req.head.OpDate + t2002Req.head.OpTime + "==���ս��ף�" + t2002Req.head.OpCode);
                T2002Res t2002Res = new T2002Res();
                responseMsg = t2002Res.toFDCDatagram();
                break;
            case 2003:
                // TODO ��ͬ�ӿڱ�
                T2003Req t2003Req = (T2003Req) BaseBean.toObject(T2003Req.class, message);
                logger.info(t2003Req.head.OpDate + t2003Req.head.OpTime + "==���ս��ף�" + t2003Req.head.OpCode);
                RsContract contract = new RsContract();
                contract.setContractNo(t2003Req.param.ContractNum);                                     // ��ͬ��
                contract.setAccountCode(t2003Req.param.Acct);                                               // ����˺�
                contract.setAccountName(t2003Req.param.AcctName);                                     // ����˻�����
                contract.setBuyerName(t2003Req.param.BuyerName);                                     // ����������
                contract.setBuyerAccCode(t2003Req.param.BuyerAcct);                                   // �����˸��˽����˺�
                contract.setBuyerBankName(t2003Req.param.BuyerBankName);                      // �������˻�������
                contract.setBuyerCertType(t2003Req.param.BuyerIDType);                              //������֤������
                contract.setBuyerCertNo(t2003Req.param.BuyerIDCode);                                // ������֤������
                contract.setTotalAmt(new BigDecimal(t2003Req.param.TotalAmt));                   //�����ܼ�
                contract.setEarnestAmt(new BigDecimal(t2003Req.param.Deposit));                  // ����
                contract.setFirstAmt(new BigDecimal(t2003Req.param.DownPay));                    // �׸���
                contract.setLoanAmt(new BigDecimal(t2003Req.param.Mortgage));                  // ����
                contract.setHouseAddress(t2003Req.param.HouseAddress);                            // ���ݵ�ַ
                contract.setHouseType(t2003Req.param.HouseType);                                      // ��������
                contract.setHouseNo(t2003Req.param.HouseNO);                                            // ����¥����Ϣ
                contract.setOverAmt(new BigDecimal(t2003Req.param.OverAmt));                  // ��������ۿ�
                contract.setTreAccName(t2003Req.param.TreasuryName);                             // �Ͻɲ���ר���˻���
                contract.setTreAccCode(t2003Req.param.TreasuryAcct);                                // �Ͻɲ���ר���˺�
                contract.setTreBankName(t2003Req.param.TreasuryBankName);                  // ����ר��������

                T2003Res t2003Res = new T2003Res();
                responseMsg = t2003Res.toFDCDatagram();
                break;
            case 2006:     // �����˻��������֪ͨ
                T2006Req t2006Req = (T2006Req) BaseBean.toObject(T2006Req.class, message);
                logger.info(t2006Req.head.OpDate + t2006Req.head.OpTime + "==���ս��ף�" + t2006Req.head.OpCode);
                List<RsAccount> overAccountList = accountService.selectAccountByCodeName(t2006Req.param.Acct, t2006Req.param.AcctName);
                T2006Res t2006Res = new T2006Res();
                if (!overAccountList.isEmpty()) {
                    RsAccount account = overAccountList.get(0);
                    if (accountService.updateAccountToStatus(account, AccountStatus.CLOSE.getCode()) == 1) {
                        t2006Res.param.CancelDate = SystemService.getSdfdate8();
                        t2006Res.param.CancelTime = SystemService.getSdftime6();
                        t2006Res.param.FinalBalance = StringUtil.toBiformatAmt(account.getBalance());
                    }
                } else {
                    t2006Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t2006Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                }

                responseMsg = t2006Res.toFDCDatagram();
                break;
            case 2007:
                T2007Req t2007Req = (T2007Req) BaseBean.toObject(T2007Req.class, message);
                logger.info(t2007Req.head.OpDate + t2007Req.head.OpTime + "==���ս��ף�" + t2007Req.head.OpCode);

                T2007Res t2007Res = new T2007Res();
                responseMsg = t2007Res.toFDCDatagram();
                break;
            case 2008:
                T2007Req t2008Req = (T2007Req) BaseBean.toObject(T2008Req.class, message);
                logger.info(t2008Req.head.OpDate + t2008Req.head.OpTime + "==���ս��ף�" + t2008Req.head.OpCode);
                T2008Res t2008Res = new T2008Res();
                responseMsg = t2008Res.toFDCDatagram();
                break;
            default:
                CommonRes okData = new CommonRes();
                responseMsg = okData.toFDCDatagram();
                // logger.error("====���յ��޷�����Ƿ����ף��������룺" + opCode + "��");

        }
        return responseMsg;
    }
}
