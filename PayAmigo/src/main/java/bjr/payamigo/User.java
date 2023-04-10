package bjr.payamigo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "username", nullable = false, length = 15)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "password", nullable = false, length = 60)
    private String password;
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Wallet> walletList;

    protected User(){}

    public User(long userId, String username, String firstName, String lastName, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }
}
