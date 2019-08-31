package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.StatusRepository;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.TodoRepository;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.VTodoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

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
    public StatusEntity update(Integer statusId, StatusEntity entity) {
        StatusEntity foundEntity = entityManager.find(StatusEntity.class, statusId, LockModeType.PESSIMISTIC_READ);
        entity.setId(statusId);
        entity.setCreatedAt(foundEntity.getCreatedAt());
        entity.setUpdatedAt(new Date());
        entity.setDeletedAt(foundEntity.getDeletedAt());
        return repository.save(entity);
    }
}
