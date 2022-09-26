package br.com.lamptech.domain.service;

import br.com.lamptech.application.dto.ProfileAnalyseDTO;
import br.com.lamptech.domain.exceptions.GlobalException;
import br.com.lamptech.infrastructure.component.impl.LamptechComponentImpl;
import br.com.lamptech.infrastructure.entity.AccountBalance;
import br.com.lamptech.infrastructure.entity.ListAccounts;
import br.com.lamptech.infrastructure.entity.TransactionsAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LampTechServiceTest {

    @InjectMocks
    LampTechService service;

    @Mock
    LamptechComponentImpl lamptechComponent;

    @Test
    public void testGetAccountList(){
        ListAccounts listAccounts = new ListAccounts();
        listAccounts.setAccountId("accountId");

        when(lamptechComponent.getAccount(eq("customerId"), anyString())).thenReturn(listAccounts);
        ListAccounts accountList = service.getAccountList("customerId", "BTG");

        assertEquals("should be equals", accountList.getAccountId(), "accountId");
    }

    @Test(expected = GlobalException.class)
    public void testGetAccountListException(){
        when(lamptechComponent.getAccount(eq("customerId"), anyString())).thenThrow(GlobalException.class);
        service.getAccountList("customerId", "BTG");

        assertNotNull("not null", service);
    }

    @Test
    public void testGetBalanceAccount(){
        AccountBalance balance = new AccountBalance();
        balance.setAvailableAmount(1000.00);
        balance.setAccountId("accountId");

        when(lamptechComponent.getBalanceAccount(eq("customerId"), anyString(), anyString())).thenReturn(balance);
        AccountBalance balanceAccount = service.getBalanceAccount("customerId", "BTG", "accountId");

        assertEquals("should be equals", balanceAccount.getAccountId(), "accountId");
        assertEquals("should be equals", balanceAccount.getAvailableAmount(),  Double.valueOf(1000.00) );
    }

    @Test(expected = GlobalException.class)
    public void testGetBalanceAccountException(){


        when(lamptechComponent.getBalanceAccount(eq("customerId"), anyString(), anyString())).thenThrow(GlobalException.class);
        AccountBalance balanceAccount = service.getBalanceAccount("customerId", "BTG", "accountId");

        assertEquals("should be equals", balanceAccount.getAccountId(), "accountId");
        assertEquals("should be equals", balanceAccount.getAvailableAmount(),  Double.valueOf(1000.00) );
    }

    @Test
    public void testGetTransactionsAccount(){
        List<TransactionsAccount> list= new ArrayList<>();
        TransactionsAccount transactionsAccount= new TransactionsAccount();
        transactionsAccount.setAccountId("customerId");
        list.add(transactionsAccount);

        when(lamptechComponent.getTransactionsAccount(eq("customerId"), anyString(), anyString())).thenReturn(list);
        List<TransactionsAccount> listTransactions = service.getTransactionsAccount("customerId", "BTG", "accountId");

        assertFalse("should be false", listTransactions.isEmpty());
        assertEquals("should be equals", listTransactions.get(0).getAccountId(),  "customerId");
    }

    @Test(expected = GlobalException.class)
    public void testGetTransactionsAccountException(){


        when(lamptechComponent.getTransactionsAccount(eq("customerId"), anyString(), anyString())).thenThrow(GlobalException.class);
        List<TransactionsAccount> listTransactions = service.getTransactionsAccount("customerId", "BTG", "accountId");

        assertNotNull("not null", service);
    }

    @Test
    public void testGetUserProfile(){
        List<TransactionsAccount> list= new ArrayList<>();
        TransactionsAccount transactionsAccount= new TransactionsAccount();
        transactionsAccount.setAccountId("customerId");
        list.add(transactionsAccount);

        AccountBalance balance = new AccountBalance();
        balance.setAvailableAmount(1000.00);
        balance.setAccountId("accountId");
        balance.setOrganizationId("f4851ca0-7163-4d71-97a7-df1e999c047f");

        ListAccounts listAccounts = new ListAccounts();
        listAccounts.setAccountId("accountId");

        ProfileAnalyseDTO profileAnalyse= new ProfileAnalyseDTO(balance, 4000.0);

        when(lamptechComponent.getAccount(eq("customerId"), anyString())).thenReturn(listAccounts);
        when(lamptechComponent.getBalanceAccount(eq("customerId"), anyString(), anyString())).thenReturn(balance);
        when(lamptechComponent.getTransactionsAccount(eq("customerId"), anyString(), anyString())).thenReturn(list);
        when(lamptechComponent.getProfileAnalyse(any(), any())).thenReturn(profileAnalyse);

        ProfileAnalyseDTO userProfile = service.getUserProfile("customerId", "BTG");

        assertFalse("should be false", userProfile.getLimitApproved());
        assertEquals("should be equals", userProfile.getAccountId(), "accountId");
    }

    @Test(expected = GlobalException.class)
    public void testGetUserProfileException(){
        List<TransactionsAccount> list= new ArrayList<>();
        TransactionsAccount transactionsAccount= new TransactionsAccount();
        transactionsAccount.setAccountId("customerId");
        list.add(transactionsAccount);

        AccountBalance balance = new AccountBalance();
        balance.setAvailableAmount(1000.00);
        balance.setAccountId("accountId");
        balance.setOrganizationId("f4851ca0-7163-4d71-97a7-df1e999c047f");

        ListAccounts listAccounts = new ListAccounts();
        listAccounts.setAccountId("accountId");


        when(lamptechComponent.getAccount(eq("customerId"), anyString())).thenReturn(listAccounts);
        when(lamptechComponent.getBalanceAccount(eq("customerId"), anyString(), anyString())).thenReturn(balance);
        when(lamptechComponent.getTransactionsAccount(eq("customerId"), anyString(), anyString())).thenReturn(list);
        when(lamptechComponent.getProfileAnalyse(any(), any())).thenThrow(GlobalException.class);

        ProfileAnalyseDTO userProfile = service.getUserProfile("customerId", "BTG");

        assertFalse("should be false", userProfile.getLimitApproved());
        assertEquals("should be equals", userProfile.getAccountId(), "accountId");
    }
}