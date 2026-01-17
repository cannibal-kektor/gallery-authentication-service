package kektor.innowise.gallery.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "LoginRequest",
        description = "Request model for user authentication"
)
public record LoginRequest(

        @Schema(
                description = "Username for authentication",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "alex_white",
                minLength = 3,
                maxLength = 30
        )
        @NotBlank
        @Size(min = 3, max = 30)
        String username,

        @Schema(
                description = "User password",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "testPassword123",
                minLength = 8,
                maxLength = 50,
                format = "password"
        )
        @NotBlank
        @Size(min = 8, max = 50)
        String password
) {
}

