package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.StatusRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class StatusesServiceImplTest {

    @MockBean
    private StatusRepository repository;

    @MockBean
    private EntityManager entityManager;

    private StatusesService sut;

    @BeforeEach
    void setup() {
        sut = new StatusesServiceImpl(repository, entityManager);
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

    @Test
    void update_データを更新できること() {
        // expected
        StatusEntity expected = new StatusEntity(0, "test00", 0, null, null, null);
        when(entityManager.find(StatusEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ))
                .thenReturn(expected);
        when(repository.save(any())).thenReturn(expected);

        // exercise
        StatusEntity actual = sut.update(expected.getId(), expected);

        // verify
        assertEquals(expected, actual);
        verify(entityManager, times(1)).find(StatusEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }

    @Test
    void update_メタデータが更新されないこと() {
        // expected
        Integer updateTargetId = 9999;
        StatusEntity stored = new StatusEntity(1, "test01", 1, new Date(1), new Date(2), new Date(3));
        StatusEntity expected = new StatusEntity(0, "test00", 0, null, null, null);
        when(entityManager.find(StatusEntity.class, updateTargetId, LockModeType.PESSIMISTIC_READ)).thenReturn(stored);
        when(repository.save(any())).thenReturn(expected);

        // exercise
        StatusEntity actual = sut.update(updateTargetId, expected);

        // verify
        assertEquals(updateTargetId, expected.getId());
        assertEquals(stored.getCreatedAt(), expected.getCreatedAt());
        assertEquals(false, stored.getUpdatedAt().equals(expected.getUpdatedAt()),
                String.format("expected: Not [%s]. but, actual is same.", stored.getUpdatedAt()));
        assertEquals(stored.getDeletedAt(), expected.getDeletedAt());
        verify(entityManager, times(1)).find(StatusEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }

}