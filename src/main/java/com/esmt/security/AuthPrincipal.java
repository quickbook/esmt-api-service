package com.esmt.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthPrincipal {

    private final String username;
    private final String ip;
}
