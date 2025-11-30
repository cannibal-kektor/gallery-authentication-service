package kektor.innowise.gallery.authentication.controller;


import jakarta.validation.Valid;
import kektor.innowise.gallery.authentication.dto.LoginRequest;
import kektor.innowise.gallery.authentication.dto.LogoutRequest;
import kektor.innowise.gallery.authentication.dto.RefreshRequest;
import kektor.innowise.gallery.authentication.dto.StorePasswordRequest;
import kektor.innowise.gallery.authentication.dto.TokenResponse;
import kektor.innowise.gallery.authentication.dto.ValidateRequest;
import kektor.innowise.gallery.authentication.service.PasswordService;
import kektor.innowise.gallery.authentication.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    final TokenService tokenService;
    final PasswordService passwordService;

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok()
                .body(tokenService.login(loginRequest));
    }

    @PostMapping(
            path = "/logout",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
        tokenService.logout(logoutRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            path = "/validate",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> validate(@RequestBody @Valid ValidateRequest validateRequest) {
        if (tokenService.validate(validateRequest))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(
            path = "/refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody @Valid RefreshRequest refreshRequest) {
        return ResponseEntity.ok()
                .body(tokenService.refresh(refreshRequest));
    }

    @PostMapping(
            path = "/password",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> storePassword(@RequestBody @Valid StorePasswordRequest passwordRequest) {
        passwordService.storeUserPassword(passwordRequest);
        return ResponseEntity.ok().build();
    }


}
