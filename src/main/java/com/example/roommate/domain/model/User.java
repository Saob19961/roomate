package com.example.roommate.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String githubadresse;
    @Column
    private Boolean is_admin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGithubadresse() {
        return githubadresse;
    }

    public void setGithubadresse(String githubadresse) {
        this.githubadresse = githubadresse;
    }

    public Boolean isAdmin() {
        return is_admin;
    }

    public void setIsAdmin(Boolean is_admin) {
        this.is_admin = is_admin;
    }
}
