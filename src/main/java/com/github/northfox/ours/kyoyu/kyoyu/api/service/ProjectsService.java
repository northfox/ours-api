package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException;
import java.util.List;

public interface ProjectsService {
    List<ProjectEntity> all();
    ProjectEntity find(Integer projectId) throws NotExistsEntityException;
    ProjectEntity save(ProjectEntity entity);
}
