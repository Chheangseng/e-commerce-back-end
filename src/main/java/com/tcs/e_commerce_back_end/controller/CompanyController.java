package com.tcs.e_commerce_back_end.controller;

import com.tcs.e_commerce_back_end.model.dto.company.CompanyDto;
import com.tcs.e_commerce_back_end.model.entity.Company;
import com.tcs.e_commerce_back_end.service.CompanyService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Company>> listCompany () {
        return ResponseEntity.ok(service.companyList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById (@PathVariable Long id){
        return ResponseEntity.ok(service.getCompanyById(id));
    }

    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Company> updateCompanyById (@PathVariable Long id,
                                                      @RequestParam("name") String name,
                                                      @RequestPart("logo")MultipartFile logo
                                                      ){
        return ResponseEntity.ok(service.updateCompany(id,new CompanyDto(name,logo)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        service.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
