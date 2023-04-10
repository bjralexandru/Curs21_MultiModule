package bjr.persistance.Repository;

import bjr.payamigo.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("update Wallet w set w.balance = ?1 where w.walletId = ?2")
    void updateWallet(Double balance, Long walletId);
}
