package com.sk.asset.logging.controller;

import com.sk.asset.logging.dto.BodyRequest;
import com.sk.asset.logging.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logging")
@RequiredArgsConstructor
public class LoggingController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoggingService loggingService;

    @GetMapping
    public String logging() {
        logger.trace("Controller TRACE");
        logger.debug("Controller DEBUG");
        logger.info("Controller INFO");
        logger.warn("Controller WARN");
        logger.error("Controller ERROR");

        loggingService.loggingTest();

        return "logging";
    }

    @GetMapping(value = "/with-header")
    public String loggingWithHeader(@RequestHeader("id") String id, @RequestHeader("name") String name) {

        logger.info("id: " + id);
        logger.info("name: " + name);

        return "id: " + id + ", name: " + name;
    }
    @GetMapping("/with-param")
    public String loggingWithParam(@RequestParam String id, @RequestParam String name) {

        logger.info("id: " + id);
        logger.info("name: " + name);

        return "id: " + id + ", name: " + name;
    }
    @PostMapping("/with-body")
    public BodyRequest loggingWithBody(@RequestBody BodyRequest request) {

        logger.info("id: " + request.getId());

        logger.info("name: " + request.getName());

        return request;
    }
}
