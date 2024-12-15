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
	@Column(updatable = false, name = "created_at")
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false, name = "created_by")
	private String createdBy;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@LastModifiedBy
	@Column(name = "update_by")
	private String updatedBy;

	private LocalDateTime deletedAt;

	private String deletedBy;

	private LocalDateTime canceledAt;

	private String canceledBy;

	private Boolean isDeleted = false;

	protected void deleteOrder(String deleteBy) {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = deleteBy;
	}

	protected void cancelOrder(String canceledBy) {
		this.canceledAt = LocalDateTime.now();
		this.canceledBy = canceledBy;
	}

}