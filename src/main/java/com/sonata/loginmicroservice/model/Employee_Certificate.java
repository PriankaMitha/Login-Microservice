package com.sonata.loginmicroservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee_Certificate {
    private EmployeeDetails employeeDetails;
    private CertificateDetails certificateDetails;
}
