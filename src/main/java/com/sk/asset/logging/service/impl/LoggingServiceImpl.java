package com.sk.asset.logging.service.impl;

import com.sk.asset.logging.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void loggingTest() {
        logger.trace("Service TRACE");
        logger.debug("Service DEBUG");
        logger.info("Service INFO");
        logger.warn("Service WARN");
        logger.error("Service ERROR");
    }


}
