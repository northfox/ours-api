package com.github.northfox.ours.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.api.exception.ApplicationException;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException.Entities;
import com.github.northfox.ours.kyoyu.api.repository.VTodoRepository;
import com.github.northfox.ours.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.api.repository.TodoRepository;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@Slf4j
public class TodosServiceImpl implements TodosService {

    private final VTodoRepository viewRepository;
    private final TodoRepository repository;
    private final EntityManager entityManager;
    final static VTodoEntity DUMMY_ENTITY;

    static {
        Date now = new Date();
        DUMMY_ENTITY = new VTodoEntity(9999, "dummyProject", 9999, "dummy", 0, "未着手", 0, null, now, now, null, null);
    }

    @Autowired
    public TodosServiceImpl(VTodoRepository viewRepository, TodoRepository repository, EntityManager entityManager) {
        this.viewRepository = viewRepository;
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public List<VTodoEntity> all() {
        return viewRepository.findAll();
    }

    @Override
    public List<VTodoEntity> findByProjectId(Integer projectId) {
        VTodoEntity criteria = new VTodoEntity();
        criteria.setProjectId(projectId);
        Example<VTodoEntity> example = Example.of(criteria);
        return viewRepository.findAll(example);
    }

    @Override
    public VTodoEntity findByTodoId(Integer todoId) throws NotExistsEntityException {
        return viewRepository.findById(todoId)
                .orElseThrow(() -> new NotExistsEntityException(Entities.VTODO, todoId));
    }

    @Override
    public TodoEntity save(TodoEntity entity) {
        return repository.save(entity);
    }

    @Override
    public VTodoEntity saveInProject(Integer projectId, TodoEntity entity) throws NotExistsEntityException {
        entity.setProjectId(projectId);
        TodoEntity savedEntity = repository.save(entity);
        return findByTodoId(savedEntity.getId());
    }

    @Override
    public VTodoEntity findByProjectIdByTodoId(Integer projectId, Integer todoId) throws NotExistsEntityException {
        VTodoEntity criteria = new VTodoEntity();
        criteria.setProjectId(projectId);
        criteria.setId(todoId);
        Example<VTodoEntity> example = Example.of(criteria);
        return viewRepository.findOne(example)
                .orElseThrow(() -> new NotExistsEntityException(Entities.VTODO, todoId));
    }

    @Override
    public TodoEntity update(Integer projectId, Integer todoId, TodoEntity entity) {
        TodoEntity stored = entityManager.find(TodoEntity.class, todoId, LockModeType.PESSIMISTIC_READ);
        entity.setProjectId(projectId);
        entity.setId(todoId);
        entity.setCreatedAt(stored.getCreatedAt());
        entity.setUpdatedAt(DateTime.now().toDate());
        entity.setDeletedAt(stored.getDeletedAt());
        return repository.save(entity);
    }

    @Override
    public TodoEntity delete(Integer projectId, Integer todoId) throws ApplicationException {
        TodoEntity stored = entityManager.find(TodoEntity.class, todoId, LockModeType.PESSIMISTIC_READ);
        if (ObjectUtils.isEmpty(stored.getDeletedAt())) {
            stored.setDeletedAt(DateTime.now().toDate());
            return repository.save(stored);
        }
        throw new ApplicationException(
                String.format("指定されたTODOは削除済です。[id: %s, title: %s]", stored.getId(), stored.getTitle()));
    }
}
