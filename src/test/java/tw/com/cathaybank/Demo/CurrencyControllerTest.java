package tw.com.cathaybank.Demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import tw.com.cathaybank.Demo.domain.currency.controller.model.CurrencyMappingCreateDto;
import tw.com.cathaybank.Demo.domain.currency.controller.model.CurrencyMappingDto;
import tw.com.cathaybank.Demo.domain.currency.controller.model.CurrencyMappingUpdateDto;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.CoindeskService;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.Coindesk;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.CurrencyInfo;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.CurrencyMappingRepository;
import tw.com.cathaybank.Demo.domain.currency.gateway.persistence.model.CurrencyMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** 幣別 controller Test* */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CurrencyControllerTest {

  /** port */
  @LocalServerPort private int port;
  /** 幣別維護Repository */
  @Autowired private CurrencyMappingRepository currencyMappingRepository;
  /** TestRestTemplate */
  @Autowired private TestRestTemplate restTemplate;
  /** ObjectMapper */
  @Autowired private ObjectMapper objectMapper;
  /** 查詢虛擬貨幣匯率服務 */
  @SpyBean private CoindeskService coindeskService;

  /** before each */
  @BeforeEach
  public void beforeEach() {
    this.currencyMappingRepository.deleteAll();
  }

  /**
   * Scenarios 測試呼叫查詢幣別對應表資料 API,並顯示其內容<br>
   * Given 新增一筆對應表<br>
   * When CurrencyController.getCurrencyMappingByCode <br>
   * Then 回傳值需與MockData相等 <br>
   */
  @Test
  public void testGetCurrencyMappingByCode() {

    // Given
    CurrencyMapping currencyMapping = getCurrencyMapping();
    this.currencyMappingRepository.save(getCurrencyMapping());

    //     When
    CurrencyMappingDto response =
        this.restTemplate.getForObject(
            "http://localhost:" + port + "/demo/api/currency/v1/currency-mapping/USD",
            CurrencyMappingDto.class);

    log.info("CurrencyMappingDto:{}", response);

    // Then
    assertEquals(currencyMapping.getCode(), response.getCode());
    assertEquals(currencyMapping.getName(), response.getName());
  }

  /**
   * Scenarios 測試呼叫新增幣別對應表資料 API <br>
   * Given database資料為空 <br>
   * When CurrencyController.insert <br>
   * Then 回傳值需與MockData相等<br>
   */
  @Test
  public void testInsert() {
    //     When
    this.restTemplate.postForObject(
        "http://localhost:" + port + "/demo/api/currency/v1/currency-mapping",
        this.getCurrencyMappingCreateDto(),
        CurrencyMappingCreateDto.class);

    // Then
    CurrencyMapping currencyMapping =
        this.currencyMappingRepository
            .findById(this.getCurrencyMappingCreateDto().getCode())
            .orElseThrow(() -> new RuntimeException("Data not found"));
    assertEquals(this.getCurrencyMappingCreateDto().getCode(), currencyMapping.getCode());
    assertEquals(this.getCurrencyMappingCreateDto().getName(), currencyMapping.getName());
  }

  /**
   * Scenarios 測試呼叫更新幣別對應表資料 API,並顯示其內容<br>
   * Given 新增一筆對應表 <br>
   * When CurrencyController.update<br>
   * Then 需為更新後結果<br>
   */
  @Test
  public void testUpdate() {
    // Given
    CurrencyMapping currencyMapping = getCurrencyMapping();
    this.currencyMappingRepository.save(getCurrencyMapping());

    //  When
    this.restTemplate.put(
        "http://localhost:" + port + "/demo/api/currency/v1/currency-mapping/USD",
        getCurrencyMappingUpdateDto());

    // Then
    CurrencyMapping newCurrencyMapping =
        this.currencyMappingRepository
            .findById(this.getCurrencyMappingCreateDto().getCode())
            .orElseThrow(() -> new RuntimeException("Data not found"));

    log.info("CurrencyMapping:{}", newCurrencyMapping);

    assertEquals("美金", newCurrencyMapping.getName());
  }

  /**
   * Scenarios 測試呼叫刪除幣別對應表資料 API <br>
   * Given 新增一筆對應表 <br>
   * When CurrencyController.delete <br>
   * The table需為零筆<br>
   */
  @Test
  public void testDelete() {
    // Given
    CurrencyMapping currencyMapping = getCurrencyMapping();
    this.currencyMappingRepository.save(getCurrencyMapping());

    //  When
    this.restTemplate.delete(
        "http://localhost:" + port + "/demo/api/currency/v1/currency-mapping/USD");

    // Then
    Optional<CurrencyMapping> currencyMappingOpt = this.currencyMappingRepository.findById("USD");
    assertFalse(currencyMappingOpt.isPresent());
  }

  /**
   * Scenarios 測試呼叫 coindesk API,並顯示其內容。 <br>
   * Given MockCoindesk <br>
   * When CurrencyController.getcCoindesk <br>
   * Then 回傳MockCoindesk應如預期<br>
   */
  @Test
  public void testGetcCoindesk() throws JsonProcessingException {
    // Given
    Mockito.doReturn(this.getMockCoindesk()).when(coindeskService).getCoindesk();

    //  When
    Coindesk response =
        this.restTemplate.getForObject(
            "http://localhost:" + port + "/demo/api/currency/v1/coindesk", Coindesk.class);

    // Then
    Coindesk coindesk = getMockCoindesk();
    log.info("Coindesk:{}", coindesk);
    assertEquals(
        objectMapper.writeValueAsString(coindesk), objectMapper.writeValueAsString(response));
  }

  /**
   * Scenarios 測試呼叫資料轉換的 API,並顯示其內容。<br>
   * Given MockCoindesk <br>
   * When CurrencyController.getCurrencyInfo <br>
   * Then 轉換後結果應如預期<br>
   */
  @Test
  public void testGetCurrencyInfo() throws JsonProcessingException {
    // Given
    Mockito.doReturn(this.getMockCoindesk()).when(coindeskService).getCoindesk();
    this.currencyMappingRepository.save(getCurrencyMapping());

    ResponseEntity<CurrencyInfo[]> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/demo/api/currency/v1/currency-infos",
            CurrencyInfo[].class);
    CurrencyInfo[] currencyInfo = response.getBody();

    // Then
    log.info("response:{}", objectMapper.writeValueAsString(currencyInfo));

    assertEquals("美元", currencyInfo[0].getName());
  }

  private CurrencyMapping getCurrencyMapping() {
    return CurrencyMapping.builder().code("USD").name("美元").build();
  }

  private CurrencyMappingUpdateDto getCurrencyMappingUpdateDto() {
    CurrencyMappingUpdateDto currencyMappingUpdateDto = new CurrencyMappingUpdateDto();
    currencyMappingUpdateDto.setCode("USD");
    currencyMappingUpdateDto.setName("美金");
    return currencyMappingUpdateDto;
  }

  private CurrencyMappingCreateDto getCurrencyMappingCreateDto() {
    CurrencyMappingCreateDto currencyMappingCreateDto = new CurrencyMappingCreateDto();
    currencyMappingCreateDto.setCode("USD");
    currencyMappingCreateDto.setName("美元");
    return currencyMappingCreateDto;
  }

  private List<CurrencyMapping> getCurrencyMappings() {
    LocalDateTime localDateTime = LocalDateTime.now();
    List<CurrencyMapping> currencyMappings = new ArrayList();
    currencyMappings.add(CurrencyMapping.builder().code("USD").name("美元").build());

    currencyMappings.add(CurrencyMapping.builder().code("EUR").name("歐元").build());
    return currencyMappings;
  }

  private Coindesk getMockCoindesk() throws JsonProcessingException {

    String json =
        "{\"time\":{\"updated\":\"May 20, 2022 15:07:00 UTC\"," +
                "\"updatedISO\":\"2022-05-20T15:07:00+00:00\",\"updateduk\":\"May 20, 2022 at 16:07 BST\"}" +
                ",\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). " +
                "Non-USD currency data converted using hourly conversion rate from openexchangerates.org\"," +
                "\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":" +
                "\"29,331.7456\",\"description\":\"United States Dollar\",\"rate_float\":29331.7456},\"GBP\":" +
                "{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"23,970.6064\",\"description\":" +
                "\"British Pound Sterling\",\"rate_float\":23970.6064},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\"" +
                ",\"rate\":\"28,212.5928\",\"description\":\"Euro\",\"rate_float\":28212.5928}}}";

    Coindesk coindesk = this.objectMapper.readValue(json, Coindesk.class);

    return coindesk;
  }
}
