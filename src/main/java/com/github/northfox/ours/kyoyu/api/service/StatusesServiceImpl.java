package com.github.northfox.ours.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.api.exception.ApplicationException;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException.Entities;
import com.github.northfox.ours.kyoyu.api.repository.StatusRepository;
import com.github.northfox.ours.kyoyu.api.domain.StatusEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@Slf4j
public class StatusesServiceImpl implements StatusesService {

    private final StatusRepository repository;
    private final EntityManager entityManager;

    @Autowired
    public StatusesServiceImpl(StatusRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public List<StatusEntity> all() {
        return repository.findAllByOrderBySort();
    }

    @Override
    public StatusEntity save(StatusEntity entity) {
        return repository.save(entity);
    }

    @Override
    public StatusEntity find(Integer statusId) throws NotExistsEntityException {
        return repository.findById(statusId)
                .orElseThrow(() -> new NotExistsEntityException(Entities.STATUS, statusId));
    }

    @Override
    public StatusEntity update(Integer statusId, StatusEntity entity) {
        StatusEntity stored = entityManager.find(StatusEntity.class, statusId, LockModeType.PESSIMISTIC_READ);
        entity.setId(statusId);
        entity.setCreatedAt(stored.getCreatedAt());
        entity.setUpdatedAt(DateTime.now().toDate());
        entity.setDeletedAt(stored.getDeletedAt());
        return repository.save(entity);
    }

    @Override
    public StatusEntity delete(Integer statusId) throws ApplicationException {
        StatusEntity stored = entityManager.find(StatusEntity.class, statusId, LockModeType.PESSIMISTIC_READ);
        if (ObjectUtils.isEmpty(stored.getDeletedAt())) {
            stored.setDeletedAt(DateTime.now().toDate());
            return repository.save(stored);
        }
        throw new ApplicationException(
                String.format("指定されたステータスは削除済です。[id: %s, name: %s]", stored.getId(), stored.getName()));
    }
}
