package bjr.bowser;

import bjr.payamigo.Transaction;
import bjr.payamigo.User;
import bjr.payamigo.Wallet;
import bjr.persistance.Exception.InsufficientFundsException;
import bjr.persistance.Exception.WalletNotFoundException;
import bjr.persistance.Repository.UserRepository;
import bjr.persistance.Repository.WalletRepository;
import bjr.persistance.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {
    private UserRepository userRepository;
    private WalletRepository walletRepository;
    private TransactionService transactionService;

    @Autowired
    public UserController(UserRepository userRepository, WalletRepository walletRepository,
                          TransactionService transactionService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUsers(@RequestParam(name ="id") Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/wallet/attach")
    public ResponseEntity<Wallet> attachNewWallet(@RequestBody Wallet wallet) {
        Optional<User> user = userRepository.findById(wallet.getUser().getUserId());
        if(user.isPresent()) {
            wallet.setUser(user.get());
            walletRepository.save(wallet);
            return new ResponseEntity(wallet, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/transaction/new_transaction")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Transaction sameCurrencyTransaction(@RequestParam(name = "sourceWalletId") Long sourceWalletId,
                                               @RequestParam(name = "destinationWalletId") Long destinationWalletId,
                                               @RequestParam(name = "amount") Double amount)
            throws WalletNotFoundException, InsufficientFundsException {
        return transactionService.registerTransactionSameCurrency(sourceWalletId, destinationWalletId, amount);
    }
}
