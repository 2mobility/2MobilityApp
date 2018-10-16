package com.mobility.a2mobilityapp.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorSenha {

    //Expressão para validar senha
    private final static String EXPRESSAO_REGULAR_SENHA_FORTE = "^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public boolean isSenha(final String password) {
        Pattern p = Pattern.compile(EXPRESSAO_REGULAR_SENHA_FORTE);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
