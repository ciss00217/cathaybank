package tw.com.cathaybank.Demo.domain.currency.gateway.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tw.com.cathaybank.Demo.domain.currency.gateway.api.model.Coindesk;

/** 查詢虛擬貨幣匯率服務 */
@Slf4j
@Service
public class CoindeskService {

  /** RestTemplate */
  @Autowired private RestTemplate restTemplate;

  /** ObjectMapper */
  @Autowired private ObjectMapper objectMapper;

  /**
   * 查詢虛擬貨幣匯率模組
   *
   * @return 虛擬貨幣匯率模組
   */
  public Coindesk getCoindesk() {
    Coindesk coindesk = null;
    try {
      String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
      String result = this.restTemplate.getForObject(url, String.class);
      log.info(result);
      coindesk = this.objectMapper.readValue(result, Coindesk.class);
    } catch (Exception e) {
      throw new RuntimeException("data error");
    }
    return coindesk;
  }
}
