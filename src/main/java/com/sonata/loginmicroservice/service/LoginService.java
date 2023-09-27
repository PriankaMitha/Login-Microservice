package com.sonata.loginmicroservice.service;

import com.sonata.loginmicroservice.model.*;

import java.util.List;

public interface LoginService {

    public List<Login> getLogins();
    public Login saveLogin(Login login);
    public CertificateDetailsList getAllCertificates();
    public  CertificateDetails getCertificateById(long certificateId);
    public EmployeeDetailsList getEmployeeDetailsById(long employeeId);
    public CertificateDetailsList getEmployeeCertificates(long employeeId);

    public List<Employee_Certificate> getEmployeeCertificateList();
}
