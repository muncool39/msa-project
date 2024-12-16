package com.msa.user.domain.repository;


import com.msa.user.domain.model.Role;
import com.msa.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<User> findUsersWith(
            Pageable pageable, String username, Role role, String belongHudId, String belongCompanyId);
}
