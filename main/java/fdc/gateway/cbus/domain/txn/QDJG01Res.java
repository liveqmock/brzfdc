package fdc.gateway.cbus.domain.txn;

import fdc.gateway.cbus.domain.base.AbstractResMsg;

public class QDJG01Res extends AbstractResMsg {

    /*
       ’Àªß”‡∂Ó£¨ø…”√”‡∂Ó  15
     */
    private String actbal;
    private String avabal;

    @Override
    public void assembleBodyFields(byte[] buffer) {
        byte[] actbalBytes = new byte[15];
        System.arraycopy(buffer, 0, actbalBytes, 0, 15);
        String actbalStr = new String(actbalBytes);
        actbal = actbalStr.trim();

        byte[] avabalBytes = new byte[15];
        System.arraycopy(buffer, 15, avabalBytes, 0, 15);
        String avabalStr = new String(avabalBytes);
        avabal = avabalStr.trim();
        System.out.println("°æ’Àªß”‡∂Ó°ø" + actbal + "°æø…”√”‡∂Ó°ø" + avabal);
    }

    // --------------------------------------------

    public String getActbal() {
        return actbal;
    }

    public void setActbal(String actbal) {
        this.actbal = actbal;
    }

    public String getAvabal() {
        return avabal;
    }

    public void setAvabal(String avabal) {
        this.avabal = avabal;
    }
}
