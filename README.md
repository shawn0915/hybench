# HyBench

Hybench是一款由中国软件评测中心、清华大学联合牵头，北京奥星贝斯科技有限公司、武汉达梦数据库股份有限公司、华为技术有限公司、腾讯云计算有限公司、阿里云计算有限公司共同研发的HTAP数据库基准测试工具。

Hybench针对HTAP数据库技术特点，参考实际典型应用场景进行设计，数据模型采用在线金融交易分析场景，提供OLTP、OLAP、OLXP三类典型HTAP负载，支持不同规模的数据集，可以计算出TPS、QPS、XPS、新鲜度等不同维度的评价指标，最终给出统一评价指标H-Score。为数据库厂商和第三方评测机构提供HTAP数据库基准性能的评价方法及工具，引导HTAP数据库的技术研究方向，帮助用户进行HTAP数据库选型。

术语及缩略语
* HTAP：Hybrid Transaction Analytical Processing，混合事务与分析处理
* OLTP：Online Transaction Processing联机事务处理
* OLAP：Online Analytical Processing 联机分析处理
* OLXP：Online miXed Processing联机混合负载处理
* 混合负载：并发执行的交互式查询与分析型事务
* TPS：Transaction Per Second 每秒事务处理数
* QPS：Queries Per Second 每秒查询处理数
* XPS：miXed operations Per Second 每秒混合负载处理数
* 新鲜度：主节点与副本节点的查询结果的最大时间延迟

## 系统概述

Hybench实现了Hybench基准测试，集成了测试数据集生成，HTAP负载运行、新鲜度度量、测试结果统计分析等多种功能，总体架构如下图所示：

<img decoding="async" src="https://atomgit.com/cstc2023/HyBench/edit/master/src/main/resources/overview.png">
src/main/resources/overview.png 目录下

通过该工具，我们不仅可以完成Hybench基准测试，得到评价指标H-Score，还可以进行多种单项测试，对单项性能进行评判。下表是Hybench工具提供的测试类型以及对应的性能指标。

|测试类型|性能指标|
|----|----|
|Hybench基准测试|	H-Score|
|OLTP测试	|TPS|
|OLAP测试	|QPS|
|OLXP测试	|XPS（包含XP-TPS，XP-QPS）|
|新鲜度测试	|Freshness|

用户，数据库厂商，第三方评测机构均可使用Hybench。用户可以利用Hybench进行基准测试，H-Score可作为数据库选型的参考指标；数据库厂商在数据库研发过程中，可利用Hybench进行面向HTAP场景的性能评价和压测；第三方评测机构可利用Hybench提供客观的数据库性能测试服务。

## 总体设计
HyBench程序可以分为4个大模块，分别是生成测试数据模块，参数与命令行解析模块，执行负载模块，结果收集与展示模块。其中执行负载模块又可以细化为分析型负载（AP），事务处理性负载（TP）以及新鲜度计算（Freshness）三个子模块。联机混合负载（XP）是融合了IQ和AT两种类型的负载，IQ和AT分别在AP和TP模块中执行。另外分析型负载又分为的power测试和throughtout测试，throughtout测试的结果会计算到H-Score中。
* 数据生成模块，可以通过配置文件配置数据量。各个特征数据的生成范围是记录在resource/parameters.toml中。
* 程序提供了多个执行选项，包括gendata，runAPower，runAP ,runTP, runXP, runHTAP，runFresh以及runAll。通过解析模块解析命令行参数并加载配置文件执行相应的负载。
* 在执行负载模块中，APClient和TPClient都继承Client类。Client类中包括了负载执行时间的控制，负载并发控制，计算TPS,QPS等功能。APClient和TPClient主要是执行各类sql，统计事务执行时间等功能。
* 结果展示模块，主要用于执行期间的结果收集，以及最后H-Scoure的计算与各指标的输出。

#### Hybench代码结构如下表所示
<table border="2" >
  <tr>
    <td>conf</td>
    <td>
用于存放4种配置文件
1. ap，tp和xp的sql file <br>
2. 兼容各个数据库语法的ddl文件 <br>
3. 数据库连接信息，配置时间等配置文件 <br>
4. 日志配置文件 <br>
    </td>
    <td>
1. 提供了兼容mysql、pg和oracle三种语法规则的sql文件Stmt_mysql.toml，stmt_oracle.toml，stmt_pg.toml  <br>
2. 提供了兼容mysql、pg和oracle三种语法规则的ddl文件  <br>
3. db.prop  <br>
4. log4j2.properties  <br>
    </td>
  </tr>
  <tr>
  <td>lib</td>
  <td>用于存放依赖的各类jar </td>
  <td>checker-qual-3.5.0.jar<br>
  gson-2.8.1.jar<br>    
  log4j-core-2.19.0.jar<br>
commons-cli-1.4.jar<br>  
guava-18.0.jar   <br>      
commons-math3-3.6.1.jar <br> 
log4j-api-2.19.0.jar<br>      
toml4j-0.7.2.jar</td>
  </tr>
  <tr>
  <td>src</td>
  <td>源代码文件，包括java文件以及resource文件</td>
  <td>
1. dbconn：数据库连接模块 <br> 
2. load：数据生成模块 <br> 
3. pojo：数据库表对应的对象 <br> 
4. stats：结果统计与计算模块 <br> 
5. util：随机数据生成模块 <br> 
6. workload：包括AP,TP和新鲜度计算等执行模块 <br> 
7. 其他：包括main入口，命令行解析，配置文件读取模块 <br> 
  </td>
  </tr>
  <tr>
  <td>hybench</td>
  <td>执行程序的Shell脚本</td>
  <td>里面配置了环境变量，封装了java执行命令(需要配置jdk17)</td>
  </tr>
</table>

## 使用说明
### 数据生成
数据生成模块主要是用于生成测试时所需要的表数据,并以csv格式保存到磁盘上,以便数据库进行导入操作.其在生成数据时,主要需要db.prop文件以及parameters.toml文件,在db.prop文件中主要需要读取的是“sf”参数,这个参数决定了生成的数据大小,之后数据生成模块会根据sf这个参数到parameters.toml读取对应的一些基础数据,这些基础数据包括需每个表需要生成的数据条数、每个列的起始以及结束值、生成数据文件的文件名等,之后数据生成模块会基于这些基础数据来生成测试所需要的数据文件.
* 生成数据命令 例如：
./hybench -t gendata -c conf/db.prop
### 初始化表结构
* 执行ddl语句 例如：
./hybench -t sql -f conf/load_mysql.sql -c conf/db.prop
### 执行负载
* 其中根据命令行-t指定的负载类型分别执行对应的负载。 例如：
./hybench -t runtp -c conf/db.prop -f conf/stmt_mysql.toml

### 命令行选项
```
usage: hybench  [options]
 -c,--conffile <conf>   The path to configuration
 -f,--sqlfile <file>    The path to sql files
 -h,--help              Print HyBench usage information
 -s,--silent            Don't print detail test response time histogram
 -t,--testType <type>   This is for test type. Now we support three types,
                        execSql, gendata and runX.
Example:
Step 1: run sql files for init or cleanup
  hybench -t sql -f sql/sqlfile.sql -c conf/db.properties
Step 2: generate data and load
  hybench -t gendata -c conf/db.properties
Step 3: run AP Power workload
  hybench -t runappower -c conf/db.properties -f sql/sql_file.sql
Step 4: run TP workload
  hybench -t runtp -c conf/db.properties -f sql/sql_file.sql
Step 5: run AP workload
  hybench -t runap -c conf/db.properties -f sql/sql_file.sql
Step 6: run XP workload
  hybench -t runxp -c conf/db.properties -f sql/sql_file.sql
Step 7: run fresh workload
  hybench -t runfresh -c conf/db.properties -f sql/sql_file.sql
Step 8: run htap workload
  hybench -t runhtap -c conf/db.properties -f sql/sql_file.sql
Step 9: run all workload
  hybench -t runall -c conf/db.properties -f sql/sql_file.sql
```

### 配置文件参数列表

| 参数名            | 默认值                                       | 值设置建议                                                                                  | 备注                                       |   |
|----------------|-------------------------------------------|----------------------------------------------------------------------------------------|------------------------------------------|---|
| db             | mysql                                     | 代表本轮测试的数据库是什么名字，若为MySQL协议的，则填写MySQL,若为postgresql协议的则填写postgresql，同时也可以填写其他类型的数据库。      | 在代码中无具体意义，主要用于打印结果。                      |   |
| classname      | com.mysql.jdbc.Driver                     | 指TP类的SQL访问采用那个JDBC的类                                                                   |                                          |   |
| username       | xxx                                       | 指TP类访问连接的用户名                                                                           |                                          |   |
| password       | xxx                                       | 指TP类访问连接的密码                                                                            |                                          |   |
| url            | jdbc:oceanbase:// :<PORT>/?useUnicode=xxx | TP类访问的jdbc 连接串，根据各jdbc的定义自行填写。后续可根据实际情况填写连接参数                                          |                                          |   |
| url_ap         | jdbc:oceanbase:// :<PORT>/?useUnicode=xxx | AP类访问的jdbc 连接串，根据各jdbc的定义自行填写。后续可根据实际情况填写连接参数                                          |                                          |   |
| classname_ap   | com.mysql.jdbc.Driver                     | 指AP类的SQL访问采用那个JDBC的类                                                                   |                                          |   |
| username_ap    | xxx                                       | AP类访问连接的用户名                                                                            |                                          |   |
| password_ap    | xxx                                       | AP类访问连接的用户名                                                                            |                                          |   |
| sf             | 1x                                        | 代表测试的数据集大小，仅支持1x、10x。                                                       | 在生成数据和执行SQL时候均会用到。只能支持2个入参值，不支持其他的数据集大小。 |   |
| at1_percent    | 35                                        | 代表在执行混合负载时，AT1类的SQL的执行比例。如值为35，则代表为35%。                                                | 与其他几个AT类的SQL的执行比例合为100。建议直接使用默认值。        |   |
| at2_percent    | 25                                        | 代表在执行混合负载时，AT2类的SQL的执行比例。如值为25，则代表为25%。                                                | 与其他几个AT类的SQL的执行比例合为100。建议直接使用默认值。        |   |
| at3_percent    | 15                                        | 代表在执行混合负载时，AT3类的SQL的执行比例。如值为15，则代表为15%。                                                | 与其他几个AT类的SQL的执行比例合为100。建议直接使用默认值。        |   |
| at4_percent    | 15                                        | 代表在执行混合负载时，AT4类的SQL的执行比例。如值为35，则代表为15%。                                                | 与其他几个AT类的SQL的执行比例合为100。建议直接使用默认值。        |   |
| at5_percent    | 7                                         | 代表在执行混合负载时，AT5类的SQL的执行比例。如值为35，则代表为7%。                                                 | 与其他几个AT类的SQL的执行比例合为100。建议直接使用默认值。        |   |
| at6_percent    | 3                                         | 代表在执行混合负载时，AT6类的SQL的执行比例。如值为35，则代表为3%。                                                 | 与其他几个AT类的SQL的执行比例合为100。建议直接使用默认值。        |   |
| apclient       | 1                                         | 在执行AP类的测试时，所开启的AP类连接数量。也可以理解为AP请求的并发数。                                                 |                                          |   |
| tpclient       | 1                                         | 在执行TP类的测试时，所开启的TP类连接数量。也可以理解为TP请求的并发数。                                                 |                                          |   |
| fresh_interval | 20                                        | 新鲜度测试的间隔时间。基于xpRunMins 的值来判断。若此值设置为20.xpRunMins 设置为1分钟，则代表1分钟除以20为3秒。意味着每3秒执行以下新鲜度的查询。 |                                          |   |
| apRunMins      | 1                                         | AP类测试的运行时长。                                                                            |                                          |   |
| tpRunMins      | 1                                         | TP类测试的运行时长                                                                             |                                          |   |
| xpRunMins      | 1                                         | XP类测试的运行时长                                                                             |                                          |   |
| xtpclient      | 1                                         | 在XP测试场景中，TP类型的请求所使用的连接数。                                                               |                                          |   |
| xapclient      | 1                                         | 在XP测试场景中，AP类型的请求所使用的连接数                                                                |                                          |   |
| apround        | 1                                         | AP类型请求的执行轮数，要求至少1轮完整跑完。                                                                | 仅在AP Power测试负载中生效。                       |   |
