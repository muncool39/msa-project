package com.msa.user.domain.repository;

import com.msa.user.domain.model.Role;
import com.msa.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

}
