package br.com.lamptech.infrastructure.component.impl;

import br.com.lamptech.domain.erros.CodeErros;
import br.com.lamptech.domain.exceptions.GlobalException;
import br.com.lamptech.infrastructure.component.LamptechComponent;
import br.com.lamptech.infrastructure.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class LamptechComponentImpl implements LamptechComponent {

    @Value("${openfinance.url}")
    private String openfinanceUrl;
    private static String ACCOUNTS_LIST = "/accounts/v1/accounts";
    private static String ACCOUNTS_BALANCE = "/accounts/v1/accounts/%s/balances";
    private static String TRANSACTIONS_ACCOUNT = "/accounts/v1/accounts/%s/transactions";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ListAccounts getAccount( String customerId, String organizationid){

        HttpHeaders headers = new HttpHeaders();
        headers.add("customerId", customerId);
        headers.add("organizationid", organizationid);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<DataListAccounts> response = restTemplate.exchange(buildUrl(ACCOUNTS_LIST), HttpMethod.GET, request, DataListAccounts.class);

        if(response.getStatusCode()!= HttpStatus.OK || response.getBody()==null || response.getBody().getData()==null
                || response.getBody().getData().isEmpty()){
            throw new GlobalException(CodeErros.ERROR_002);
        }
        return response.getBody().getData().stream().findFirst().get();
    }
    @Override
    public AccountBalance getBalanceAccount( String customerId, String organizationid, String accountId){

        HttpHeaders headers = new HttpHeaders();
        headers.add("customerId", customerId);
        headers.add("organizationid", organizationid);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<DataAccountsBalance> response = restTemplate.exchange(buildUrl(String.format(ACCOUNTS_BALANCE, accountId)), HttpMethod.GET, request, DataAccountsBalance.class);

        if(response.getStatusCode()!= HttpStatus.OK || response.getBody()==null || response.getBody().getData()==null
                ){
            throw new GlobalException(CodeErros.ERROR_002);
        }
        response.getBody().getData().setAvailableAmount(response.getBody().getData().getAvailableAmount()*1000);

        return response.getBody().getData();
    }

    @Override
    public List<TransactionsAccount> getTransactionsAccount(String customerId, String organizationid, String accountId){

        HttpHeaders headers = new HttpHeaders();
        headers.add("customerId", customerId);
        headers.add("organizationid", organizationid);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<DataTransactionsAccount> response = restTemplate.exchange(buildUrl(String.format(TRANSACTIONS_ACCOUNT, accountId)), HttpMethod.GET, request, DataTransactionsAccount.class);

        if(response.getStatusCode()!= HttpStatus.OK || response.getBody()==null || response.getBody().getData()==null
        || response.getBody().getData().isEmpty()){
            throw new GlobalException(CodeErros.ERROR_002);
        }
        //considero todas as transações como se fossem de luz, multiplico o valor por 1000 e é tudo de PJ

        return generateDataMock(response.getBody().getData());
    }


    @Override
    public Object getUserProfile(String customerId, String organizationid, String accountId){
        List<TransactionsAccount> transactionsAccount = getTransactionsAccount(customerId, organizationid, accountId);

        //calculo o que na lista e mostro pro front

        return null;
    }
    private List<TransactionsAccount> generateDataMock(List<TransactionsAccount> list){

        List<TransactionsAccount> mockList = new ArrayList<>();
        long timeInitial = System.currentTimeMillis();

        list.forEach(referenceTransactions->{
            TransactionsAccount transactionsAccount =new TransactionsAccount();
            transactionsAccount.setAccountId(referenceTransactions.getAccountId());
            transactionsAccount.setCustomerId(referenceTransactions.getCustomerId());
            transactionsAccount.setOrganizationId(referenceTransactions.getOrganizationId());
            transactionsAccount.setOrganizationName(referenceTransactions.getOrganizationName());
            transactionsAccount.setTransactionId(referenceTransactions.getTransactionId());
            transactionsAccount.setCompletedAuthorisedPaymentType(referenceTransactions.getCompletedAuthorisedPaymentType());
            transactionsAccount.setAcreditDebitTypeccountId(referenceTransactions.getAcreditDebitTypeccountId());
            transactionsAccount.setTransactionName("Luz");
            transactionsAccount.setType(referenceTransactions.getType());
            transactionsAccount.setAmount(referenceTransactions.getAmount()*10);
            transactionsAccount.setTransactionCurrency(referenceTransactions.getTransactionCurrency());
            transactionsAccount.setTransactionDate(referenceTransactions.getTransactionDate());
            transactionsAccount.setPartieCnpjCpf(referenceTransactions.getPartieCnpjCpf());
            transactionsAccount.setPartiePersonType(referenceTransactions.getPartiePersonType());
            transactionsAccount.setPartieCompeCode(referenceTransactions.getPartieCompeCode());
            transactionsAccount.setPartieBranchCode(referenceTransactions.getPartieBranchCode());
            transactionsAccount.setPartieNumber(referenceTransactions.getPartieNumber());
            transactionsAccount.setPartieCheckDigit(referenceTransactions.getPartieCheckDigit());
            mockList.add(transactionsAccount);
        });
        long timeFinal = System.currentTimeMillis();

        log.info(String.format("Tempo de processamento : %s", timeFinal-timeInitial));

        return mockList;
    }

    private String buildUrl(String path){
        return String.format("%s%s", openfinanceUrl, path);
    }
}
