package tw.com.cathaybank.Demo.domain.currency.gateway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/** 幣別 */
@Data
public class Currency {
  /** 代號 */
  private String code;
  /** 符號 */
  private String symbol;
  /** 匯率（字串） */
  private String rate;
  /** 說明 */
  private String description;
  /** 匯率 */
  @JsonProperty("rate_float")
  private BigDecimal rateFloat;
}
