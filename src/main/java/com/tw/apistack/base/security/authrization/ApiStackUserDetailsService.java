/* Starbucks Licensed  */

package com.tw.apistack.base.security.authrization;

import com.tw.apistack.base.security.exception.BadPasswordException;
import com.tw.apistack.base.security.api.dto.NewPassword;
import com.tw.apistack.base.security.core.model.User;
import com.tw.apistack.base.security.core.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class ApiStackUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApiStackUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userFromDatabase = userRepository.findByUsername(username);

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        Collection<GrantedAuthority> grantedAuthorities = userFromDatabase.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(),
                userFromDatabase.getPassword(), grantedAuthorities);
    }

    public void changePassword(String username, NewPassword password) {
        String oldPassword = password.getOldPassword();
        User user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadPasswordException(oldPassword);
        }
        userRepository.changePassword(username, this.passwordEncoder.encode(password.newPassword));
    }
}
