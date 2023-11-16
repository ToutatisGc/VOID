
Mysql 8.0更新默认加密方式为 **caching_sha2_password** ,部分连接工具无法连接,命令行连接Mysql执行命令

PS> mysql -u root -p

USE mysql;

ALTER USER root@'%' IDENTIFIED WITH MYSQL_NATIVE_PASSWORD BY '12345678';

FLUSH PRIVILEGES ;

CREATE SCHEMA VOID DEFAULT CHARSET UTF8MB4 COLLATE utf8mb4_unicode_ci;

排序字符集utf8mb4_unicode_ci和utf8mb4_general_ci的区别

1.准确性
    utf8mb4_unicode_ci 是基于标准的 Unicode 来排序和比较,能够在各种语言之间精确排序.
    utf8mb4_general_ci 没有实现 Unicode 排序规则,在遇到某些特殊语言或者字符集,排序结果可能不一致.
    但是绝大多数情况下,这些特殊字符的顺序并不需要那么精确.
2.性能
    utf8mb4_general_ci 没有特殊排序算法,在比较和排序的时候更快.
    utf8mb4_unicode_ci 在特殊情况下,Unicode排序规则为了能够处理特殊字符的情况,实现了略微复杂的排序算法.
    但是在绝大多数情况下,不会发生此类复杂比较.相比选择哪一种排序字符集,使用者更应该关心字符集与排序规则在数据库里需要统一.
    推荐使用utf8mb4_unicode_ci,但是无特殊情况utf8mb4_general_ci也没啥问题.

MySQL常用排序规则utf8mb4_general_ci、utf8mb4_unicode_ci、utf8mb4_bin
ci即case insensitive,不区分大小写.
utf8mb4_unicode_ci:
是基于标准的Unicode来排序和比较,能够在各种语言之间精确排序,Unicode排序规则为了能够处理特殊字符的情况,实现了略微复杂的排序算法.
utf8mb4_general_ci:
是一个遗留的校对规则,不支持扩展,它仅能够在字符之间进行逐个比较.utf8_general_ci校对规则进行的比较速度很快,但是与使用 utf8mb4_unicode_ci的校对规则相比,比较正确性较差.
utf8mb4_bin:
将字符串每个字符用二进制数据编译存储,区分大小写,而且可以存二进制的内容.
utf8mb4_0900_ai_ci:

MySQL 8.0 默认的是 utf8mb4_0900_ai_ci,属于 utf8mb4_unicode_ci 中的一种,具体含义如下:
uft8mb4 表示用 UTF-8 编码方案,每个字符最多占 4 个字节.
0900 指的是 Unicode 校对算法版本.（Unicode 归类算法是用于比较符合 Unicode 标准要求的两个 Unicode 字符串的方法）.
ai 指的是口音不敏感.也就是说,排序时 e,è,é,ê 和 ë 之间没有区别.
ci 表示不区分大小写.也就是说,排序时 p 和 P 之间没有区别.
utf8mb4 已成为默认字符集,在 MySQL 8.0.1 及更高版本中将 utf8mb4_0900_ai_ci 作为默认排序规则.以前,utf8mb4_general_ci 是默认排序规则.由于 utf8mb4_0900_ai_ci 排序规则现在是默认排序规则,因此默认情况下新表格可以存储基本多语言平面之外的字符.现在可以默认存储表情符号.如果需要重音灵敏度和区分大小写,则可以使用 utf8mb4_0900_as_cs 代替.