package com.supreeth.terralogicsecurity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private static final long serialVersionUID = 2874357660561551940L;
    private String userName;
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String middleName;
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPassword;
    private String email;
}
