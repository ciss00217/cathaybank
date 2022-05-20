package tw.com.cathaybank.Demo.domain.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tw.com.cathaybank.Demo.domain.currency.controller.model.CurrencyMappingCreateDto;
import tw.com.cathaybank.Demo.domain.currency.controller.model.CurrencyMappingDto;
import tw.com.cathaybank.Demo.domain.currency.controller.model.CurrencyMappingUpdateDto;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.CoindeskService;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.Coindesk;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.CurrencyInfo;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.CurrencyMappingService;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.model.CurrencyMapping;
import tw.com.cathaybank.Demo.domain.currency.service.CurrencyService;

import java.util.List;

/** 幣別 controller */
@RestController
@RequestMapping("/api/currency/v1")
public class CurrencyController {
  /** 幣別對照維護服務 */
  @Autowired private CurrencyMappingService currencyMappingService;
  /** 查詢虛擬貨幣匯率服務 */
  @Autowired private CoindeskService coindeskService;
  /** 貨幣服務 */
  @Autowired private CurrencyService currencyService;

  /**
   * 查詢幣別對照資訊
   *
   * @param code 幣別代碼
   * @return 幣別對照表
   */
  @GetMapping(value = "/currency-mapping/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CurrencyMappingDto getCurrencyMappingByCode(@PathVariable String code) {
    CurrencyMappingDto currencyMappingDto =
        this.currencyMappingService
            .getByCode(code)
            .map(
                o ->
                    CurrencyMappingDto.builder()
                        .code(o.getCode())
                        .name(o.getName())
                        .build())
            .orElse(null);

    return currencyMappingDto;
  }

  /**
   * 新增幣別對照資訊
   *
   * @param currencyMappingCreateDto 新增幣別對照表 Data Transfer Object
   */
  @PostMapping("/currency-mapping")
  public void insert(@RequestBody CurrencyMappingCreateDto currencyMappingCreateDto) {
    this.currencyMappingService.insert(
        currencyMappingCreateDto.getCode(), currencyMappingCreateDto.getName());
  }

  /**
   * 更新幣別對照資訊
   *
   * @param code 幣別代號
   * @param currencyMappingUpdateDto
   * @return 幣別對照表
   */
  @PutMapping ("/currency-mapping/{code}")
  public CurrencyMappingDto update(
      @PathVariable String code, @RequestBody CurrencyMappingUpdateDto currencyMappingUpdateDto) {
    CurrencyMapping currencyMapping =
        this.currencyMappingService.update(
            currencyMappingUpdateDto.getCode(), currencyMappingUpdateDto.getName());

    return CurrencyMappingDto.builder()
        .code(currencyMapping.getCode())
        .name(currencyMapping.getName())
        .build();
  }

  /**
   * 刪除幣別對照資訊
   *
   * @param code 幣別代號
   */
  @DeleteMapping("/currency-mapping/{code}")
  public void delete(@PathVariable String code) {
    this.currencyMappingService.delete(code);
  }

  /**
   * 查詢虛擬貨幣匯率
   *
   * @return 幣別對照表
   */
  @GetMapping("/coindesk")
  public Coindesk getcCoindesk() {
    return this.coindeskService.getCoindesk();
  }

  /**
   * 查詢全項幣別對照資訊
   *xz
   * @return 幣別對照表
   */
  @GetMapping("/currency-infos")
  public List<CurrencyInfo> getCurrencyInfo() {
    return this.currencyService.getCurrencyInfo();
  }
}
