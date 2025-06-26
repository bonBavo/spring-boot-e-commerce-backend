package com.bonbravo.store.controllers;

import com.bonbravo.store.dtos.RegisterUserRequest;
import com.bonbravo.store.dtos.UserDto;
import com.bonbravo.store.mappers.UserMapper;
import com.bonbravo.store.models.User;
import com.bonbravo.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {


    private final UserRepository userRepository;
    private final UserMapper userMapper;



    @GetMapping
    public Iterable<UserDto> getUsers(
            @RequestParam(required = false, defaultValue = "") String sort
    ) {
        if (!Set.of("email", "name").contains(sort))
            sort =  "name";
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            UriComponentsBuilder uriBuilder,
            @RequestBody RegisterUserRequest request) {
        var user  = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

}













//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
//        var user = userRepository.findById(id).orElse(null);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
//        return ResponseEntity.ok(userDto);
//    }


//    @GetMapping
//    public Iterable<UserDto> getUsers() {
//        return userRepository.findAll()
//                .stream()
//                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
//                .toList();
//    }