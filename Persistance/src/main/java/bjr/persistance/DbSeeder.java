package bjr.persistance;

import bjr.payamigo.*;
import bjr.persistance.Repository.TransactionRepository;
import bjr.persistance.Repository.UserRepository;
import bjr.persistance.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    private TransactionRepository transactionRepository;

    public DbSeeder(UserRepository userRepository, WalletRepository walletRepository,
                    TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create 2 users:


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "123456";
        String newPassword = passwordEncoder.encode(password);
        User user1 = new User(1, "firstusr", "First", "User", newPassword, Role.ADMIN);
        User user2 = new User(2, "secondusr", "Second", "User", newPassword, Role.USER);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        this.userRepository.saveAll(users);

        // Attach 1 wallet to each user
        Wallet wallet1 = new Wallet(1, 2000, Currency.EUR, user1);
        Wallet wallet2 = new Wallet(2, 1500, Currency.EUR, user2);

        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet1);
        walletList.add(wallet2);

        this.walletRepository.saveAll(walletList);

        // Make 2 transactions
        Transaction transaction1 = new Transaction(1, 50, 0.02,
                0.02*50, Currency.EUR, Date.valueOf("2023-10-02"), wallet1, wallet2);
        Transaction transaction2 = new Transaction(2, 25, 0.02,
                0.02*25, Currency.EUR, Date.valueOf("2023-10-03"), wallet2, wallet1);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        this.transactionRepository.saveAll(transactions);
    }
}
