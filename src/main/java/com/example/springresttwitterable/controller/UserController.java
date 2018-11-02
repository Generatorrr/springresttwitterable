package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.message.ListMessageDTO;
import com.example.springresttwitterable.entity.dto.user.UserDTO;
import com.example.springresttwitterable.entity.dto.user.UserForAdminDTO;
import com.example.springresttwitterable.entity.dto.user.UserSubscribDTO;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="User API", description="User operations")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper)
    {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "Get all users", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserForAdminDTO>> getAllUsers() {

        return new ResponseEntity<>(userMapper.convertToUserForAdminDTO(userService.findAll()), HttpStatus.OK);
    }

//    @ApiOperation(value = "Get specific user", response = HTMLDocument.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = ""),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 403, message = "Forbidden")
//        }
//    )
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @GetMapping("{user}")
//    public String getUserEditForm(@PathVariable User user, Model model) {
//
//        model.addAttribute("user", user);
//        model.addAttribute("roles", Role.values());
//        return "userEdit";
//    }

//    @ApiOperation(value = "Change user data", response = HTMLDocument.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = ""),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 403, message = "Forbidden")
//        }
//    )
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PostMapping
//    public String saveUser(
//            @RequestParam String username,
//            @RequestParam Map<String, String> form,
//            @RequestParam("userId") User user
//    ) {
//
//        userService.saveUser(username, form, user);
//
//        return "redirect:/user";
//    }

//    @ApiOperation(value = "Get yourself profile", response = HTMLDocument.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = ""),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 403, message = "Forbidden")
//        }
//    )
//    @GetMapping("profile")
//    public String getProfile(Model model, @AuthenticationPrincipal User user) {
//
//        model.addAttribute("username", user.getName());
//        model.addAttribute("email", user.getEmail());
//        return "profile";
//    }

    @ApiOperation(value = "Update your profile", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
    @PostMapping("profile")
    public ResponseEntity updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UserDTO userToUpdate
    ) {
        userService.updateProfile(user, userToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subscribe/{user}")
    public ResponseEntity subscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser

    ) {

        userService.subscribe(currentUser, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unsubscribe/{user}")
    public ResponseEntity unsubscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser

    ) {

        userService.unsubscribe(currentUser, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{type}/{user}/list")
    public ResponseEntity<Set<UserSubscribDTO>> userList(
            @PathVariable User user,
            @PathVariable String type
    ) {

        Set<UserSubscribDTO> users = "subscriptions".equals(type)
                ? userMapper.convertToUserSubscribDTO(user.getSubscribtions())
                : userMapper.convertToUserSubscribDTO(user.getSubscribers());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
