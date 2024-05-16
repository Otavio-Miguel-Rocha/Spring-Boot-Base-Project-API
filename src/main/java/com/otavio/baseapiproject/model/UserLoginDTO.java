package com.otavio.baseapiproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
