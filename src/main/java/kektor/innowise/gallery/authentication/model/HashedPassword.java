package kektor.innowise.gallery.authentication.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hashed_passwords", schema = "authentication")
public class HashedPassword implements Persistable<Long> {

    @Id
    Long userId;
    String password;
    @Transient
    boolean isNew;

    @Override
    public Long getId() {
        return userId;
    }

}
