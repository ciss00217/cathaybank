package tw.com.cathaybank.Demo.domain.currency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.CoindeskService;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.Coindesk;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.Currency;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.CurrencyInfo;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.CurrencyMappingService;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.model.CurrencyMapping;
import tw.com.cathaybank.Demo.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 貨幣服務 */
@Service
public class CurrencyService {

  /** 查詢虛擬貨幣匯率服務 */
  @Autowired private CoindeskService coindeskService;
  /** 幣別對照維護服務 */
  @Autowired private CurrencyMappingService currencyMappingService;

  /**
   * 查詢幣別資訊
   *
   * @return 幣別資訊
   */
  public List<CurrencyInfo> getCurrencyInfo() {
    Coindesk coindesk = this.coindeskService.getCoindesk();
    String updateTimeStr = coindesk.getTime().getUpdatedISO();
    LocalDateTime updateTime =
        DateUtil.parseToLocalDateTime(updateTimeStr, "yyyy-MM-dd'T'HH:mm:ssXXX");

    List<CurrencyMapping> currencyMappings = this.currencyMappingService.getAll();

    Map<String, CurrencyMapping> currencyMappingMap =
        currencyMappings.stream()
            .collect(Collectors.toMap(CurrencyMapping::getCode, Function.identity()));

    Map<String, Currency> bpi = coindesk.getBpi();

    return bpi.values().stream()
        .map(o -> convert(o, currencyMappingMap.get(o.getCode()), updateTime))
        .collect(Collectors.toList());
  }

  private CurrencyInfo convert(
      Currency currency, CurrencyMapping currencyMapping, LocalDateTime updateTime) {

    return CurrencyInfo.builder()
        .code(currency.getCode())
        .name(Optional.ofNullable(currencyMapping).map(o -> o.getName()).orElse(null))
        .rate(currency.getRateFloat())
        .updateTime(updateTime)
        .build();
  }
}
