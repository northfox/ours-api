package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.ProjectEntity;
import java.util.List;

public interface ProjectsService {
    List<ProjectEntity> all();
    ProjectEntity save(ProjectEntity entity);
}
