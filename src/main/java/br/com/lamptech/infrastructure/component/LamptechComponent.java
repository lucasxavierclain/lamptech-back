package br.com.lamptech.infrastructure.component;

import br.com.lamptech.infrastructure.entity.AccountBalance;
import br.com.lamptech.infrastructure.entity.ListAccounts;
import br.com.lamptech.infrastructure.entity.TransactionsAccount;

import java.util.List;

public interface LamptechComponent {

    ListAccounts getAccount(String customerId, String organizationid);

    AccountBalance getBalanceAccount(String customerId, String organizationid, String accountId);

    List<TransactionsAccount> getTransactionsAccount(String customerId, String organizationid, String accountId);

    Object getUserProfile(String customerId, String organizationid, String accountId);
}
