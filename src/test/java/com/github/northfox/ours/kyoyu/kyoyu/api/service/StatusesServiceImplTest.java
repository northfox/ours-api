package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.ApplicationException;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.StatusRepository;
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
    void find_指定したIDのデータが取得できること() throws NotExistsEntityException {
        StatusEntity expected = new StatusEntity(0, "test00", 0, null, null, null);
        when(repository.findById(anyInt())).thenReturn(Optional.of(expected));
        StatusEntity actual = sut.find(10);
        assertEquals(expected, actual);
    }

    @Test
    void find_指定したIDのデータが存在しない場合例外が発生すること() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        NotExistsEntityException exception = assertThrows(NotExistsEntityException.class,
                () -> sut.find(10));
        assertEquals(exception.getMessage(), "[ステータス]には、指定されたID(10)を持つデータが存在しません。");
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
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
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
        assertEquals(now, expected.getUpdatedAt());
        assertEquals(stored.getDeletedAt(), expected.getDeletedAt());
        verify(entityManager, times(1)).find(StatusEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }

    @Test
    void delete_データを論理削除できること() throws ApplicationException {
        // expected
        DateTimeUtils.setCurrentMillisFixed(10L);
        Date now = DateTime.now().toDate();
        StatusEntity expected = new StatusEntity(0, "test00", 0, new Date(1), new Date(2), null);
        when(entityManager.find(StatusEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ))
                .thenReturn(expected);
        when(repository.save(any())).thenReturn(expected);

        // exercise
        StatusEntity actual = sut.delete(expected.getId());

        // verify
        assertEquals(expected, actual);
        assertEquals(now, actual.getDeletedAt());
        verify(entityManager, times(1)).find(StatusEntity.class, expected.getId(), LockModeType.PESSIMISTIC_READ);
        verify(repository, times(1)).save(expected);
    }
}