package com.songify.infrastructure.security;

import com.songify.domain.usercrud.User;
import com.songify.domain.usercrud.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
class UserDetailsServiceImpl implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public static final String DEFAULT_USER_ROLE = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            log.warn(user.getUsername() + " already exists");
            throw new RuntimeException("Username already exists");
        }
        User createdUser = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                true,
                List.of(DEFAULT_USER_ROLE));
        User save = userRepository.save(createdUser);
        log.info("User saved: " + save);
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deleteUser(String username) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new RuntimeException("Not implemented");
    }

    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }

}
