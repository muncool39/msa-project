package com.msa.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="ai_request")
public class AiRequest {

    // AI 요청 데이터
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "JSON")
    private String requestData; // 요청 데이터(JSON)

    @Column(nullable = false)
    private String answer; // 응답 데이터

    @Column(nullable = false)
    private Boolean isDeleted = false;

}
