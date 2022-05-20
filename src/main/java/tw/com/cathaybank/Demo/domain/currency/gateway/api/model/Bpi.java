package tw.com.cathaybank.Demo.domain.currency.gateway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** 法定貨幣 */
@Data
public class Bpi {
  /** 美元 */
  @JsonProperty("USD")
  private Currency usd;
  /** 英鎊 */
  @JsonProperty("GBP")
  private Currency gbp;
  /** 歐元 */
  @JsonProperty("EUR")
  private Currency eur;


}
