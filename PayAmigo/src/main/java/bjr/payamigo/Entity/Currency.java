package bjr.payamigo.Entity;

public enum Currency {
    EUR("EUR"),
    LEU("LEU"),
    CHF("CHF");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
