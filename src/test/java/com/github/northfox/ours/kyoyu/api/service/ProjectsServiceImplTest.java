package com.github.northfox.ours.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.api.repository.ProjectRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProjectsServiceImplTest {


    @MockBean
    private ProjectRepository repository;

    private ProjectsService sut;

    @BeforeEach
    void setup() {
        sut = new ProjectsServiceImpl(repository);
    }

    @Test
    void all_すべてのデータが取得できること() {
        List<ProjectEntity> expected = Arrays.asList(
                new ProjectEntity(0, "test00", null, null, null),
                new ProjectEntity(10, "test10", null, null, null));
        when(repository.findAll()).thenReturn(expected);
        List<ProjectEntity> actual = sut.all();
        assertEquals(expected, actual);
    }

    @Test
    void find_指定したIDのデータが取得できること() throws NotExistsEntityException {
        ProjectEntity expected = new ProjectEntity(10, "test10", null, null, null);
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));
        ProjectEntity actual = sut.find(10);
        assertEquals(expected, actual);
    }

    @Test
    void find_指定したIDのデータが存在しない場合例外が発生すること() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        NotExistsEntityException exception = assertThrows(NotExistsEntityException.class,
                () -> sut.find(10));
        assertEquals(exception.getMessage(), "[プロジェクト]には、指定されたID(10)を持つデータが存在しません。");
    }

    @Test
    void save_データを登録できること() {
        ProjectEntity expected = new ProjectEntity(0, "test00", null, null, null);
        when(repository.save(any())).thenReturn(expected);
        ProjectEntity actual = sut.save(expected);
        assertEquals(expected, actual);
    }
}