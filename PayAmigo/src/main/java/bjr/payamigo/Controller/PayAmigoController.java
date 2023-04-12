package bjr.payamigo.Controller;

import bjr.payamigo.DTO.TransactionRequest;
import bjr.payamigo.Entity.Transaction;
import bjr.payamigo.Entity.User;
import bjr.payamigo.Entity.Wallet;
import bjr.payamigo.Exception.WalletNotFoundException;
import bjr.payamigo.Repository.UserRepository;
import bjr.payamigo.Repository.WalletRepository;
import bjr.payamigo.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayAmigoController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/wallets/attach")
    public ResponseEntity<Wallet> attachWallet(@RequestBody Wallet wallet) {
        Wallet savedWallet = walletRepository.save(wallet);
        return ResponseEntity.ok(savedWallet);
    }

    @PostMapping("/transactions/new-transaction")
    public ResponseEntity<Transaction> newSameCurrencyTransaction
            (@RequestBody TransactionRequest transactionRequest) throws WalletNotFoundException {
        Transaction savedTransaction = transactionService.registerTransactionSameCurrency
                                                                (transactionRequest.getSourceWalletId(),
                                                                 transactionRequest.getDestinationWalletId(),
                                                                 transactionRequest.getAmount());
        return ResponseEntity.ok(savedTransaction);
    }
}
