package tw.com.cathaybank.Demo.domain.currency.gateway.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.model.CurrencyMapping;

import java.util.List;
import java.util.Optional;

/** 幣別對照維護服務 */
@Service
public class CurrencyMappingService {

  /** 幣別維護Repository */
  @Autowired private CurrencyMappingRepository currencyMappingRepository;

  /**
   * 查詢全項幣別對照資訊
   *
   * @return 全項幣別對照資訊
   */
  public List<CurrencyMapping> getAll() {
    return this.currencyMappingRepository.findAll();
  }

  /**
   * 查詢幣別對照資訊
   *
   * @param code 幣別代號
   * @return 幣別對照資訊
   */
  public Optional<CurrencyMapping> getByCode(String code) {
    return this.currencyMappingRepository.findById(code);
  }

  /**
   * 新增幣別對照資訊
   *
   * @param code 幣別代號
   * @param name 幣別中文名稱
   */
  public void insert(String code, String name) {
    CurrencyMapping newCurrencyMapping =
        CurrencyMapping.builder()
            .code(code)
            .name(name)
            .build();

    if (this.currencyMappingRepository.existsById(code)) {
      throw new RuntimeException("此資料已存在");
    }

    this.currencyMappingRepository.save(newCurrencyMapping);
  }

  /**
   * 更新幣別對照資訊
   *
   * @param code 幣別代號
   * @param name 幣別中文名稱
   * @return 幣別對照表
   */
  public CurrencyMapping update(String code, String name) {
    CurrencyMapping currencyMapping =
        this.currencyMappingRepository
            .findById(code)
            .orElseThrow(() -> new RuntimeException("查無資料"));
    currencyMapping.setName(name);
    return this.currencyMappingRepository.save(currencyMapping);
  }

  /**
   * 刪除幣別對照資訊
   *
   * @param code 幣別代號
   */
  public void delete(String code) {
    this.currencyMappingRepository.delete(CurrencyMapping.builder().code(code).build());
  }
}
