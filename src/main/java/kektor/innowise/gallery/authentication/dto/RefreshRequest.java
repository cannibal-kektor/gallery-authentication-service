package kektor.innowise.gallery.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshRequest(
        @NotBlank
        @Size(min = 16, max = 36)
        String refreshToken
) {
}
