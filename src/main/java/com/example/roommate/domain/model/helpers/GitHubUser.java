package com.example.roommate.domain.model.helpers;

import com.example.roommate.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class GitHubUser extends User implements OAuth2User, Serializable {
    public static final String SESSION_TAG = "rome_mate_user";

    private OAuth2User oAuth2User;

    public GitHubUser(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;

        setId(UUID.randomUUID().toString());
        setName(oAuth2User.getAttribute("name"));
        setGithubadresse(oAuth2User.getAttribute("email"));
        setIsAdmin(false);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }
}
