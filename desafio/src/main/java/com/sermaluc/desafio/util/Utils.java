package com.sermaluc.desafio.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * Valida formato de la password
     * @param  password
     * @return boolean
     */
    public static boolean isValidPassword(String password) {
        String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d.*\\d)[A-Za-z\\d]{8,12}$";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Convierte el formato del objeto formato json
     * @param  object
     * @return boolean
     */
    public static String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(object);
    }
}
