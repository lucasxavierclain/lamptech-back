package br.com.lamptech.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListAccounts {

    private String accountId;
    private String customerId;
    private String organizationId;
    private String organizationName;


    private String brandName;
    private String companyCnpj;
    private String type;
    private String compeCode;
    private String branchCode;
    private String number;
    private Integer checkDigit;


}
