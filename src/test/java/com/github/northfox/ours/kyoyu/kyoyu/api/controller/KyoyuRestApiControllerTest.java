package com.github.northfox.ours.kyoyu.kyoyu.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.ProjectsService;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.StatusesService;
import com.github.northfox.ours.kyoyu.kyoyu.api.service.TodosService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = KyoyuRestApiController.class)
public class KyoyuRestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    StatusesService statusesService;

    @MockBean
    ProjectsService projectsService;

    @MockBean
    TodosService todosService;

    @AfterEach
    public void cleanup() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    void apiV1Statuses_すべてのデータが取得できること() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        List<StatusEntity> expected = Arrays.asList(
                new StatusEntity(0, "test00", now, now, null),
                new StatusEntity(10, "test10", now, now, null));

        when(statusesService.all()).thenReturn(expected);

        mockMvc.perform(get("/kyoyu/api/v1/statuses"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @Test
    void apiV1StatusesPost_Statusを追加できること() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        StatusEntity expected = new StatusEntity(0, "test00", now, now, null);
        when(statusesService.save(any())).thenReturn(expected);
        String expectedJson = mapper.writeValueAsString(expected);

        //expected
        mockMvc.perform(post("/kyoyu/api/v1/statuses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(expectedJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void apiV1Projects_すべてのデータが取得できること() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        List<ProjectEntity> expected = Arrays.asList(
                new ProjectEntity(0, "test00", now, now, null),
                new ProjectEntity(10, "test10", now, now, null));

        when(projectsService.all()).thenReturn(expected);

        mockMvc.perform(get("/kyoyu/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @Test
    void apiV1ProjectsPost_Projectを追加できること() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        ProjectEntity expected = new ProjectEntity(0, "test00", now, now, null);
        when(projectsService.save(any())).thenReturn(expected);
        String expectedJson = mapper.writeValueAsString(expected);

        //expected
        mockMvc.perform(post("/kyoyu/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(expectedJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void apiV1Todos_すべてのデータが取得できること() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        List<VTodoEntity> expected = Arrays.asList(
                new VTodoEntity(0, "project-title", 0, "title", 0, "未着手", 0, now, now, now, null, null));

        when(todosService.all()).thenReturn(expected);

        mockMvc.perform(get("/kyoyu/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @Test
    void apiV1TodosPost_Todoを追加できること() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        TodoEntity expected = new TodoEntity(0, 0, "title", 0, 0, null, now, now, null, null);
        when(todosService.save(any())).thenReturn(expected);
        String expectedJson = mapper.writeValueAsString(expected);

        //expected
        mockMvc.perform(post("/kyoyu/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(expectedJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}