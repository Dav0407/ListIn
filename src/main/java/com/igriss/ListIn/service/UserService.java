package com.igriss.ListIn.service;

import com.igriss.ListIn.entity.User;
import com.igriss.ListIn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
    private final UserRepository repository;

    private final List<UserDetails> users;
    @Override
    public void createUser(UserDetails user) {
        repository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
     }

    @Override
    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String email) {
        Optional<User>user=repository.findByEmail(email);
        return user.isPresent();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return users.stream().
                filter(user -> user.getUsername()
                                .equals(email)).findFirst()
                        .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
