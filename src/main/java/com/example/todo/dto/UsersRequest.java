package com.example.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "비밀번호는 8~15자, 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;
}