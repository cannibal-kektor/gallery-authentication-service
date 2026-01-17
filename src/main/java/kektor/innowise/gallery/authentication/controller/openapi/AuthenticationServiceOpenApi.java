package kektor.innowise.gallery.authentication.controller.openapi;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.FailedApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kektor.innowise.gallery.authentication.dto.LoginRequest;
import kektor.innowise.gallery.authentication.dto.LogoutRequest;
import kektor.innowise.gallery.authentication.dto.RefreshRequest;
import kektor.innowise.gallery.authentication.dto.StorePasswordRequest;
import kektor.innowise.gallery.authentication.dto.TokenResponse;
import kektor.innowise.gallery.authentication.dto.ValidateRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static kektor.innowise.gallery.authentication.config.OpenApiConfig.INTERNAL_SERVICE_AUTH;
import static kektor.innowise.gallery.authentication.config.OpenApiConfig.JWT_BEARER_TOKEN;
import static kektor.innowise.gallery.authentication.config.OpenApiConfig.PROBLEM_DETAIL_RESPONSE;

@Tag(
        name = "Authentication API",
        description = "API for user authentication, token management, and password storage in the Image Gallery system."
)
@FailedApiResponse(ref = PROBLEM_DETAIL_RESPONSE)
public interface AuthenticationServiceOpenApi {

    @Operation(
            summary = "User login",
            description = "Authenticates user credentials and returns JWT access token and refresh token",
            requestBody = @RequestBody(
                    description = "User login credentials",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            )
    )
    @TokenApiResponse
    ResponseEntity<TokenResponse> login(@Valid LoginRequest loginRequest);

    @Operation(
            summary = "Refresh access token",
            description = "Generates new access and refresh tokens using a valid refresh token",
            requestBody = @RequestBody(
                    description = "Refresh token request",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RefreshRequest.class)
                    )
            )
    )
    @TokenApiResponse
    ResponseEntity<TokenResponse> refreshToken(@Valid RefreshRequest refreshRequest);


    @Operation(
            summary = "Validate access token",
            description = "Validates the provided JWT access token",
            requestBody = @RequestBody(
                    description = "Token validation request",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidateRequest.class)
                    )
            ),
            security = @SecurityRequirement(name = INTERNAL_SERVICE_AUTH)
    )
    @ApiResponse(
            responseCode = "200",
            description = "The provided token is valid"
    )
    ResponseEntity<Void> validate(@Valid ValidateRequest validateRequest);


    @Operation(
            summary = "User logout",
            description = """
                    Invalidates the provided access token and refresh token.
                    The access token is blacklisted until its natural expiration.
                    """,
            requestBody = @RequestBody(
                    description = "Logout request with tokens to invalidate",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LogoutRequest.class)
                    )
            ),
            security = @SecurityRequirement(name = JWT_BEARER_TOKEN)
    )
    @ApiResponse(
            responseCode = "200",
            description = "Logout successful, tokens invalidated"
    )
    ResponseEntity<Void> logout(@Valid LogoutRequest logoutRequest);

    @Operation(
            summary = "Store user password",
            description = "Stores hashed user password in the authentication database",
            requestBody = @RequestBody(
                    description = "Password storage request",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StorePasswordRequest.class)
                    )
            ),
            security = @SecurityRequirement(name = INTERNAL_SERVICE_AUTH)
    )
    @ApiResponse(
            responseCode = "200",
            description = "Password stored successfully"
    )
    ResponseEntity<Void> storePassword(@Valid StorePasswordRequest passwordRequest);
}
