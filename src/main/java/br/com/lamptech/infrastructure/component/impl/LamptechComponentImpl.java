package br.com.lamptech.infrastructure.component.impl;

import br.com.lamptech.application.dto.ProfileAnalyse;
import br.com.lamptech.domain.OrganizationInfos;
import br.com.lamptech.domain.erros.CodeErros;
import br.com.lamptech.domain.exceptions.GlobalException;
import br.com.lamptech.infrastructure.component.LamptechComponent;
import br.com.lamptech.infrastructure.entity.*;
import br.com.lamptech.infrastructure.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Slf4j
@Component
public class LamptechComponentImpl implements LamptechComponent {

    @Value("${openfinance.url}")
    private String openfinanceUrl;
    private static String ACCOUNTS_LIST = "/accounts/v1/accounts";
    private static String ACCOUNTS_BALANCE = "/accounts/v1/accounts/%s/balances";
    private static String TRANSACTIONS_ACCOUNT = "/accounts/v1/accounts/%s/transactions";

    private static final String CUSTOMER_ID= "customerId";
    private static final String ORGANIZATION_ID= "organizationid";
    private Integer miniLimit = 50000;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ListAccounts getAccount(String customerId, String organizationid) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CUSTOMER_ID, customerId);
        headers.add(ORGANIZATION_ID, organizationid);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<DataListAccounts> response = restTemplate.exchange(buildUrl(ACCOUNTS_LIST), HttpMethod.GET, request, DataListAccounts.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null
                || response.getBody().getData() == null
                || response.getBody().getData().isEmpty()) {

            throw new GlobalException(CodeErros.ERROR_002);

        }

        return response.getBody().getData().stream().findFirst().get();
    }

    @Override
    public AccountBalance getBalanceAccount(String customerId, String organizationid, String accountId) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CUSTOMER_ID, customerId);
        headers.add(ORGANIZATION_ID, organizationid);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<DataAccountsBalance> response = restTemplate.exchange(buildUrl(String.format(ACCOUNTS_BALANCE, accountId)), HttpMethod.GET, request, DataAccountsBalance.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null ||
                response.getBody().getData() == null) {

            throw new GlobalException(CodeErros.ERROR_004);

        }

        if (response.getBody().getData().getAvailableAmount() == null) {

            response.getBody().getData().setAvailableAmount(0.0);

        } else {

            //nossas empressas não são devedoras
            if(response.getBody().getData().getAvailableAmount()<0){
                response.getBody().getData().setAvailableAmount(response.getBody().getData().getAvailableAmount() * -1);
            }
            response.getBody().getData().setAvailableAmount(response.getBody().getData().getAvailableAmount() * 10000);

        }

        return response.getBody().getData();
    }

    @Override
    public List<TransactionsAccount> getTransactionsAccount(String customerId, String organizationid, String accountId) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CUSTOMER_ID, customerId);
        headers.add(ORGANIZATION_ID, organizationid);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<DataTransactionsAccount> response = restTemplate.exchange(buildUrl(String.format(TRANSACTIONS_ACCOUNT, accountId)), HttpMethod.GET, request, DataTransactionsAccount.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().getData() == null
                || response.getBody().getData().isEmpty()) {

            throw new GlobalException(CodeErros.ERROR_005);

        }

        return generateDataMock(response.getBody().getData());
    }


    @Override
    public ProfileAnalyse getProfileAnalyse(AccountBalance balanceAccount, List<TransactionsAccount> transactionsAccount) {

        if (balanceAccount.getAvailableAmount() == null || miniLimit > balanceAccount.getAvailableAmount()) {

            throw new GlobalException(CodeErros.ERROR_006);

        }

        AtomicReference<Double> totalAccount = new AtomicReference<>(0.0);

        Stream<TransactionsAccount> transactionsAccountStream = transactionsAccount.stream().filter(transactions -> "Luz".equals(transactions.getTransactionName()));

        transactionsAccountStream.forEach(t -> {
            double amount = t.getAmount() + totalAccount.get();
            totalAccount.updateAndGet(v -> v + amount);

        });

        ProfileAnalyse profileAnalyse = new ProfileAnalyse(balanceAccount, totalAccount.get());

        return profileAnalyse;
    }

    private List<TransactionsAccount> generateDataMock(List<TransactionsAccount> list) {

        list.forEach(referenceTransactions -> {
            referenceTransactions.setAmount(referenceTransactions.getAmount() * 100);
        });

        return list;
    }

    private String buildUrl(String path) {
        return String.format("%s%s", openfinanceUrl, path);
    }
}
