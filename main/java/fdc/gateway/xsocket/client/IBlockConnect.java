package fdc.gateway.xsocket.client;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-10
 * Time: обнГ9:02
 * To change this template use File | Settings | File Templates.
 */
public interface IBlockConnect extends IConnect{
  boolean sendDataUntilRcv(String datagram) throws IOException;
}
