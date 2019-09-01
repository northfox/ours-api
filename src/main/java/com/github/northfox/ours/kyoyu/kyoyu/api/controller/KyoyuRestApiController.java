package com.github.northfox.ours.kyoyu.kyoyu.api.controller;


import com.github.northfox.ours.kyoyu.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.ApplicationException;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.ProjectsService;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.StatusesService;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.TodosService;
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
@RequestMapping(value = "/kyoyu/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class KyoyuRestApiController {

    private final StatusesService statusesService;
    private final ProjectsService projectsService;
    private final TodosService todosService;

    @Autowired
    public KyoyuRestApiController(
            StatusesService statusesService,
            ProjectsService projectsService,
            TodosService todosService) {
        this.statusesService = statusesService;
        this.projectsService = projectsService;
        this.todosService = todosService;
    }

    @RequestMapping(value = "/statuses", method = {RequestMethod.GET})
    public ResponseEntity<List<StatusEntity>> apiV1Statuses() {
        preCall();
        List<StatusEntity> result = statusesService.all();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/statuses", method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StatusEntity> apiV1StatusesPost(@RequestBody StatusEntity entity) {
        preCall();
        StatusEntity result = statusesService.save(entity);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/statuses/{statusId}", method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StatusEntity> apiV1StatusesById(@PathVariable Integer statusId)
            throws NotExistsEntityException {
        preCall();
        StatusEntity result = statusesService.find(statusId);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/statuses/{statusId}", method = {RequestMethod.PUT},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StatusEntity> apiV1StatusesPut(@PathVariable Integer statusId,
            @RequestBody StatusEntity entity) {
        preCall();
        StatusEntity result = statusesService.update(statusId, entity);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/statuses/{statusId}", method = {RequestMethod.DELETE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<StatusEntity> apiV1StatusesDelete(@PathVariable Integer statusId)
            throws ApplicationException {
        preCall();
        StatusEntity result = statusesService.delete(statusId);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/projects", method = {RequestMethod.GET})
    public ResponseEntity<List<ProjectEntity>> apiV1Projects() {
        preCall();
        List<ProjectEntity> result = projectsService.all();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/projects/{projectId}", method = {RequestMethod.GET})
    public ResponseEntity<ProjectEntity> apiV1ProjectById(@PathVariable Integer projectId)
            throws NotExistsEntityException {
        preCall();
        ProjectEntity result = projectsService.find(projectId);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/projects", method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ProjectEntity> apiV1ProjectsPost(@RequestBody ProjectEntity entity) {
        preCall();
        ProjectEntity result = projectsService.save(entity);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/projects/{projectId}/todos", method = {RequestMethod.GET})
    public ResponseEntity<List<VTodoEntity>> apiV1TodosOfProjectById(@PathVariable Integer projectId) {
        preCall();
        List<VTodoEntity> result = todosService.findByProjectId(projectId);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/projects/{projectId}/todos", method = {RequestMethod.POST})
    public ResponseEntity<VTodoEntity> apiV1TodosOfProjectByIdPost(@PathVariable Integer projectId,
            @RequestBody TodoEntity entity) throws NotExistsEntityException {
        preCall();
        VTodoEntity result = todosService.saveInProject(projectId, entity);
        return ResponseEntity.ok(result);
    }


    @RequestMapping(value = "/todos", method = {RequestMethod.GET})
    public ResponseEntity<List<VTodoEntity>> apiV1Todos() {
        preCall();
        List<VTodoEntity> result = todosService.all();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/todos", method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<TodoEntity> apiV1TodosPost(@RequestBody TodoEntity entity) {
        preCall();
        TodoEntity result = todosService.save(entity);
        return ResponseEntity.ok(result);
    }

    private void preCall() {
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        log.info(String.format("call api: [%s]", requestUri));
    }
}
