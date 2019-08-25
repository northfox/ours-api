package com.github.northfox.ours.kyoyu.kyoyu.api.repository;

import com.github.northfox.ours.kyoyu.kyoyu.api.domain.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

}
