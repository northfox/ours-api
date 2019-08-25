package com.github.northfox.ours.kyoyu.kyoyu.api.service;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.domain.VTodoEntity;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.TodoRepository;
import com.github.northfox.ours.kyoyu.kyoyu.api.repository.VTodoRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class TodosServiceImpl implements TodosService {

    private final VTodoRepository viewRepository;
    private final TodoRepository repository;
    final static VTodoEntity DUMMY_ENTITY;

    static {
        Date now = new Date();
        DUMMY_ENTITY = new VTodoEntity(9999, "dummyProject", 9999, "dummy", 0, "未着手", null, now, now, null, null);
    }

    @Autowired
    public TodosServiceImpl(VTodoRepository viewRepository, TodoRepository repository) {
        this.viewRepository = viewRepository;
        this.repository = repository;
    }

    @Override
    public List<VTodoEntity> all() {
        preCall("all");
        return viewRepository.findAll();
    }

    @Override
    public TodoEntity save(TodoEntity entity) {
        preCall("save");
        return repository.save(entity);
    }

    private void preCall(String methodName) {
        log.info(String.format("execution: %s.%s()", repository.getClass().getName(), methodName));
    }
}
