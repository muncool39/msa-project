package com.msa.order.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false)
	private String createdBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@LastModifiedBy
	private String updatedBy;

	private LocalDateTime deletedAt;

	private String deletedBy;

	@LastModifiedDate
	private LocalDateTime canceledAt;

	@LastModifiedBy
	private String canceledBy;

	private Boolean isDeleted = false;

	protected void deleteOrder(String deleteBy) {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = deleteBy;
	}

}