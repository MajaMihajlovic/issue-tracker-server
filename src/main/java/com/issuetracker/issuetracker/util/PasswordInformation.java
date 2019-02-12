package com.issuetracker.issuetracker.util;

public class PasswordInformation {
    private Integer id;
    private String oldPassword;
    private String newPassword;
    private String repeatedNewPassword;

    public PasswordInformation(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PasswordInformation(Integer id, String oldPassword, String newPassword, String repeatedNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.id=id;
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }
}
