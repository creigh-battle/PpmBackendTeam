package com.creighbattle.exceptions;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class InvalidLoginResponse {
    private String username;
    private String password;

}
