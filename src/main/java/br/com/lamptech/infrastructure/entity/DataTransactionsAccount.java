package br.com.lamptech.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataTransactionsAccount {

    private List<TransactionsAccount> data;
}
