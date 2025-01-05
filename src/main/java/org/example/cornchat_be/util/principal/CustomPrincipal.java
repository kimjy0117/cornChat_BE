package org.example.cornchat_be.util.principal;

import lombok.AllArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
public class CustomPrincipal implements Principal {
    private  String name;

    @Override
    public String getName() {
        return name;
    }
}
