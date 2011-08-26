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
 * Time: 上午2:27
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
        // 得到交易码，根据交易码将xml转换成相应的接口对象。
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            // 【0001】  0002   2001 2002 2003 【2006】    2007 2008
            case 1:
                T0001Req t0001Req = (T0001Req) BaseBean.toObject(T0001Req.class, message);
                logger.info(t0001Req.head.OpDate + t0001Req.head.OpTime + "==接收交易：" + t0001Req.head.OpCode);
                List<RsAccount> accountList = accountService.selectAccountByCodeName(t0001Req.param.Acct, t0001Req.param.AcctName);
                T0001Res t0001Res = new T0001Res();
                if (accountList.isEmpty()) {
                    t0001Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t0001Res.head.RetMsg = "没有查到账户，请检查账户信息。";
                } else {
                    RsAccount account = accountList.get(0);
                    t0001Res.param.Balance = StringUtil.toBiformatAmt(account.getBalance());
                    t0001Res.param.UsableBalance = StringUtil.toBiformatAmt(account.getBalanceUsable());
                }
                responseMsg = t0001Res.toFDCDatagram();
                break;
            case 2:
                T0002Req t0002Req = (T0002Req) BaseBean.toObject(T0002Req.class, message);
                logger.info(t0002Req.head.OpDate + t0002Req.head.OpTime + "==接收交易：" + t0002Req.head.OpCode);
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
                record.ToAcctName = "张三";
                record.ToBankName = "日照银行";
                record.Purpose = "购房";

                t0002Res.param.recordList.add(record);

                record = T0002Res.getRecord();
                record.Date = SystemService.getSdfdate8();
                record.Time = SystemService.getSdftime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "2000";
                record.ContractNum = "100000000089";
                record.ToAcct = "55555666666";
                record.ToAcctName = "张四";
                record.ToBankName = "日照银行";
                record.Purpose = "也购房";

                t0002Res.param.recordList.add(record);
                responseMsg = t0002Res.toFDCDatagram();
                break;

            case 2001:
                T2001Req t2001Req = (T2001Req) BaseBean.toObject(T2001Req.class, message);
                logger.info(t2001Req.head.OpDate + t2001Req.head.OpTime + "==接收交易：" + t2001Req.head.OpCode);
                T2001Res t2001Res = new T2001Res();
                responseMsg = t2001Res.toFDCDatagram();
                break;
            case 2002:
                T2002Req t2002Req = (T2002Req) BaseBean.toObject(T2002Req.class, message);
                logger.info(t2002Req.head.OpDate + t2002Req.head.OpTime + "==接收交易：" + t2002Req.head.OpCode);
                T2002Res t2002Res = new T2002Res();
                responseMsg = t2002Res.toFDCDatagram();
                break;
            case 2003:
                // TODO 合同接口表
                T2003Req t2003Req = (T2003Req) BaseBean.toObject(T2003Req.class, message);
                logger.info(t2003Req.head.OpDate + t2003Req.head.OpTime + "==接收交易：" + t2003Req.head.OpCode);
                RsContract contract = new RsContract();
                contract.setContractNo(t2003Req.param.ContractNum);                                     // 合同号
                contract.setAccountCode(t2003Req.param.Acct);                                               // 监管账号
                contract.setAccountName(t2003Req.param.AcctName);                                     // 监管账户户名
                contract.setBuyerName(t2003Req.param.BuyerName);                                     // 购房人姓名
                contract.setBuyerAccCode(t2003Req.param.BuyerAcct);                                   // 购房人个人结算账号
                contract.setBuyerBankName(t2003Req.param.BuyerBankName);                      // 购房人账户开户行
                contract.setBuyerCertType(t2003Req.param.BuyerIDType);                              //购房人证件类型
                contract.setBuyerCertNo(t2003Req.param.BuyerIDCode);                                // 购房人证件号码
                contract.setTotalAmt(new BigDecimal(t2003Req.param.TotalAmt));                   //房屋总价
                contract.setEarnestAmt(new BigDecimal(t2003Req.param.Deposit));                  // 定金
                contract.setFirstAmt(new BigDecimal(t2003Req.param.DownPay));                    // 首付款
                contract.setLoanAmt(new BigDecimal(t2003Req.param.Mortgage));                  // 贷款
                contract.setHouseAddress(t2003Req.param.HouseAddress);                            // 房屋地址
                contract.setHouseType(t2003Req.param.HouseType);                                      // 房屋类型
                contract.setHouseNo(t2003Req.param.HouseNO);                                            // 房屋楼栋信息
                contract.setOverAmt(new BigDecimal(t2003Req.param.OverAmt));                  // 超标面积价款
                contract.setTreAccName(t2003Req.param.TreasuryName);                             // 上缴财政专用账户名
                contract.setTreAccCode(t2003Req.param.TreasuryAcct);                                // 上缴财政专用账号
                contract.setTreBankName(t2003Req.param.TreasuryBankName);                  // 财政专户开户行

                T2003Res t2003Res = new T2003Res();
                responseMsg = t2003Res.toFDCDatagram();
                break;
            case 2006:     // 接收账户撤销监管通知
                T2006Req t2006Req = (T2006Req) BaseBean.toObject(T2006Req.class, message);
                logger.info(t2006Req.head.OpDate + t2006Req.head.OpTime + "==接收交易：" + t2006Req.head.OpCode);
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
                    t2006Res.head.RetMsg = "没有查到账户，请检查账户信息。";
                }

                responseMsg = t2006Res.toFDCDatagram();
                break;
            case 2007:
                T2007Req t2007Req = (T2007Req) BaseBean.toObject(T2007Req.class, message);
                logger.info(t2007Req.head.OpDate + t2007Req.head.OpTime + "==接收交易：" + t2007Req.head.OpCode);

                T2007Res t2007Res = new T2007Res();
                responseMsg = t2007Res.toFDCDatagram();
                break;
            case 2008:
                T2007Req t2008Req = (T2007Req) BaseBean.toObject(T2008Req.class, message);
                logger.info(t2008Req.head.OpDate + t2008Req.head.OpTime + "==接收交易：" + t2008Req.head.OpCode);
                T2008Res t2008Res = new T2008Res();
                responseMsg = t2008Res.toFDCDatagram();
                break;
            default:
                CommonRes okData = new CommonRes();
                responseMsg = okData.toFDCDatagram();
                // logger.error("====接收到无法处理非法交易：【交易码：" + opCode + "】");

        }
        return responseMsg;
    }
}
