package com.sonata.loginmicroservice.serviceimpl;

import com.sonata.loginmicroservice.model.*;
import com.sonata.loginmicroservice.repository.LoginRepository;
import com.sonata.loginmicroservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Value("${http://CERTIFICATE-MICRO-SERVICE/certificate}")
    private String certificateUrl;
    @Value("${http://EMPLOYEE-MICRO-SERVICE/employee}")
    private String employeeUrl;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public List<Login> getLogins() {
        try {
            logger.info("Retrieving login details");

            List<Login> logins = loginRepository.findAll();

            logger.info("Retrieved {} login details", logins.size());

            return logins;
        } catch (Exception e) {
            logger.error("Error retrieving login details: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve login details", e);
        }
    }

    @Override
    public Login saveLogin(Login login) {
        try {
            logger.info("Saving login: {}", login);

            Login savedLogin = loginRepository.save(login);

            logger.info("Login saved: {}", savedLogin);

            return savedLogin;
        } catch (Exception e) {
            logger.error("Error saving login: {}", e.getMessage());
            throw new RuntimeException("Failed to save login", e);
        }
    }


    //Getting all certification courses list from certificate-micro-service using rest template
    @Override
    public CertificateDetailsList getAllCertificates() {
        try {
            logger.info("Retrieving all certificates");

            CertificateDetailsList certificates = restTemplate.getForObject(certificateUrl, CertificateDetailsList.class);

            if (logger.isDebugEnabled()) {
                logger.debug("Retrieved certificates: {}", certificates);
            }

            logger.info("Retrieved {} certificates", certificates.getCertificateList().size());

            return certificates;
        } catch (Exception e) {
            logger.error("Error retrieving certificates: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve certificates", e);
        }
    }


    //Getting a certification course from certificate-micro-service using rest template by passing certificate Id as parameter
    @Override
    public CertificateDetails getCertificateById(long certificateId) {
        try {
            logger.info("Retrieving certificate with ID: {}", certificateId);

            CertificateDetails certificate = restTemplate.getForObject(
                    certificateUrl + "/{certificateId}", CertificateDetails.class, certificateId);

            if (logger.isDebugEnabled()) {
                logger.debug("Retrieved certificate: {}", certificate);
            }

            logger.info("Retrieved certificate with ID: {}", certificateId);

            return certificate;
        } catch (Exception e) {
            logger.error("Error retrieving certificate with ID {}: {}", certificateId, e.getMessage());
            throw new RuntimeException("Failed to retrieve certificate", e);
        }
    }


    //Getting list of employee details by passing employee Id as a parameter
    @Override
    public EmployeeDetailsList getEmployeeDetailsById(long employeeId) {
        try {
            logger.info("Retrieving employee details with ID: {}", employeeId);

            EmployeeDetailsList employeeDetails = restTemplate.getForObject(
                    employeeUrl + "/{employeeId}", EmployeeDetailsList.class, employeeId);

            if (logger.isDebugEnabled()) {
                logger.debug("Retrieved employee details: {}", employeeDetails);
            }

            logger.info("Retrieved employee details with ID: {}", employeeId);

            return employeeDetails;
        } catch (Exception e) {
            logger.error("Error retrieving employee details with ID {}: {}", employeeId, e.getMessage());
            throw new RuntimeException("Failed to retrieve employee details", e);
        }
    }


    //Getting all certification courses list done by a employee by passing employeeId as a parameter
    @Override
    public CertificateDetailsList getEmployeeCertificates(long employeeId) {
        try {
            logger.info("Retrieving certificates for employee with ID: {}", employeeId);

            CertificateDetailsList certificateDetailsList = restTemplate.getForObject(
                    employeeUrl + "/certificate/{employeeId}", CertificateDetailsList.class, employeeId);

            if (logger.isDebugEnabled()) {
                logger.debug("Retrieved certificates: {}", certificateDetailsList);
            }

            logger.info("Retrieved certificates for employee with ID: {}", employeeId);

            return certificateDetailsList;
        } catch (Exception e) {
            logger.error("Error retrieving certificates for employee with ID {}: {}", employeeId, e.getMessage());
            throw new RuntimeException("Failed to retrieve certificates for employee", e);
        }
    }



    //Getting a list of all employee details and the details of certification courses done by them
    @Override
    public List<Employee_Certificate> getEmployeeCertificateList() {
        try {
            logger.info("Retrieving employee certificate list");

            List<EmployeeDetails> employeeList = restTemplate.getForObject(
                    employeeUrl, EmployeeDetailsList.class).getEmployeeList();

            CertificateDetailsList certificateDetailsList = this.getAllCertificates();
            List<CertificateDetails> certificateList = certificateDetailsList.getCertificateList();

            List<Employee_Certificate> employeeCertificateList = new ArrayList<>();

            for (EmployeeDetails employee : employeeList) {
                CertificateDetails certificate = certificateList.stream()
                        .filter(c -> c.getCertificateId() == employee.getCertificateId())
                        .findFirst()
                        .orElse(null);

                if (certificate != null) {
                    Employee_Certificate employeeCertificate = new Employee_Certificate(employee, certificate);
                    employeeCertificateList.add(employeeCertificate);
                } else {
                    logger.warn("Certificate not found for employee with ID: {}", employee.getEmployeeId());
                }
            }

            logger.info("Retrieved {} employee certificate records", employeeCertificateList.size());

            return employeeCertificateList;
        } catch (Exception e) {
            logger.error("Error retrieving employee certificate list: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve employee certificate list", e);
        }
    }

}
