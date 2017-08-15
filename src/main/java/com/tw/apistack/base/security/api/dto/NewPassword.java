package com.tw.apistack.base.security.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewPassword {
    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    public String newPassword;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    public String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
