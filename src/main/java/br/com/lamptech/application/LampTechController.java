package br.com.lamptech.application;

import br.com.lamptech.domain.service.LampTechService;
import br.com.lamptech.infrastructure.entity.AccountBalance;
import br.com.lamptech.infrastructure.entity.ListAccounts;
import br.com.lamptech.infrastructure.entity.TransactionsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lamptech")
public class LampTechController {

    @Autowired
    private LampTechService service;

    @GetMapping("/account-list")
    public ResponseEntity<ListAccounts> getAccountList(@RequestHeader("customerId") String customerId,
                                                      @RequestHeader("organizationid") String organizationid){

        return ResponseEntity.ok().body(service.getAccountList(customerId, organizationid));
    }

    @GetMapping("/balance-account")
    public ResponseEntity<AccountBalance> balanceAccount(@RequestHeader("customerId") String customerId,
                                                        @RequestHeader("organizationid") String organizationid,
                                                        @RequestHeader("accountId") String  accountId){

        return ResponseEntity.ok().body(service.getBalanceAccount(customerId, organizationid, accountId));
    }

    @GetMapping("/transactions-account")
    public ResponseEntity<List<TransactionsAccount>> transactionsAccount(@RequestHeader("customerId") String customerId,
                                                         @RequestHeader("organizationid") String organizationid,
                                                         @RequestHeader("accountId") String  accountId){

        return ResponseEntity.ok().body(service.getTransactionsAccount(customerId, organizationid, accountId));
    }


    @GetMapping("/transactions-account")
    public ResponseEntity<List<TransactionsAccount>> getUserProfile(@RequestHeader("customerId") String customerId,
                                                                         @RequestHeader("organizationid") String organizationid,
                                                                         @RequestHeader("accountId") String  accountId){

        return ResponseEntity.ok().body(service.getTransactionsAccount(customerId, organizationid, accountId));
    }
}
