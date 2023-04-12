package bjr.payamigo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;
    @Column(name = "amount", nullable = false)
    @DecimalMin(value = "0.0")
    private double amount;
    @Column(name = "commission_percent", nullable = false)
    private double commissionPercent = 0.02;
    @Column(name = "commission_amount", nullable = false)
    private double commissionAmount;
    @Column(name = "currency", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "wallet_id")
    private Wallet sourceWallet;
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "wallet_id")
    private Wallet destinationWallet;

    public Transaction(){}

    public Transaction(long transactionId, double amount, double commissionPercent, double commissionAmount,
                       Currency currency, Date creationDate, Wallet sourceWallet, Wallet destinationWallet) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.commissionPercent = commissionPercent;
        this.commissionAmount = commissionAmount;
        this.currency = currency;
        this.creationDate = creationDate;
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
    }
}
