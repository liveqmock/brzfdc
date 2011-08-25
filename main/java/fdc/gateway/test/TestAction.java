package fdc.gateway.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-25
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class TestAction {
    private static final Logger logger = LoggerFactory.getLogger(TestAction.class);
    public String test0003() {
        try {
            Client0003Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
            logger.error(e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
     public String test0004() {
        try {
            Client0004Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
           logger.error(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
     public String test0005() {
        try {
            Client0005Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
           logger.error(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
     public String test0006() {
        try {
            Client0006Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
           logger.error(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
     public String test0007() {
        try {
            Client0007Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
           logger.error(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

     public String test2004() {
        try {
            Client2004Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
           logger.error(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
     public String test2005() {
        try {
            Client2005Test.test();
            MessageUtil.addInfo("成功");

        } catch (Exception e) {
            MessageUtil.addError("异常");
           logger.error(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

}
