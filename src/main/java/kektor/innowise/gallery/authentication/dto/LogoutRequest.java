package kektor.innowise.gallery.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogoutRequest(
        @NotBlank
        @Size(min = 30)
        String accessToken,
        @NotBlank
        @Size(min = 16, max = 36)
        String refreshToken
) {
}
