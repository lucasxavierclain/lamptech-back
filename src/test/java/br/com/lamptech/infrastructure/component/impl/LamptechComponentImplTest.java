package br.com.lamptech.infrastructure.component.impl;

import br.com.lamptech.application.dto.ProfileAnalyseDTO;
import br.com.lamptech.domain.exceptions.GlobalException;
import br.com.lamptech.infrastructure.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LamptechComponentImplTest {

    @InjectMocks
    LamptechComponentImpl lamptechComponent;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void setUp(){
        ReflectionTestUtils.setField(lamptechComponent, "openfinanceUrl", "openfinanceUrl");

    }

    @Test
    public void testGetAccount(){
        DataListAccounts dataListAccounts= new DataListAccounts();
        List<ListAccounts> data= new ArrayList<>();
        ListAccounts listAccounts = new ListAccounts();
        data.add(listAccounts);
        listAccounts.setAccountId("accountId");
        dataListAccounts.setData(data);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(DataListAccounts.class))).thenReturn(ResponseEntity.ok(dataListAccounts));
        ListAccounts account = lamptechComponent.getAccount("customerId", "organizationId");
        assertEquals("should be equals", account.getAccountId(), listAccounts.getAccountId());

    }

    @Test(expected = GlobalException.class)
    public void testGetAccountException(){
        DataListAccounts dataListAccounts= new DataListAccounts();
        List<ListAccounts> data= new ArrayList<>();
        ListAccounts listAccounts = new ListAccounts();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(DataListAccounts.class))).thenReturn(ResponseEntity.ok(dataListAccounts));
        ListAccounts account = lamptechComponent.getAccount("customerId", "organizationId");
        assertEquals("should be equals", account.getAccountId(), listAccounts.getAccountId());

    }

    @Test
    public void testGetBalanceAccount(){
        DataAccountsBalance dataAccountsBalance = new DataAccountsBalance();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("accountId");
        accountBalance.setAvailableAmount(-1.0);
        dataAccountsBalance.setData(accountBalance);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(DataAccountsBalance.class))).thenReturn(ResponseEntity.ok(dataAccountsBalance));

        AccountBalance balanceAccount = lamptechComponent.getBalanceAccount("customerId", "organizationId", "accountId");

        assertEquals("should be equals", accountBalance.getAccountId(), balanceAccount.getAccountId());
        assertEquals("should be equals", accountBalance.getAvailableAmount(), balanceAccount.getAvailableAmount());
        assertTrue("should be TRUE",  balanceAccount.getAvailableAmount() > 999.0);
    }
    @Test(expected = GlobalException.class)
    public void testGetBalanceAccountException(){
        DataAccountsBalance dataAccountsBalance = new DataAccountsBalance();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(DataAccountsBalance.class))).thenReturn(ResponseEntity.ok(dataAccountsBalance));

        AccountBalance balanceAccount = lamptechComponent.getBalanceAccount("customerId", "organizationId", "accountId");

        assertNotNull("not null", lamptechComponent);
    }

    @Test
    public void testGetTransactionsAccount(){
        DataTransactionsAccount dataTransactionsAccount= new DataTransactionsAccount();
        List<TransactionsAccount> data= new ArrayList<>();
        TransactionsAccount transactionsAccount= new TransactionsAccount();
        transactionsAccount.setAccountId("accountId");
        transactionsAccount.setAmount(10000.0);
        data.add(transactionsAccount);
        dataTransactionsAccount.setData(data);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(DataTransactionsAccount.class))).thenReturn(ResponseEntity.ok(dataTransactionsAccount));
        List<TransactionsAccount> response = lamptechComponent.getTransactionsAccount("customerId", "organizationId", "accountId");

        assertEquals("should be equals", response.get(0).getAccountId(),transactionsAccount.getAccountId());
        assertEquals("should be equals", response.get(0).getAmount(),transactionsAccount.getAmount());
    }

    @Test(expected = GlobalException.class)
    public void testGetTransactionsAccountException(){
        DataTransactionsAccount dataTransactionsAccount= new DataTransactionsAccount();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(DataTransactionsAccount.class))).thenReturn(ResponseEntity.ok(dataTransactionsAccount));
        List<TransactionsAccount> response = lamptechComponent.getTransactionsAccount("customerId", "organizationId", "accountId");

        assertNotNull("not null", lamptechComponent);
        assertNotNull("not null", lamptechComponent);
    }

    @Test
    public void  testGetProfileAnalyse(){

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("accountId");
        accountBalance.setAvailableAmount(5000000.0);



        List<TransactionsAccount> data= new ArrayList<>();
        TransactionsAccount transactionsAccount= new TransactionsAccount();
        transactionsAccount.setAccountId("accountId");
        transactionsAccount.setTransactionName("Luz");
        transactionsAccount.setAmount(5000000.0);
        data.add(transactionsAccount);

        ProfileAnalyseDTO profileAnalyse = lamptechComponent.getProfileAnalyse(accountBalance, data);

        assertEquals("should be equals", profileAnalyse.getAccountId(), "accountId");
        assertTrue("should be true", profileAnalyse.getLimitApproved());
        assertNotNull("not null", lamptechComponent);
    }

    @Test(expected = GlobalException.class)
    public void  testGetProfileAnalyseException(){

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("accountId");
        accountBalance.setAvailableAmount(-1.0);


        List<TransactionsAccount> data= new ArrayList<>();
        TransactionsAccount transactionsAccount= new TransactionsAccount();
        transactionsAccount.setAccountId("accountId");
        transactionsAccount.setTransactionName("Luz");
        transactionsAccount.setAmount(5000000.0);
        data.add(transactionsAccount);


        lamptechComponent.getProfileAnalyse(accountBalance, data);

        assertNotNull("not null", lamptechComponent);
    }
}