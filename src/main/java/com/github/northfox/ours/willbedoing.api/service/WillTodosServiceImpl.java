package com.github.northfox.ours.willbedoing.api.service;

import com.github.northfox.ours.willbedoing.api.domain.WillBackupEntity;
import com.github.northfox.ours.willbedoing.api.domain.WillTodoEntity;
import com.github.northfox.ours.willbedoing.api.exception.ApplicationException;
import com.github.northfox.ours.willbedoing.api.repository.WillBackupRepository;
import com.github.northfox.ours.willbedoing.api.repository.WillTodoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WillTodosServiceImpl implements WillTodosService {

  private final WillBackupRepository willBackupRepository;
  private final WillTodoRepository willTodoRepository;

  @Override
  public List<WillTodoEntity> findAll() {
    return willTodoRepository.findAll();
  }

  @Override
  public List<WillTodoEntity> findByKeyword(String keyword) throws ApplicationException {
    WillBackupEntity backupCriteria = WillBackupEntity.builder()
        .savedKeyword(keyword)
        .build();
    WillBackupEntity foundBackup = willBackupRepository.findOne(Example.of(backupCriteria))
        .orElseThrow(() -> new ApplicationException("指定されたキーワードに紐づくバックアップは取得できません。"));

    WillTodoEntity criteria = WillTodoEntity.builder()
        .backupId(foundBackup.getId())
        .build();

    return willTodoRepository.findAll(Example.of(criteria));
  }

  @Override
  public WillTodoEntity findById(Integer id) throws ApplicationException {
    return willTodoRepository.findById(id)
        .orElseThrow(() -> new ApplicationException("指定されたIDのTODOは取得できません。"));
  }

  @Override
  @Transactional(rollbackFor = {ApplicationException.class})
  public List<WillTodoEntity> saveAll(String keyword, List<WillTodoEntity> entity) throws ApplicationException {
    Date now = new Date();
    String requestedBy = "service";
    WillBackupEntity backup = WillBackupEntity.builder()
        .savedKeyword(keyword)
        .createdAt(now)
        .createdBy(requestedBy)
        .updatedAt(now)
        .updatedBy(requestedBy)
        .build();
    Optional<WillBackupEntity> foundBackup = willBackupRepository.findOne(Example.of(backup));
    if (foundBackup.isPresent()) {
      throw new ApplicationException("禁止ワードか使用済のキーワードが指定されました。");
    } else {
      backup = willBackupRepository.save(backup);
    }

    Integer backupId = backup.getId();
    entity.forEach(e -> {
      e.setBackupId(backupId);
      e.setUpdatedAt(now);
      e.setUpdatedBy(requestedBy);
    });

    return willTodoRepository.saveAll(entity);
  }

  @Override
  public List<WillTodoEntity> updateAll(String keyword, List<WillTodoEntity> entity) {
    return null;
  }

  @Override
  public void deleteAll(String keyword) {

  }
}
