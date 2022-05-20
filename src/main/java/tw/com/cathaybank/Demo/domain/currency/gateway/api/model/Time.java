package tw.com.cathaybank.Demo.domain.currency.gateway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** 更新時間 */
@Data
public class Time {
  /** 更新時間 UTC */
  @JsonProperty("updated")
  private String updated;
  /** 更新時間 ISO */
  @JsonProperty("updatedISO")
  // RFC 3339
  private String updatedISO;
  /** 更新時間 EUR */
  @JsonProperty("updateduk")
  private String updatedUk;
}
