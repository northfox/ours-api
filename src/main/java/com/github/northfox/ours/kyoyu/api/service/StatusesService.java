package com.github.northfox.ours.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.api.exception.ApplicationException;
import com.github.northfox.ours.kyoyu.api.domain.StatusEntity;
import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import java.util.List;

public interface StatusesService {
    List<StatusEntity> all();
    StatusEntity save(StatusEntity entity);
    StatusEntity find(Integer statusId) throws NotExistsEntityException;
    StatusEntity update(Integer statusId, StatusEntity entity);
    StatusEntity delete(Integer statusId) throws ApplicationException;
}
