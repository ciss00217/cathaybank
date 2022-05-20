package tw.com.cathaybank.Demo.domain.currency.gateway.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/** 幣別對照表 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyMapping {
  /** 代號 */
  @Id
  @Column(length = 3)
  private String code;
  /** 中文名稱 */
  @Column(length = 30)
  private String name;
}
