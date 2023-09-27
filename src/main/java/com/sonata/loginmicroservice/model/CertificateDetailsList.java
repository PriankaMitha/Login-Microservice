package com.sonata.loginmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDetailsList {
    private List<CertificateDetails> certificateList;
}
