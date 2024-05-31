package org.doc.com.service;

import org.doc.com.entity.User;

import java.util.List;

public interface UserService {


    User create(User entity);

    User update(Long id, User entity);

    List<User> getAll();

    User findById(Long id);

    Long getCurrentUserId();

    void delete(Long id);
}