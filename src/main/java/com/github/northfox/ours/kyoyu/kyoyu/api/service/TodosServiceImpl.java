package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException.Entities;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.TodoRepository;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.VTodoRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class TodosServiceImpl implements TodosService {

    private final VTodoRepository viewRepository;
    private final TodoRepository repository;
    final static VTodoEntity DUMMY_ENTITY;

    static {
        Date now = new Date();
        DUMMY_ENTITY = new VTodoEntity(9999, "dummyProject", 9999, "dummy", 0, "未着手", 0, null, now, now, null, null);
    }

    @Autowired
    public TodosServiceImpl(VTodoRepository viewRepository, TodoRepository repository) {
        this.viewRepository = viewRepository;
        this.repository = repository;
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
}
