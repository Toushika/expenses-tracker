package rnd.dev.authmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import rnd.dev.authmanagement.entity.misc.Role;

import static rnd.dev.authmanagement.constant.CollectionConstants.USERS_COLLECTION;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = USERS_COLLECTION)
public class User {

    @Id
    private String userId;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private Role role;

    private String createdAt;
}
