package org.doc.com.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.doc.com.dto.UserCreateDto;
import org.doc.com.dto.UserDto;
import org.doc.com.entity.User;
import org.doc.com.mapper.UserMapper;
import org.doc.com.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user = mapper.userCreateDtoToEntity(userCreateDto);
        User createdUser = service.create(user);
        return ResponseEntity.ok(mapper.toDto(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,
                                          @Valid @RequestBody UserCreateDto userCreateDto) {
        User user = mapper.userCreateDtoToEntity(userCreateDto);
        User updatedUser = service.update(id, user);
        return ResponseEntity.ok(mapper.toDto(updatedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = service.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}