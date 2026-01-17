package kektor.innowise.gallery.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(
        name = "StorePasswordRequest",
        description = """
                Internal request model for storing hashed user passwords.
                **This DTO is used only for inter-service communication**
                between user-service and authentication-service.
                """
)
public record StorePasswordRequest(

        @Schema(
                description = "User id for password storage",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "12345",
                minimum = "1"
        )
        @NotNull
        @Positive
        Long userId,

        @Schema(
                description = "User password to be hashed and stored",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "testPassword123",
                minLength = 8,
                maxLength = 50,
                format = "password"
        )
        @NotBlank
        @Size(min = 8, max = 50)
        String password) {
}
