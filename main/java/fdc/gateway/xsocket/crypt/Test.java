package fdc.gateway.xsocket.crypt;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            String test = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>�ൺΰҵ���ز����޹�˾</AcctName></Param></root>";
            DesCryptHelper des = new DesCryptHelper("12345678");//�Զ�����Կ
            System.out.println("����ǰ��" + test);

            String desStr = des.encrypt(test);
            System.out.println("���ܺ�" + desStr);

            System.out.println("���ܺ�" + des.decrypt(desStr));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
