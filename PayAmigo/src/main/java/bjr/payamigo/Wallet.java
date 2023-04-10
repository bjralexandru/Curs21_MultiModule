package bjr.payamigo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.DecimalMin;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private long walletId;
    @Column(name = "balance")
    @DecimalMin(value = "0.0")
    private double balance;
    @Column(name = "currency")
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @OneToMany(mappedBy = "sourceWallet")
    @JsonIgnore
    private List<Transaction> sourceWallet = new ArrayList<>();
    @OneToMany(mappedBy = "destinationWallet")
    @JsonIgnore
    private List<Transaction> destinationWallet = new ArrayList<>();

    protected Wallet(){};

    public Wallet(long walletId, double balance, Currency currency, User user) {
        this.walletId = walletId;
        this.balance = balance;
        this.currency = currency;
        this.user = user;
    }
}
