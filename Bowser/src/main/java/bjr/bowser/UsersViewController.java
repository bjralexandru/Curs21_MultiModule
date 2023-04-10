package bjr.bowser;

import bjr.payamigo.Transaction;
import bjr.payamigo.User;
import bjr.payamigo.Wallet;
import bjr.persistance.Repository.UserRepository;
import bjr.persistance.Repository.WalletRepository;
import bjr.persistance.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class UsersViewController {

    private UserRepository userRepository;
    private WalletRepository walletRepository;
    private TransactionService transactionService;

    @Autowired
    public UsersViewController(UserRepository userRepository, WalletRepository walletRepository,
                               TransactionService transactionService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/users")
    public String getUsersList(Model model) {
        List<User> users = this.userRepository.findAll();
        model.addAttribute("listUsers", users);
        return "users";
    }

    @GetMapping("/wallets")
    public String getWallets(Model model) {
        List<Wallet> walletList = walletRepository.findAll();
        model.addAttribute("walletList", walletList);
        return "wallets";
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getTransactions(Model model) {
        List<Transaction> transactionList = transactionService.getTransactions();
        model.addAttribute("transactionList", transactionList);
        return "transactions";
    }
}
