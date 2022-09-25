package br.com.lamptech.domain.erros;

public enum CodeErros {

    ERROR_001("001", "Erro inesperado"),
    ERROR_002("002", "Objeto não contém dados"),
    ERROR_003("003", "Erro ao buscar accounts"),
    ERROR_004("004", "Erro ao buscar saldo da conta"),
    ERROR_005("004", "Erro ao buscar transações da conta");


    private String code;
    private String message;

    CodeErros(String code, String message){
        this.code = code;
        this.message = message;

    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
