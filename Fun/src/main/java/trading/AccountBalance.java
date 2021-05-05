package trading;

public class AccountBalance {
  // futures, equities, crypto account
  private String accountType;

  // startingBalance float64
  private double endingBalance;

  public AccountBalance(String accountType, double endingBalance) {
    this.accountType = accountType;
    this.endingBalance = endingBalance;
  }

  public String getAccountType() {
    return this.accountType;
  }

  public double getEndingBalance() {
    return this.endingBalance;
  }

}
