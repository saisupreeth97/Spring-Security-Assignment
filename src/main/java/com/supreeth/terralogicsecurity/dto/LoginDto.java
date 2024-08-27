package com.supreeth.terralogicsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 6123814524296357831L;
    private String userName;
    private String userPassword;
}
