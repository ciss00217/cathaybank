package tw.com.cathaybank.Demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/** Date 共用工具 */
@Slf4j
public class DateUtil {
  /**
   * 字串轉LocalDateTime
   *
   * @param dateStr 日期時間字串
   * @param format 格式
   * @return Date
   */
  public static LocalDateTime parseToLocalDateTime(String dateStr, String format) {
    LocalDateTime date = null;
    if (StringUtils.isNotEmpty(dateStr)) {
      date = LocalDateTime.parse(dateStr, getDefDateTimeFormatter(format));
    }
    return date;
  }

  /**
   * 取得預設DateTimeFormatter<br>
   *
   * @param format 日期格式
   * @return DateTimeFormatter
   */
  private static DateTimeFormatter getDefDateTimeFormatter(String format) {
    return new DateTimeFormatterBuilder().appendPattern(format).toFormatter();
  }
}
