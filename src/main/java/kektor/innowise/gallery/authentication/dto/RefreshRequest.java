package kektor.innowise.gallery.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "RefreshRequest",
        description = "Request model for refreshing JWT tokens"
)
public record RefreshRequest(

        @Schema(
                description = "Valid refresh token obtained during login",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 16,
                maxLength = 36
        )
        @NotBlank
        @Size(min = 16, max = 36)
        String refreshToken
) {
}