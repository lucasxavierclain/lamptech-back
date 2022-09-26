package br.com.lamptech.application.dto;

import br.com.lamptech.infrastructure.entity.AccountBalance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileAnalyseDTO {

    private String accountId;
    private String customerId;
    private String organizationId;
    private String organizationName;
    private String amount;
    private Boolean limitApproved;

    public ProfileAnalyseDTO(AccountBalance balanceAccount, Double totalTransactions){
        this.accountId=balanceAccount.getAccountId();
        this.customerId=balanceAccount.getCustomerId();
        this.organizationName=balanceAccount.getOrganizationName();
        this.organizationId=balanceAccount.getOrganizationId();
        this.amount=String.format("%.2f",balanceAccount.getAvailableAmount());
        if(totalTransactions > 50000){
            this.limitApproved=true;
        }else {
            this.limitApproved=false;
        }

    }
}
