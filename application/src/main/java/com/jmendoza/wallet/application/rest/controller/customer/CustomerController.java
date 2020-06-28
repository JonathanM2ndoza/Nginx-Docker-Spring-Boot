package com.jmendoza.wallet.application.rest.controller.customer;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.common.exception.ResourceNotFoundException;
import com.jmendoza.wallet.domain.model.customer.Customer;
import com.jmendoza.wallet.domain.ports.inbound.customer.DeleteCustomerUseCase;
import com.jmendoza.wallet.domain.ports.inbound.customer.UpdateCustomerUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteCustomer(@PathVariable(value = "id") String id) throws ResourceNotFoundException, GlobalException {
        deleteCustomerUseCase.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity updateCustomer(@PathVariable(value = "id") String id,
                                         @RequestBody Customer customer) throws ResourceNotFoundException, GlobalException {
        updateCustomerUseCase.updateCustomer(id, customer);
        return ResponseEntity.noContent().build();
    }
}
