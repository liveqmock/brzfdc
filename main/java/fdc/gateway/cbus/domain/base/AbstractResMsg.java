package fdc.gateway.cbus.domain.base;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-25
 * Time: 下午1:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractResMsg {
    protected MsgHeader header = new MsgHeader();

    public void assembleFields(byte[] buffer) throws IllegalAccessException {
        byte[] headBytes = new byte[59];
        System.arraycopy(buffer, 0, headBytes, 0, 59);
        header.assembleFields(headBytes);
        byte[] bodyBytes = new byte[buffer.length - 59];
        System.arraycopy(buffer, 59, bodyBytes, 0, bodyBytes.length);
        if ("99".equals(header.getRtnCode())) {
             throw new RuntimeException("99与核心交易失败。" + new String(bodyBytes));
        }
        assembleBodyFields(bodyBytes);
    }

    public abstract void assembleBodyFields(byte[] bodyBuffer) throws IllegalAccessException;
}
