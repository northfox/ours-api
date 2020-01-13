package com.github.northfox.ours.willbedoing.api.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * RestAPIのインターフェイス群
 */
@Slf4j
@RestController
@RequestScope
@RequestMapping(value = "/will_be_doing/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class WillBeDoingRestApiController {

    // hello
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public ResponseEntity<String> apiV1Statuses() {
        preCall();
        return ResponseEntity.ok("hello, world");
    }

    private void preCall() {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        log.info(String.format("call api: [%s]", requestUri));
    }
}
