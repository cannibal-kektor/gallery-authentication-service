package kektor.innowise.gallery.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record StorePasswordRequest(
        @NotNull
        @Positive
        Long userId,
        @NotBlank
        @Size(min = 8, max = 50)
        String password) {
}
