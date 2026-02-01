package com.curso.react.model.request;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDetailRequestModel {
    @NotEmpty(message = "El nombre es oblifatorio")
    private String firstName;
    @NotEmpty(message = "El apellido es oblifatorio")
    private String lastName;
    @NotEmpty(message = "El correo electronico es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$",
     message = "El correo electronico es invalido")
    private String email;
    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String password;

    public UserDetailRequestModel() {
    }

    public UserDetailRequestModel(String firstName, String lastName, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetailRequestModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", password=" + password + "]";
    }

}
