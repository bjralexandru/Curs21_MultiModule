package bjr.payamigo;

public enum Currency {
    EUR("EUR"),
    LEU("LEU"),
    CHF("CHF");

    private String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
