package bjr.payamigo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionRequest {
    @NotBlank
    private Long sourceWalletId;

    @NotBlank
    private Long destinationWalletId;

    @NotBlank
    private Double amount;
}
