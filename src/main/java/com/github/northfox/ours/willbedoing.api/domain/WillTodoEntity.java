package com.github.northfox.ours.willbedoing.api.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
@Table(name = "trx_will_todo")
public class WillTodoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "backup_id")
  private Integer backup_id;
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
  private Date done_at;
  @Column(name = "done_by")
  private String done_by;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date created_at = DateTime.now().toDate();
  @Column(name = "created_by")
  private String created_by;
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated_at = DateTime.now().toDate();
  @Column(name = "updated_by")
  private String updated_by;
  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date deleted_at;
  @Column(name = "deleted_by")
  private String deleted_by;
}
