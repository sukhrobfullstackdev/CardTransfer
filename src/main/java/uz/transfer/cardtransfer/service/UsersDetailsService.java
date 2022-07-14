package uz.transfer.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersDetailsService implements UserDetailsService {
    final PasswordEncoder passwordEncoder;

    public UsersDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = new ArrayList<>(
                Arrays.asList(
                        new User("sukhrobdev@gmail.com", passwordEncoder.encode("13691369"), new ArrayList<>()),
                        new User("shirin@gmail.com", passwordEncoder.encode("13691368"), new ArrayList<>())
                )
        );
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("The user is not found!");
    }
}
