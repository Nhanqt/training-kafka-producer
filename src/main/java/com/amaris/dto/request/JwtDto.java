package com.amaris.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto extends BaseDto{
    private int accountId;
    private String email;
    private List<String> roleName;
    private String password;

    @Override
    public String toString() {
        return "JwtDto {" +
                "accountId: " + accountId+
                "email: '" + email+ '\n'+
                "roleName: " + roleName+'\n'+
                "password: '" + password +'\n'+
                '}';
    }
}
