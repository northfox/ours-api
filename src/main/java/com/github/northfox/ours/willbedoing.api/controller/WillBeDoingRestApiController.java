package com.github.northfox.ours.willbedoing.api.controller;


import com.github.northfox.ours.willbedoing.api.domain.WillTodoEntity;
import com.github.northfox.ours.willbedoing.api.exception.ApplicationException;
import com.github.northfox.ours.willbedoing.api.service.WillTodosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@RestController
@RequestScope
@RequestMapping(value = "/will_be_doing/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class WillBeDoingRestApiController {

  private final WillTodosService willTodosService;

  // hello
  @RequestMapping(value = "/hello", method = {RequestMethod.GET})
  public ResponseEntity<String> apiV1Statuses() {
    preCall();
    return ResponseEntity.ok("hello, world");
  }

  // main
  @RequestMapping(value = "/backups/{keyword}", method = {RequestMethod.GET},
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ResponseEntity<List<WillTodoEntity>> apiV1BackupsByKeyword(@PathVariable String keyword)
      throws ApplicationException {
    preCall();
    List<WillTodoEntity> result = willTodosService.findByKeyword(keyword);
    return ResponseEntity.ok(result);
  }

  @RequestMapping(value = "/todos", method = {RequestMethod.GET},
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ResponseEntity<List<WillTodoEntity>> apiV1Todos() {
    preCall();
    List<WillTodoEntity> result = willTodosService.findAll();
    return ResponseEntity.ok(result);
  }

  @RequestMapping(value = "/backups/{keyword}", method = {RequestMethod.POST},
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public ResponseEntity<List<WillTodoEntity>> apiV1BackupsByKeywordPost(@PathVariable String keyword,
      @RequestBody List<WillTodoEntity> entity) throws ApplicationException {
    preCall();
    List<WillTodoEntity> result = willTodosService.saveAll(keyword, entity);
    return ResponseEntity.ok(result);
  }

  private void preCall() {
    String requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
    log.info(String.format("call api: [%s]", requestUri));
  }
}
