package com.github.northfox.ours.willbedoing.api.service;

import com.github.northfox.ours.willbedoing.api.domain.WillTodoEntity;
import com.github.northfox.ours.willbedoing.api.exception.ApplicationException;
import java.util.List;

public interface WillTodosService {
    List<WillTodoEntity> findAll();
    List<WillTodoEntity> findByKeyword(String keyword) throws ApplicationException;
    WillTodoEntity findById(Integer id) throws ApplicationException;
    List<WillTodoEntity> saveAll(String keyword, List<WillTodoEntity> entity) throws ApplicationException;
    List<WillTodoEntity> updateAll(String keyword, List<WillTodoEntity> entity);
    void deleteAll(String keyword);
}
