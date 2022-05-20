package tw.com.cathaybank.Demo.domain.currency.gateway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/** 虛擬貨幣匯率模組 */
@Data
public class Coindesk {
  /** 更新時間 */
  private Time time;
  /** 免責聲明 */
  private String disclaimer;
  /** 虛擬貨幣名稱 */
  private String chartName;
  /** 呼叫 */
  @JsonProperty("bpi")
  private Map<String,Currency> bpi;
}
