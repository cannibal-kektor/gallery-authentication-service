package kektor.innowise.gallery.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "LogoutRequest",
        description = "Request model for user logout and token invalidation"
)
public record LogoutRequest(

        @Schema(
                description = "JWT access token to blacklist",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 30
        )
        @NotBlank
        @Size(min = 30)
        String accessToken,

        @Schema(
                description = "Refresh token to invalidate",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 16,
                maxLength = 36
        )
        @NotBlank
        @Size(min = 16, max = 36)
        String refreshToken
) {
}
