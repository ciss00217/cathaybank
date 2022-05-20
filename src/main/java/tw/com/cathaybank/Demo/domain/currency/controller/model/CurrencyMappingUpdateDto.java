package tw.com.cathaybank.Demo.domain.currency.controller.model;

import lombok.Data;

/** 更新幣別對照表 Data Transfer Object */
@Data
public class CurrencyMappingUpdateDto {
  /** 代號 */
  private String code;
  /** 中文名稱 */
  private String name;
}
