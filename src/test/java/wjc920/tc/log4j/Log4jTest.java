package wjc920.tc.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
    
    private static final Logger log = LogManager.getLogger();
    
    @Test
    public void testLogConfig() {
        log.error("log error");
        log.warn("log warn");
        log.debug("log debug");
        log.info("log info");
    }
    
    

}
