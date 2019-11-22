package com.github.northfox.ours.bousai.api.controller;


import com.github.northfox.ours.bousai.api.exception.ApplicationException;
import com.github.northfox.ours.bousai.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.api.service.ProjectsService;
import com.github.northfox.ours.kyoyu.api.service.StatusesService;
import com.github.northfox.ours.kyoyu.api.service.TodosService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value = "/bousai/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BousaiRestApiController {
    private void preCall() {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        log.info(String.format("call api: [%s]", requestUri));
    }
}
