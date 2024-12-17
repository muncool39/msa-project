package com.msa.company.domain.repository.company;

import com.msa.company.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

    Page<Company> getListCompanies(String hubId, String name, String type, String status, String address, Pageable pageable);
}
