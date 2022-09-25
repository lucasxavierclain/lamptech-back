package br.com.lamptech.infrastructure.utils;

public  class Utils {

    public Utils(){
        throw new IllegalArgumentException("Classe não instanciável");
    }
    public static boolean isNullOrEmpty(String o){
        return o==null || "".equals(o);
    }
}
