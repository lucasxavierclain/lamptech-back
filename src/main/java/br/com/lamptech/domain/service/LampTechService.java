package br.com.lamptech.domain.service;

import br.com.lamptech.application.dto.ProfileAnalyse;
import br.com.lamptech.domain.OrganizationInfos;
import br.com.lamptech.domain.erros.CodeErros;
import br.com.lamptech.domain.exceptions.GlobalException;
import br.com.lamptech.infrastructure.component.LamptechComponent;
import br.com.lamptech.infrastructure.component.impl.LamptechComponentImpl;
import br.com.lamptech.infrastructure.entity.AccountBalance;
import br.com.lamptech.infrastructure.entity.ListAccounts;
import br.com.lamptech.infrastructure.entity.TransactionsAccount;
import br.com.lamptech.infrastructure.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class LampTechService {


    @Autowired
    LamptechComponent lamptechComponent;

    public ListAccounts getAccountList(String customerId, String organizationid) {

        ListAccounts account = null;

        try {

            account = lamptechComponent.getAccount(customerId, getOrganizationid(organizationid));

        } catch (GlobalException e) {

            throw new GlobalException(CodeErros.ERROR_003);

        }
        return account;
    }

    public AccountBalance getBalanceAccount(String customerId, String organizationid, String accountId) {

        AccountBalance balanceAccount = null;

        try {

            balanceAccount = lamptechComponent.getBalanceAccount(customerId, getOrganizationid(organizationid), accountId);

        } catch (GlobalException e) {

            throw new GlobalException(CodeErros.ERROR_004);

        }

        return balanceAccount;

    }

    public List<TransactionsAccount> getTransactionsAccount(String customerId, String organizationid, String accountId) {

        List<TransactionsAccount> transactionsAccount = null;

        try {

            transactionsAccount = lamptechComponent.getTransactionsAccount(customerId, getOrganizationid(organizationid), accountId);

        } catch (GlobalException e) {

            throw new GlobalException(CodeErros.ERROR_005);

        }

        return transactionsAccount;
    }

    public ProfileAnalyse getUserProfile(String customerId, String organizationid) {

        ProfileAnalyse profileAnalyse = null;
        try {

            ListAccounts account = getAccountList(customerId, organizationid);

            AccountBalance balanceAccount = getBalanceAccount(customerId, organizationid, account.getAccountId());

            List<TransactionsAccount> transactionsAccount = getTransactionsAccount(customerId, organizationid, account.getAccountId());

            profileAnalyse = lamptechComponent.getProfileAnalyse(balanceAccount, transactionsAccount);

            profileAnalyse.setOrganizationName(getOrganizationFantasyName(profileAnalyse.getOrganizationId()));


        } catch (GlobalException e) {

            if (Utils.isNullOrEmpty(e.getCodeError())) {

                throw new GlobalException(CodeErros.ERROR_001);

            }
            throw e;
        }

        return profileAnalyse;

    }

    private String getOrganizationid(String organizationName) {

        AtomicReference<String> organizationId = new AtomicReference<>("");

        Arrays.stream(OrganizationInfos.values()).forEach(name -> {
            if (name.getName().equals(organizationName.toUpperCase())) {
                organizationId.set(name.getOrganizationId());
            }
        });

        if (Utils.isNullOrEmpty(organizationId.get())) {

            throw new GlobalException(CodeErros.ERROR_008);

        }

        return organizationId.get();
    }

    private String getOrganizationFantasyName(String organizationFantasyName) {

        AtomicReference<String> organizationId = new AtomicReference<>("");

        Arrays.stream(OrganizationInfos.values()).forEach(name -> {
            if (name.getOrganizationId().equals(organizationFantasyName)) {
                organizationId.set(name.getName());
            }
        });

        if (Utils.isNullOrEmpty(organizationId.get())) {

            throw new GlobalException(CodeErros.ERROR_008);

        }

        return organizationId.get();

    }


}
