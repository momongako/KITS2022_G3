package com.example.projectemarketg3.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassRequest {
    private String oldPass;
    private String newPass;
    private String newPass2;
}
