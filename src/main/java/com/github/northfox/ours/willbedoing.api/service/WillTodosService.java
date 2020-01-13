package com.github.northfox.ours.willbedoing.api.service;

import com.github.northfox.ours.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.willbedoing.api.domain.WillTodoEntity;
import com.github.northfox.ours.willbedoing.api.exception.ApplicationException;
import java.util.List;

public interface WillTodosService {
    List<WillTodoEntity> all();
    List<WillTodoEntity> findByKeyword(String keyword) throws ApplicationException;
    WillTodoEntity findById(Integer id) throws ApplicationException;
    List<WillTodoEntity> saveAll(String keyword, List<WillTodoEntity> entity);
    List<WillTodoEntity> updateAll(String keyword, List<WillTodoEntity> entity);
    void deleteAll(String keyword);
}
