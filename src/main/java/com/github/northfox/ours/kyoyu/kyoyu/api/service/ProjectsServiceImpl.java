package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.ProjectEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException;
import com.github.northfox.ours.kyoyu.kyoyu.api.exception.NotExistsEntityException.Entities;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.ProjectRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class ProjectsServiceImpl implements ProjectsService {

    private final ProjectRepository repository;

    @Autowired
    public ProjectsServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProjectEntity> all() {
        return repository.findAll();
    }

    @Override
    public ProjectEntity find(Integer projectId) throws NotExistsEntityException {
        return repository.findById(projectId)
                .orElseThrow(() -> new NotExistsEntityException(Entities.PROJECT, projectId));
    }

    @Override
    public ProjectEntity save(ProjectEntity entity) {
        return repository.save(entity);
    }
}
