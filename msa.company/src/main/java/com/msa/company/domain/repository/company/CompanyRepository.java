package com.msa.company.domain.repository.company;

import com.msa.company.domain.entity.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>, CompanyRepositoryCustom {

    boolean existsByBusinessNumber(String businessNumber);
}
