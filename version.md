- 3.6.12
  - 新增 JSON 函数注解：@JsonExtract、@JsonUnquote、@JsonQuote、@JsonLength、@JsonKeys、@JsonType、@JsonValid、@JsonDepth、@JsonContains、@JsonObject、@JsonArray

- 3.5.11.7
  - 底层优化，新增update字段校验.不允许更新空字段
  - sql自动识别优化批处理
  - 新增注解@GroupConcat
  - 修改通用api返回Id包装类型
  - @ColName新增excludeColAll属性

- 3.5.11.8
  - 避免批处理事务自动提交
  - 优化函数解析  
  