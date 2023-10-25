package com.SistemInformasiApotek.UserService.User;

import lombok.Data;

@Data
public class UserDTO {
    private Integer nik;
    private String username;
    private String password;
    private Role role;
}
