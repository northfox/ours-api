package com.github.northfox.ours.kyoyu.kyoyu.api.controller;


import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.TodosService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/kyoyu/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class KyoyuRestApiController {

    @Autowired
    private TodosService todosService;

    @RequestMapping(value = "/todos", method = {RequestMethod.GET})
    public ResponseEntity<List<VTodoEntity>> apiV1Bookmarks() {
        preCall();
        List<VTodoEntity> result = todosService.all();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/todos", method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<TodoEntity> apiV1BookmarksPost(@RequestBody TodoEntity entity) {
        preCall();
        TodoEntity result = todosService.save(entity);
        return ResponseEntity.ok(result);
    }

    private void preCall() {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        log.info(String.format("call api: [%s]", requestUri));
    }
}