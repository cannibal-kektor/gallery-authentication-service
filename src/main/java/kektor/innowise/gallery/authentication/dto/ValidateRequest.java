package kektor.innowise.gallery.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ValidateRequest(
        @NotBlank
        @Size(min = 30)
        String accessToken
) {
}
