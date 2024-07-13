package com.example.progpi.Security.Authentication;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
public class KeycloakConfig {

    static Keycloak keycloak = null;
    final static String serverUrl = "http://localhost:8081";
    public final static String realm = "piatSofWeb";
    final static String clientId = "springEtt";
    final static String clientSecret = "rUIdJZSrccuGxjCM1ebjlEQKgRgievEu";
    final static String userName = "ett";
    final static String password = "12345";


    private KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();

        }
        return keycloak;
    }
}