package org.doc.com.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.doc.com.entity.User;
import org.doc.com.exceptions.UserNotFoundException;
import org.doc.com.repository.UserJpaRepository;
import org.doc.com.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User create(User entity) {
        String hashedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(hashedPassword);
        return repository.save(entity);
    }

    @Transactional
    @Override
    public User update(Long id, User entity) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id: "
                        + id + " not found"));
        existingUser.setDepartment(entity.getDepartment());
        existingUser.setPosition(entity.getPosition());
        return repository.save(existingUser);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no users with id " + id));
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId();
        }
        throw new UserNotFoundException("User not found");
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.findById(id);
        repository.deleteById(id);
    }
}