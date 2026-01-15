package kektor.innowise.gallery.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "ValidateRequest",
        description = "Request model for JWT token validation"
)
public record ValidateRequest(

        @Schema(
                description = "JWT access token to validate",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 30
        )
        @NotBlank
        @Size(min = 30)
        String accessToken
) {
}
