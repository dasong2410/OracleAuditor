1.jar功能
  oaudit.jar
    数据库审计报告生成工具

2.目录
  cfg：配置文件目录
  ora_rpt：审计报告目录

3.配置数据库连接串
  修改cfg/params.properties中DB_LIST参数的值为你的数据库连接串，多个连接串用英文逗号隔开，格式参照文件中的样例

4.使用方法（如果报OutOfMemoryError: Java heap space，在加后加上内存设置，java -Xms512m -Xmx1024m -jar oaudit.jar）
  java -jar oaudit.jar [all|groupName]
    groupName:
      dbinfo
      sqlperf
      jobinfo

5.查看报告
  用户浏览器打开ora_rpt目录下的index.html文件
