package com.tcs.e_commerce_back_end.service;

import java.util.List;

import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.company.CompanyDto;
import com.tcs.e_commerce_back_end.model.entity.Company;
import com.tcs.e_commerce_back_end.repository.CompanyRepository;
import com.tcs.e_commerce_back_end.service.file.FileSystemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
  private final CompanyRepository companyRepository;
  private final FileSystemService fileSystemService;

  public List<Company> companyList() {
    return companyRepository.findAll();
  }

  public Company createCompany(CompanyDto companyDto) {
    if (companyDto.getName() == null || companyDto.getName().isEmpty()) {
      throw new ApiExceptionStatusException("company name is required", 400);
    }
    var company = new Company();
    company.setName(companyDto.getName());
    if (companyDto.getFile() != null) {
      company.setLogoId(fileSystemService.saveFile(companyDto.getFile(), FileDirCategory.COMPANY_FILES));
    }
    return companyRepository.save(company);
  }

  public Company getCompanyById(Long id) {
    return companyRepository
        .findById(id)
        .orElseThrow(() -> new ApiExceptionStatusException("company not found", 404));
  }

  public Company updateCompany(Long id, CompanyDto companyDto) {
    var company = getCompanyById(id);
    company.setName(companyDto.getName());
    if (companyDto.getFile() != null) {
      fileSystemService.deleteFileById(company.getLogoId());
      company.setLogoId(fileSystemService.saveFile(companyDto.getFile(),FileDirCategory.COMPANY_FILES));
    }
    return companyRepository.save(company);
  }

  public void deleteCompany(Long id) {
    var company = getCompanyById(id);
    companyRepository.deleteById(company.getId());
  }
}
