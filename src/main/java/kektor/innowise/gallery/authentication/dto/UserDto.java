package kektor.innowise.gallery.authentication.dto;

public record UserDto(
        Long id,
        String username,
        String email) {
}
