package com.github.northfox.ours.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.api.domain.ProjectEntity;
import java.util.List;

public interface ProjectsService {
    List<ProjectEntity> all();
    ProjectEntity find(Integer projectId) throws NotExistsEntityException;
    ProjectEntity save(ProjectEntity entity);
}
