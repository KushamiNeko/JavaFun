package trading;

import java.time.LocalDateTime;

public class TradeRecord {

  private LocalDateTime tradeOpen;
  private LocalDateTime tradeClose;

  private TradeDirection direction;

  private double pl;

  public TradeRecord(LocalDateTime tradeOpen, LocalDateTime tradeClose, TradeDirection direction, double pl) {
    this.tradeOpen = tradeOpen;
    this.tradeClose = tradeClose;
    this.direction = direction;
    this.pl = pl;
  }

  public LocalDateTime getTradeOpen() {
    return this.tradeOpen;
  }

  public LocalDateTime getTradeClose() {
    return this.tradeClose;
  }

  public TradeDirection getTradeDirection() {
    return direction;
  }

  public double getTradePL() {
    return this.pl;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (obj instanceof TradeRecord) {
      TradeRecord t = (TradeRecord) obj;

      if (
        this.tradeOpen.equals(t.tradeOpen) && this.tradeClose.equals(t.tradeClose) && this.direction.equals(t.direction)
            && this.pl == t.pl
      ) {
        return true;
      }
    }

    return false;
  }
}
