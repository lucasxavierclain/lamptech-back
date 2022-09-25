package br.com.lamptech.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBalance {

    private String accountId;
    private String customerId;
    private String organizationId;
    private String organizationName;
    private Double availableAmount;
    private String availableAmountCurrency;
    private Integer blockedAmount;
    private String blockedAmountCurrency;
    private Integer automaticallyInvestedAmount;
    private String automaticallyInvestedAmountCurrency;




}