# movie
# 开箱可用的简易电影推荐系统

我家小仙女毕业论文要用项目，花了点时间 在另一个老哥的项目基础上略做修改。

- 前端 vue vuex 
- 后台 spring boot jpa mahout
- 环境要求 nodejs java mysql

使用方法：

- 前端：

clone代码后，在（装有nodejs的）本地环境 执行 npm install，依赖安装完毕后

执行npm run serve 可以开启 本机调试

执行npm run build 可以编译为静态html部署到nginx、apache等服务器上

- 后端：

配置好数据库，导入sql数据，修改movie-server\src\main\resources\application.properties中的数据库连接信息

修改一个可访问的本机路径为 file.fileFtpPath 用以存储文件

使用常用开发工具idea或eclipse 启动项目即可

- 图片显示逻辑：

前端以 movie 数据的 ima_url 字段数据 拼接host 后发送请求， server 的 /file/view/{hash} 接口收到请求
到文件表中通过hash查找文件记录common_file，以file_url字段的数据访问本机路径，读取文件流并输出。

- jwt简要说明：

一种无状态的认证设计，将用户一部分信息 以特定格式加密后返回给前端。前端携带该jwt 令牌可以被server认证为其签发者用户。

一般存储一些其他业务逻辑所必要的非敏感信息。jwt的构成非常简单，字节占用很小，非常便于传输。
它不需要在服务端保存会话信息, 易于应用的扩展

