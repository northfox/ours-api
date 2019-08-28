package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.ProjectRepository;
import java.util.Arrays;
import java.util.List;
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
    void save_データを登録できること() {
        ProjectEntity expected = new ProjectEntity(0, "test00", null, null, null);
        when(repository.save(any())).thenReturn(expected);
        ProjectEntity actual = sut.save(expected);
        assertEquals(expected, actual);
    }
}