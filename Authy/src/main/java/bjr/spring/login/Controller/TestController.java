package bjr.spring.login.Controller;

import bjr.payamigo.DTO.TransactionRequest;
import bjr.payamigo.Entity.User;
import bjr.payamigo.Entity.Wallet;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "All users can see this!";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderators can see this prompt.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin only space!";
  }

  @PostMapping("/wallets/attach")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> attachWallet(@RequestBody Wallet wallet) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User payAmigoUser = new User();
    payAmigoUser.setUsername(userDetails.getUsername());
    Wallet savedWalled = new Wallet(wallet.getWalletId(), wallet.getBalance(), wallet.getCurrency(), payAmigoUser);
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8090/wallets/attach";
    HttpEntity<Wallet> request = new HttpEntity<>(savedWalled);
    Wallet payAmigoWallet = restTemplate.postForObject(url, request, Wallet.class);

    return ResponseEntity.ok(payAmigoWallet);
  }

  @PostMapping("/transactions/new-sc-transaction")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> newSameCurrencyTransaction
          (@RequestBody TransactionRequest transactionRequest) {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8090/transactions/new-transaction";
    HttpEntity<TransactionRequest> request = new HttpEntity<>(transactionRequest);
    TransactionRequest payAmigoScTransaction = restTemplate.postForObject(url, request, TransactionRequest.class);

    return ResponseEntity.ok(payAmigoScTransaction);
  }
}

