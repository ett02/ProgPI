package com.example.progpi.services;

import com.example.progpi.Security.Authentication.KeycloakConfig;
import com.example.progpi.Utilities.Exception.ExistingUserException;
import com.example.progpi.Utilities.Exception.NotExistingUserException;
import com.example.progpi.entities.Cart;
import com.example.progpi.entities.Users;
import com.example.progpi.repositories.CartRepository;
import com.example.progpi.repositories.ProductInCartRepository;
import com.example.progpi.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;

    EntityManager entityManager;


    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Users saveUser(Users u) throws Exception{
        if(usersRepository.existsByEmail(u.getEmail())){
            throw new ExistingUserException();
        }else {
            //settiamo l'utente su keycloak
            addKeyUser(u);
            Cart cart = new Cart();
            u.setCart(cart);
            cart.setUser(u);
            cart.setListProductInCart(new ArrayList<>());
            cartRepository.save(cart);
            usersRepository.save(u);
            return u;
        }
    }

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public Users getUser(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public boolean Esiste(String email){
        return usersRepository.existsByEmail(email);
    }

    /*
    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Users updateUser(Users u, String cF) throws Exception{
        Users user = usersRepository.findByCodFisc(cF);

        user.setEmail(u.getEmail());
        user.setName(u.getName());
        user.setSurname(u.getSurname());
        user.setAddress(u.getAddress());
        user.setTelephon(u.getTelephon());



    }

     */

    @Transactional(readOnly = false)
    public boolean delette(String cF) throws NotExistingUserException {

        if(!usersRepository.existsByCodFisc(cF)){
            throw new NotExistingUserException();
        }
        Users u = usersRepository.findByCodFisc(cF);

        usersRepository.delete(u);
        return !usersRepository.existsByCodFisc(cF);
    }


    public void addKeyUser(Users utente) throws ExistingUserException {

        Keycloak keycloak= KeycloakConfig.getInstance();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(utente.getUsername());
        user.setFirstName(utente.getName());
        user.setLastName(utente.getSurname());
        user.setEmail(utente.getEmail());
        user.setCredentials(Collections.singletonList(createPasswordCredentials(utente.getPassword())));
        user.setEmailVerified(true);


//       Get realm
        RealmResource realmResource = keycloak.realm(KeycloakConfig.realm);
        UsersResource usersResource = realmResource.users();


        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(utente.getPassword());
        user.setCredentials(Collections.singletonList(credentialRepresentation));

//      Create user (requires manage-users role)
        Response response=usersResource.create(user);
        if (response.getStatus() == 201) {
            System.out.println("User created successfully.");
        } else {
            System.err.println("Failed to create user. HTTP error code: " + response.getStatus());
            System.err.println("Error message: " + response.getStatusInfo().getReasonPhrase());
            if (response.hasEntity())
                System.err.println("Error details: " + response.readEntity(String.class));

            response.close();
            throw new ExistingUserException();
        }



    }


    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


}