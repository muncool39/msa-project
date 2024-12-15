package com.msa.user.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_USER_USERNAME", columnNames = "username")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="username", nullable=false)
    private String username;
    @Column(name="password", nullable=false)
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="slack_id")
    private String slackId;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;
    @Column(name="belong_hub_id")
    private String belongHubId;
    @Column(name = "belong_company_id")
    private String belongCompanyId;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String username, String password, String email, String slackId, Role role, String belongHubId, String belongCompanyId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.slackId = slackId;
        this.role = role;
        this.belongHubId = belongHubId;
        this.belongCompanyId = belongCompanyId;
    }

    public void update(String username, String password, String email, String slackId) {
        if (username!=null) this.username = username;
        if (password!=null) this.password = password;
        if (email!=null) this.email = email;
        if (slackId!=null) this.slackId = slackId;
    }

    public void setBelongHub(String belongHubId) {
        this.belongHubId = belongHubId;
    }

    public static User createBy(
            String username, String password, String email, String slackId, Role role
    ) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .slackId(slackId)
                .role(role)
                .build();
    }

}
