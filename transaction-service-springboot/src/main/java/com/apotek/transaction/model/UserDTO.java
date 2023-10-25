package com.apotek.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String password;
    private Integer nik;
    private String name;
    private String username;
    private String foto;
    private Boolean gender;
    private Integer telpon;
    private String alamat;
    private Integer role;


}
