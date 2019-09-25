package com.white.stratego.stratego.market.service;

import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.repository.RoleRepository;
import com.white.stratego.stratego.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByAuthentication(Authentication authentication) {
        User user;
        Object principal = authentication.getPrincipal();
        System.err.println(principal);
        if (principal instanceof org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser) {
            Map attributes =  ((DefaultOidcUser) principal).getAttributes();
            System.err.println(((DefaultOidcUser) principal).getAttributes());
            user = userByAttributes(attributes);
            String new_avatar = (String)attributes.get("picture");
            saveAvatar(new_avatar, user);
        }
        else {
            if(principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
                System.err.println(authentication);
                Map attributes = ((DefaultOAuth2User) principal).getAttributes();
                user = userByAttributes(attributes);
                String newAvatar = (String)((Map)((Map)attributes.get("picture")).get("data")).get("url");
                saveAvatar(newAvatar, user);
            }
            else {
                user = userRepository.findByUsername(authentication.getName());
            }
        }
        return user;
    }

    private void saveAvatar(String new_avatar, User user) {
        String avatar = user.getAvatar_url();
        if (avatar == null || !avatar.equals(new_avatar)) {
            user.setAvatar_url(new_avatar);
            userRepository.save(user);
        }
    }

    private User userByAttributes(Map attributes) {
        User user;
        String email = (String)attributes.get("email");
        user = userRepository.findByEmail(email);
        if(user == null) {
            user = new User();
            user.setEmail(email);
            user.setName((String)attributes.get("name"));
            userRepository.save(user);
        }
        if(!user.getIsActive()) {
            user.setIsActive(true);
            userRepository.save(user);
        }
        return user;
    }
}
