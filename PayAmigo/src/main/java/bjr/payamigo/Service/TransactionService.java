package bjr.payamigo.Service;

import bjr.payamigo.Entity.Transaction;
import bjr.payamigo.Entity.Wallet;
import bjr.payamigo.Exception.InsufficientFundsException;
import bjr.payamigo.Exception.WalletNotFoundException;
import bjr.payamigo.Repository.TransactionRepository;
import bjr.payamigo.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletRepository walletRepository;

    public List<Transaction> getTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList;
    }

    public Transaction registerTransactionSameCurrency(Long sourceWalletId, Long destinationWalletId, Double amount)
            throws InsufficientFundsException, WalletNotFoundException {
        Optional<Wallet> sourceWallet = walletRepository.findById(sourceWalletId);
        Optional<Wallet> destinationWallet = walletRepository.findById(destinationWalletId);

        if(sourceWallet.isEmpty()) {
            throw new WalletNotFoundException("Source wallet does not exist or bad input!");
        } else if(destinationWallet.isEmpty()) {
            throw new WalletNotFoundException("Destination wallet does not exist or bad input!");
        }

        Wallet effectiveSourceWallet = sourceWallet.get();
        Wallet effectiveDestinationWallet = destinationWallet.get();

        // TODO: Maybe it can be dealt with better
        // But for new we want a flat fee for all transactions
        if(effectiveSourceWallet.getCurrency().equals(effectiveDestinationWallet.getCurrency())) {
            // Il creez aici pt ca am nevoie de comision
            Transaction sameCurrencyTransaction = new Transaction();
            Double commissionPercent = sameCurrencyTransaction.getCommissionPercent();
            // Urasc felul in care functioneaza bancile in momentul de fata. Daca vreau sa trimit 2 lei altcuiva,
            // sa-mi ia mie 2.02 lei decat sa ajunga 1.98 pe partea cealalta.
            if(effectiveSourceWallet.getBalance() >= amount + commissionPercent*amount) {
                Double sourceWalletBalance = effectiveSourceWallet.getBalance() -
                        (amount + commissionPercent*amount);
                walletRepository.updateWallet(sourceWalletBalance, effectiveSourceWallet.getWalletId());
                Double destinationWalletBalance = effectiveDestinationWallet.getBalance() + amount;
                walletRepository.updateWallet(destinationWalletBalance, effectiveDestinationWallet.getWalletId());

                // Adunam totul si intocmim detaliile tranzactiei
                sameCurrencyTransaction.setSourceWallet(effectiveSourceWallet);
                sameCurrencyTransaction.setDestinationWallet(effectiveDestinationWallet);
                sameCurrencyTransaction.setAmount(amount);
                sameCurrencyTransaction.setCommissionAmount(amount* commissionPercent);
                sameCurrencyTransaction.setCurrency(effectiveSourceWallet.getCurrency());
                sameCurrencyTransaction.setCreationDate(new Date());
                return transactionRepository.save(sameCurrencyTransaction);
            }
        }
        throw new InsufficientFundsException();
    }

}
