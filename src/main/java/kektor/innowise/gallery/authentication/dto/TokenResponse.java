package kektor.innowise.gallery.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "TokenResponse",
        description = "Response model containing JWT access token and refresh token"
)
public record TokenResponse(

        @Schema(
                description = "JWT access token for API authentication",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String accessToken,

        @Schema(
                description = "Refresh token for obtaining new access tokens",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String refreshToken
) {
}
