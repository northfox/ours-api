package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.TodoRepository;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.VTodoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TodosServiceImplTest {

    @MockBean
    private VTodoRepository viewRepository;

    @MockBean
    private TodoRepository repository;

    private TodosService sut;

    @BeforeEach
    void setup() {
        sut = new TodosServiceImpl(viewRepository, repository);
    }

    @Test
    void all_すべてのデータが取得できること() {
        List<TodoEntity> expected = new ArrayList<>();
        when(repository.findAll()).thenReturn(expected);
        List<VTodoEntity> actual = sut.all();
        assertIterableEquals(expected, actual);
    }

    @Test
    void save_データを登録できること() {
        Date now = new Date();
        TodoEntity expected = new TodoEntity(0, 0, "title", 0, 0, null, now, now, null, null);
        when(repository.save(any())).thenReturn(expected);
        TodoEntity actual = sut.save(expected);

        // expected
        assertEquals(expected, actual);
        verify(repository, times(1)).save(expected);
    }

    @Test
    void findByProjectId_指定したプロジェクトIDのデータを取得できること() {
        Date now = new Date();
        VTodoEntity expected1 = new VTodoEntity(1, "project1", 2, "title2", 10, "着手中", 100, now, now, now, now, now);
        VTodoEntity expected2 = new VTodoEntity(1, "project1", 5, "title2", 20, "待ち", 500, null, now, now, null, null);
        List<VTodoEntity> expected = Arrays.asList(expected1, expected2);
        when(viewRepository.findAll(any(Example.class))).thenReturn(expected);

        List<VTodoEntity> actual = sut.findByProjectId(1);
        assertEquals(expected, actual);
    }
}