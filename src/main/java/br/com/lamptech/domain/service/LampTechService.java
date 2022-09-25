package br.com.lamptech.domain.service;

import br.com.lamptech.domain.erros.CodeErros;
import br.com.lamptech.domain.exceptions.GlobalException;
import br.com.lamptech.infrastructure.component.impl.LamptechComponentImpl;
import br.com.lamptech.infrastructure.entity.AccountBalance;
import br.com.lamptech.infrastructure.entity.ListAccounts;
import br.com.lamptech.infrastructure.entity.TransactionsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LampTechService {



    @Autowired
    LamptechComponentImpl lamptechComponent;

    public ListAccounts getAccountList(String customerId, String organizationid ){
        ListAccounts account = null;
       try {
             account = lamptechComponent.getAccount(customerId, organizationid);

        }catch (GlobalException e){
            throw new GlobalException(CodeErros.ERROR_003);
        }
        return account;
    }

    public AccountBalance getBalanceAccount(String customerId, String organizationid, String accountId){
        AccountBalance balanceAccount= null;
        try {
            balanceAccount = lamptechComponent.getBalanceAccount(customerId, organizationid, accountId);
        }catch (GlobalException e){
            throw new GlobalException(CodeErros.ERROR_004);
        }
        return balanceAccount;
    }

    public List<TransactionsAccount> getTransactionsAccount(String customerId, String organizationid, String accountId){
        List<TransactionsAccount> transactionsAccount= null;

        try {
            transactionsAccount = lamptechComponent.getTransactionsAccount(customerId, organizationid, accountId);

        }catch (GlobalException e){
            throw new GlobalException(CodeErros.ERROR_005);
        }

        return transactionsAccount;
    }

}
