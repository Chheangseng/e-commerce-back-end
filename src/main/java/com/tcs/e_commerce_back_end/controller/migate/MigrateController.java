package com.tcs.e_commerce_back_end.controller.migate;

import com.tcs.e_commerce_back_end.service.migration.MigrationService;
import com.tcs.e_commerce_back_end.service.user.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/migrate")
public class MigrateController {
  private final MigrationService service;
  private final UserManagementService userManagementService;

  public MigrateController(MigrationService service, UserManagementService userManagementService) {
    this.service = service;
    this.userManagementService = userManagementService;
  }

  @PostMapping("/default-value")
  public ResponseEntity<Void> insertDefaultProductCategory() {
    service.insertDefaultDataProduct(service.insertDefaultDataCategory());
    userManagementService.insertDefaultAdmin();
    userManagementService.insertDefaultTestingUser();
    return ResponseEntity.ok().build();
  }
}
