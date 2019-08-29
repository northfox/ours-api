package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException;
import java.util.List;

public interface TodosService {
    List<VTodoEntity> all();
    List<VTodoEntity> findByProjectId(Integer projectId);
    VTodoEntity findByTodoId(Integer todoId) throws NotExistsEntityException;
    TodoEntity save(TodoEntity entity);
    VTodoEntity saveInProject(Integer projectId, TodoEntity entity) throws NotExistsEntityException;
}
