package com.github.northfox.ours.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.api.exception.ApplicationException;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.api.repository.VTodoRepository;
import com.github.northfox.ours.kyoyu.api.repository.TodoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
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

    @MockBean
    private EntityManager entityManager;

    private TodosService sut;

    @BeforeEach
    void setup() {
        sut = new TodosServiceImpl(viewRepository, repository, entityManager);
    }

    @Test
    void all_すべてのデータが取得できること() {
        List<TodoEntity> expected = new ArrayList<>();
        when(repository.findAll()).thenReturn(expected);
        List<VTodoEntity> actual = sut.all();
        assertIterableEquals(expected, actual);
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
    void saveInProject_データを指定したプロジェクト内に登録できること() throws NotExistsEntityException {
        Date now = new Date();
        TodoEntity expectedTodo = new TodoEntity(0, 0, "title", 0, 0, null, now, now, null, null);
        VTodoEntity expectedVtodo = new VTodoEntity(1, "project1", 2, "title2", 10, "着手中", 100, now, now, now, now,
                now);
        when(repository.save(any())).thenReturn(expectedTodo);
        when(viewRepository.findById(anyInt())).thenReturn(Optional.of(expectedVtodo));
        VTodoEntity actual = sut.saveInProject(expectedTodo.getProjectId(), expectedTodo);

        // expected
        assertEquals(expectedVtodo, actual);
        verify(repository, times(1)).save(expectedTodo);
        verify(viewRepository, times(1)).findById(expectedTodo.getId());
    }

    @Test
    void findByProjectIdByTodoId_指定したプロジェクト内で指定したIDのデータが取得できること() throws NotExistsEntityException {
        Date now = new Date();
        VTodoEntity expected = new VTodoEntity(1, "project1", 2, "title2", 10, "着手中", 100, now, now, now, now, now);
        when(viewRepository.findOne(any())).thenReturn(Optional.of(expected));
        VTodoEntity actual = sut.findByProjectIdByTodoId(expected.getProjectId(), expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByProjectIdByTodoId_指定したIDのデータが存在しない場合例外が発生すること() {
        when(viewRepository.findOne(any())).thenReturn(Optional.empty());
        NotExistsEntityException exception = assertThrows(NotExistsEntityException.class,
                () -> sut.findByProjectIdByTodoId(0, 0));
        assertEquals(exception.getMessage(), "[Todo]には、指定されたID(0)を持つデータが存在しません。");
    }

    @Test
    void update_データを更新できること() {
        // expected
        Date now = new Date();
        TodoEntity expected = new TodoEntity(0, 0, "title", 0, 0, null, now, now, null, null);
        when(entityManager.find(TodoEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ))
                .thenReturn(expected);
        when(repository.save(any())).thenReturn(expected);

        // exercise
        TodoEntity actual = sut.update(expected.getProjectId(), expected.getId(), expected);

        // verify
        assertEquals(expected, actual);
        verify(entityManager, times(1)).find(TodoEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }

    @Test
    void update_メタデータが更新されないこと() {
        // expected
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        Integer updateTargetId = 9999;
        TodoEntity stored = new TodoEntity(0, 0, "title", 0, 0, null, new Date(1), new Date(2), new Date(3), null);
        TodoEntity expected = new TodoEntity(0, 1, "title", 0, 0, null, null, null, null, null);
        when(entityManager.find(TodoEntity.class, updateTargetId, LockModeType.PESSIMISTIC_READ)).thenReturn(stored);
        when(repository.save(any())).thenReturn(expected);

        // exercise
        TodoEntity actual = sut.update(expected.getProjectId(), updateTargetId, expected);

        // verify
        assertEquals(updateTargetId, expected.getId());
        assertEquals(stored.getCreatedAt(), expected.getCreatedAt());
        assertEquals(false, stored.getUpdatedAt().equals(expected.getUpdatedAt()),
                String.format("expected: Not [%s]. but, actual is same.", stored.getUpdatedAt()));
        assertEquals(now, expected.getUpdatedAt());
        assertEquals(stored.getDeletedAt(), expected.getDeletedAt());
        verify(entityManager, times(1)).find(TodoEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }

    @Test
    void delete_データを論理削除できること() throws ApplicationException {
        // expected
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        TodoEntity expected = new TodoEntity(0, 0, "title", 0, 0, null, new Date(1), new Date(2), new Date(3), null);
        when(entityManager.find(TodoEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ))
                .thenReturn(expected);
        when(repository.save(any())).thenReturn(expected);

        // exercise
        TodoEntity actual = sut.delete(expected.getProjectId(), expected.getId());

        // verify
        assertEquals(expected, actual);
        assertEquals(now, actual.getDeletedAt());
        verify(entityManager, times(1)).find(TodoEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }
}