package br.com.lamptech.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionsAccount {

    private String accountId;
    private String customerId;
    private String organizationId;
    private String organizationName;
    private String transactionId;
    private String completedAuthorisedPaymentType;
    private String acreditDebitTypeccountId;
    private String transactionName;
    private String type;
    private Double amount;
    private String transactionCurrency;
    private String transactionDate;
    private String partieCnpjCpf;
    private String partiePersonType;
    private String partieCompeCode;
    private String partieBranchCode;
    private String partieNumber;
    private Integer partieCheckDigit;
}
