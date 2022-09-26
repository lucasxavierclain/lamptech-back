package br.com.lamptech.domain.exceptions;

import br.com.lamptech.domain.enums.CodeErros;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalException extends RuntimeException{

    private String codeError;
    private String message;
    private static final long serialVersionUID=1L;

    public GlobalException(String codeError, String message){
        this.codeError=codeError;
        this.message=message;
    }
    public GlobalException(CodeErros codeErros){
        this.codeError=codeErros.getCode();
        this.message=codeErros.getMessage();
    }

}
