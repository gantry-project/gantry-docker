package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.exception.UserNotFoundException;
import org.gantry.apiserver.persistence.UserRepository;
import org.gantry.apiserver.web.dto.ResetPasswordRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("%s is not found.".formatted(username)));
    }

    public User findById(Long userId) {
        return repository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("%d is not found.".formatted(userId)));
    }

    @Transactional
    public User create(User user) {
        if (user.getAuthority() == null) {
            user.setAuthority(Authority.USER);
        }
        user.setEnabled(true);
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public User updateUser(User user) {
        throwIfUserNotExist(user.getId());
        return repository.save(user);
    }

    private void throwIfUserNotExist(long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException("%d is not found.".formatted(userId));
        }
    }

    @Transactional
    public User updateAuthority(User user) {
        User exUser = findById(user.getId());
        exUser.setAuthority(user.getAuthority());
        return repository.save(exUser);
    }

    @Transactional
    public User resetPassword(ResetPasswordRequest passwordRequest) {
        User exUser = findById(passwordRequest.getId());
        String password = passwordEncoder.encode(passwordRequest.getPassword());
        exUser.setPassword(password);
        return repository.save(exUser);
    }

    @Transactional
    public User deleteUser(Long userId) {
        User exUser = findById(userId);
        repository.delete(exUser);
        return exUser;
    }

    @Transactional
    public User disableUser(Long userId) {
        User exUser = findById(userId);
        exUser.setEnabled(false);
        return repository.save(exUser);
    }

    @Transactional
    public User enableUser(Long userId) {
        User exUser = findById(userId);
        exUser.setEnabled(true);
        return repository.save(exUser);
    }
}
