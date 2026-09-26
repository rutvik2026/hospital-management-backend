package com.hospital.system.hospitalmanagementsystem.controller;


import com.hospital.system.hospitalmanagementsystem.dto.UserDto;
import com.hospital.system.hospitalmanagementsystem.dto.UserDtoResponse;
import com.hospital.system.hospitalmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDtoResponse> signUp(@RequestBody UserDto userDto){
        UserDtoResponse response=userService.addUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
