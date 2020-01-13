package com.github.northfox.ours.willbedoing.api.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "trx_will_todo",
    indexes = {@Index(name = "idx_will_saved_keyword", columnList = "saved_keyword", unique = true)})
public class WillBackupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "backup_id")
  private Integer backupId;
  @Column(name = "sense")
  private String sense;
  @Column(name = "content")
  private String content;
  @Column(name = "iine")
  private Integer iine = 0;
  @Column(name = "priority")
  private Integer priority = 0;
  @Column(name = "done_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date doneAt;
  @Column(name = "done_by")
  private String doneBy;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt = DateTime.now().toDate();
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt = DateTime.now().toDate();
  @Column(name = "updated_by")
  private String updatedBy;
  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date deletedAt;
  @Column(name = "deleted_by")
  private String deletedBy;
}
