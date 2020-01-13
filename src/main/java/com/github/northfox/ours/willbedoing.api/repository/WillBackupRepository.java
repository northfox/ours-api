package com.github.northfox.ours.willbedoing.api.repository;

import com.github.northfox.ours.willbedoing.api.domain.WillBackupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WillBackupRepository extends JpaRepository<WillBackupEntity, Integer> {

}
