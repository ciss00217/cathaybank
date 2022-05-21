# cathaybank

## 說明
這是一個簡易的範例，內容含
1. 取得https://api.coindesk.com/v1/bpi/currentprice.json 內容
2. 建立一張幣別與其對應中文名稱的資料表並提供 查詢 / 新增 / 修改 / 刪除 功能 API。
3. Unit Test

##  Sql Script
已更改為自動執行路徑位置為分別為
1. /src/main/resources/data.sql
 ``` sql
insert into currency_mapping (code,name ) values ('USD','美元')
insert into currency_mapping (code,name ) values ('GBP','英鎊')
insert into currency_mapping (code,name ) values ('EUR','歐元')
 ```
2. /src/main/resources/schema.sql
 ``` sql
create table currency_mapping (code varchar(3) not null, name varchar(30), primary key (code))
 ```


