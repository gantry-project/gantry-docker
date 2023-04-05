package org.gantry.apiserver.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.exception.MismatchedPasswordException;
import org.gantry.apiserver.service.UserService;
import org.gantry.apiserver.web.dto.JoinRequest;
import org.gantry.apiserver.web.dto.ResetPasswordRequest;
import org.gantry.apiserver.web.dto.UserResponse;
import org.gantry.apiserver.web.dto.UserUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService service;

    @Operation(summary = "Join a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Join a user"),
            @ApiResponse(responseCode = "404", description = "Not Found the container"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @PostMapping()
    public UserResponse join(@RequestBody JoinRequest joinRequest) {
        if (!joinRequest.getPassword().equals(joinRequest.getConfirmPassword())) {
            throw new MismatchedPasswordException();
        }
        User newUser = joinRequest.toUser();
        User user = service.create(newUser);
        return UserResponse.from(user);
    }

    @Operation(summary = "Get user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping()
    public List<UserResponse> list() {
        return service.findAll().stream().map(UserResponse::from).toList();
    }

    @Operation(summary = "List users information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        User user = service.findById(userId);
        return UserResponse.from(user);
    }

    @Operation(summary = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update user information"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest updateUser) {
        throwIfMismatchUserId(userId, updateUser.getId());
        User user = service.updateUser(updateUser.toUser());
        return UserResponse.from(user);
    }

    @Operation(summary = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update user information"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{userId}/authority")
    public UserResponse updateAuthority(@PathVariable Long userId, @RequestBody UserUpdateRequest updateUser) {
        throwIfMismatchUserId(userId, updateUser.getId());
        User user = service.updateAuthority(updateUser.toUser());
        return UserResponse.from(user);
    }

    @Operation(summary = "Reset user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reset user password"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{userId}/password")
    public UserResponse resetPassword(@PathVariable Long userId, @RequestBody ResetPasswordRequest passwordRequest) {
        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
            throw new MismatchedPasswordException();
        }

        throwIfMismatchUserId(userId, passwordRequest.getId());
        User user = service.resetPassword(passwordRequest);
        return UserResponse.from(user);
    }

    @Operation(summary = "Disable a user state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disable a user state"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{userId}/disabled")
    public UserResponse disableUser(@PathVariable Long userId) {
        User user = service.disableUser(userId);
        return UserResponse.from(user);
    }

    @Operation(summary = "Enable a user state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enable a user state"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{userId}/enabled")
    public UserResponse enableUser(@PathVariable Long userId) {
        User user = service.enableUser(userId);
        return UserResponse.from(user);
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a user"),
            @ApiResponse(responseCode = "404", description = "Not Found a user"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable Long userId) {
        User user = service.deleteUser(userId);
        return UserResponse.from(user);
    }

    private static void throwIfMismatchUserId(Long urlId, Long bodyId)  {
        if (urlId != bodyId) {
            throw new IllegalArgumentException(
                    "Mismatch request information: %d in URL, %d in Body".formatted(urlId, bodyId));
        }
    }

}
