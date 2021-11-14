package com.gsugambit.partydjserver.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsugambit.partydjserver.model.User;
import com.gsugambit.partydjserver.utils.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSuccess {

    private User user;

    private UserJwtTokenState token;

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}

