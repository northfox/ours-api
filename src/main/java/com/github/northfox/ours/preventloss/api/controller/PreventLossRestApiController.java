package com.github.northfox.ours.preventloss.api.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * RestAPIのインターフェイス群
 */
@Slf4j
@RestController
@RequestScope
@RequestMapping(value = "/preventloss/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PreventLossRestApiController {
    private void preCall() {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        log.info(String.format("call api: [%s]", requestUri));
    }
}
