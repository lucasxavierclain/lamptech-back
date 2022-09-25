package br.com.lamptech.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Stream;


public enum OrganizationInfos {

    NUBANK("NUBANK", "3fda1ef5-4b72-4557-bcbf-296780ba2c1f","Instituição Financeira 01"),
    SANTANDER("SANTANDER", "459b64e6-be78-4777-8b0b-f3b14e61ef31", "Instituição Financeira 02"),
    ITAU("ITAU", "69665991-da55-4aac-a1f2-32d23daba8fe", "Instituição Financeira 03"),
    NEXT("NEXT", "a81729b5-ec71-4552-b4b1-26931c130154", "Instituição Financeira 04"),
    CAIXA("CAIXA", "63d929c5-2a09-479e-806c-8cd073a029ab", "Instituição Financeira 05"),
    BRADESCO("BRADESCO", "e7fc1280-4dd3-4977-bc24-c08ffea8baf5", "Instituição Financeira 06"),
    BTG("BTG", "f4851ca0-7163-4d71-97a7-df1e999c047f", "Instituição Financeira 07"),
    SAFRA("SAFRA", "3506654e-b794-4818-b2b1-12f2efb03d39", "Instituição Financeira 08"),
    BANCO_DO_BRASIL("BANCO DO BRASIL", "d3d2b0d5-903a-4f9f-b793-c139490d5ca7", "Instituição Financeira 09"),
    INTER("INTER", "ac2ab4c1-dc48-4958-b964-e46938817aaa", "Instituição Financeira 10");


    private String name;
    private String organizationId;
    private String fantasyName;

    OrganizationInfos(String name, String organizationId, String fantasyName) {
        this.fantasyName=fantasyName;
        this.name = name;
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

}