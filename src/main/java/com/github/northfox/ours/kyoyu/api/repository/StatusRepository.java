package com.github.northfox.ours.kyoyu.api.repository;

import com.github.northfox.ours.kyoyu.api.domain.StatusEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    List<StatusEntity> findAllByOrderBySort();
}
