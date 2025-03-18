package io.sicredi.tranquilidade.financeira.utils.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ManagerPropertyLoader {

    private static final String DEFAULT_LOGIN = "standard_user";
    private static final String DEFAULT_SENHA = "secret_sauce";
    private static final List<Credencias> usersList;



    static {
        String listCredenciasPath = "src/test/resources/json/user_list.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            usersList = objectMapper.readValue(new File(listCredenciasPath), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());

        }
    }


    public static Credencias getLogin(String user) {
        return usersList.stream()
                .filter(i -> Objects.equals(i.getUser(),user))
                .findFirst().get();

    }

    @Getter
    public static class Credencias {
        private String user;
        private String login;
        private String senha;

    }

}

