package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.StatusRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class StatusesServiceImplTest {

    @MockBean
    private StatusRepository repository;

    private StatusesService sut;

    @BeforeEach
    void setup() {
        sut = new StatusesServiceImpl(repository);
    }

    @Test
    void all_すべてのデータが取得できること() {
        List<StatusEntity> expected = Arrays.asList(
                new StatusEntity(0, "test00", 0, null, null, null),
                new StatusEntity(10, "test10", 10, null, null, null));
        when(repository.findAllByOrderBySort()).thenReturn(expected);
        List<StatusEntity> actual = sut.all();
        assertEquals(expected, actual);
    }

    @Test
    void save_データを登録できること() {
        StatusEntity expected = new StatusEntity(0, "test00", 0, null, null, null);
        when(repository.save(any())).thenReturn(expected);
        StatusEntity actual = sut.save(expected);
        assertEquals(expected, actual);
    }

}