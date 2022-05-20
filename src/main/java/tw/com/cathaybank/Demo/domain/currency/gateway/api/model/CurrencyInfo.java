package tw.com.cathaybank.Demo.domain.currency.gateway.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 幣別資訊 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyInfo {
  /** 幣別 */
  private String code;
  /** 幣別（中文） */
  private String name;
  /** 匯率 */
  private BigDecimal rate;
  /** 更新時間 */
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private LocalDateTime updateTime;
}
