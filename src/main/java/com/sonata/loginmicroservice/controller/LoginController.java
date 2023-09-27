package com.sonata.loginmicroservice.controller;

import com.sonata.loginmicroservice.model.*;
import com.sonata.loginmicroservice.serviceimpl.LoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login/")
public class LoginController {
    @Autowired
    LoginServiceImpl loginServiceImpl;

    @Operation(summary = "To get all login details",
            description = "Fetching all the available login details")
    @GetMapping("get")
    public ResponseEntity<List<Login>> getAllLogins(){

        return ResponseEntity.ok(loginServiceImpl.getLogins());
    }

    @Operation(summary = "To save a login entity",
            description = "Saving a Login Details Entity")
    @PostMapping("save")
    public ResponseEntity<Login> saveLogin(@RequestBody Login login){
        return ResponseEntity.ok(loginServiceImpl.saveLogin(login));
    }

    //Getting all certification courses list from certificate-micro-service using rest template
    @Operation(summary = "To get all the certificate details",
            description = "Fetching all the available  certification courses details")
    @GetMapping("getAllCertificates")
    public ResponseEntity<CertificateDetailsList> getAllCertificates(){
        return ResponseEntity.ok(loginServiceImpl.getAllCertificates());
    }

    //Getting a certification course from certificate-micro-service using rest template by passing certificate Id as parameter
    @Operation(summary = "To get a particular certificate details",
            description = "Fetching details of a particular certificate")
    @GetMapping("getCertificate/{certificateId}")
    public ResponseEntity<CertificateDetails> getCertificateById(@PathVariable("certificateId") long certificateId){
        return ResponseEntity.ok(loginServiceImpl.getCertificateById(certificateId));
    }

    //Getting list of employee details by passing employee Id as a parameter
    @Operation(summary = "To get a particular employee details",
            description = "Fetching details of a particular employee")
    @GetMapping("employee/{employeeId}")
    public ResponseEntity<EmployeeDetailsList> getEmployeeDetailsById(@PathVariable("employeeId") long employeeId){
        return ResponseEntity.ok(loginServiceImpl.getEmployeeDetailsById(employeeId));
    }

    //Getting all certification courses list done by a employee by passing employeeId as a parameter
    @Operation(summary = "To get certification course details of a employee",
            description = "Fetching certification course details of a particular employee")
    @GetMapping("employee/certificates/{employeeId}")
    public  ResponseEntity<CertificateDetailsList> getEmployeeCertificates(@PathVariable("employeeId") long employeeId){
        return ResponseEntity.ok(loginServiceImpl.getEmployeeCertificates(employeeId));
    }

    //Getting a list of all employee details and the details of certification courses done by them
    @Operation(summary = "To get all employee details along with certification course details",
            description = "Fetching all employee details along with certification course details done by that employee")
    @GetMapping("employeeCertificates")
    public ResponseEntity<List<Employee_Certificate>> getEmployeeCertificatesList(){
        return ResponseEntity.ok(loginServiceImpl.getEmployeeCertificateList());
    }
}
