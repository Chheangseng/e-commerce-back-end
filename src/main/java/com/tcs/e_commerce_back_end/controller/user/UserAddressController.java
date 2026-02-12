package com.tcs.e_commerce_back_end.controller.user;

import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoUserAddress;
import com.tcs.e_commerce_back_end.service.user.UserAddressService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-address")
public class UserAddressController {
    private final UserAddressService service;

    public UserAddressController(UserAddressService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<DtoUserAddress>> getUserAddress () {
        return ResponseEntity.ok(service.getAddressByUser());
    }
    @PostMapping
    public ResponseEntity<DtoUserAddress> create(@RequestBody DtoUserAddress dtoUserAddress){
        return ResponseEntity.ok(service.createUserAddress(dtoUserAddress));
    }
    @PutMapping
    public ResponseEntity<DtoUserAddress> update (@RequestBody DtoUserAddress dtoUserAddress){
        return ResponseEntity.ok(service.updateUserAddress(dtoUserAddress));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.deleteUserAddress(id);
        return ResponseEntity.ok().build();
    }
}
