package com.github.northfox.ours.kyoyu.api.repository;

import com.github.northfox.ours.kyoyu.api.domain.VTodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VTodoRepository extends JpaRepository<VTodoEntity, Integer> {

}
