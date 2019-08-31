package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.StatusEntity;
import java.util.List;

public interface StatusesService {
    List<StatusEntity> all();
    StatusEntity save(StatusEntity entity);
    StatusEntity update(Integer statusId, StatusEntity entity);
}
