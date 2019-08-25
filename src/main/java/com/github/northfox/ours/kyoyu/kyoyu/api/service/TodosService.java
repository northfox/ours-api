package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import java.util.List;

public interface TodosService {
    List<VTodoEntity> all();
    TodoEntity save(TodoEntity entity);
}
