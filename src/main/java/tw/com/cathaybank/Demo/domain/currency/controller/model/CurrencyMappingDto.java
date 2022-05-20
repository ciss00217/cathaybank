package tw.com.cathaybank.Demo.domain.currency.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 幣別對照表 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyMappingDto {
  /** 代號 */
  private String code;
  /** 中文名稱 */
  private String name;
}
