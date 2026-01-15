package kektor.innowise.gallery.authentication.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
