搜索mysql安装包列表：yum search mysql
安装mysql:yum install mysql-server.x86_64
查看防火墙列表：chkconfig --list |grep iptables
关闭防火墙：service iptables stop
关闭防火墙开机自启动：chkconfig iptables off
修改防火墙列表：vim /etc/sysconfig/iptables
开启mysql开机自启动：chkconfig mysqld on
启动mysql：service mysqld start
给mysql root账户设置密码：mysqladmin -u root password '123456'
登录客户端：mysql -u root -p
设置root账户远程连接权限：GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
更新远程连接权限设置：flush privileges;
查看远程连接权限设置：SELECT DISTINCT CONCAT('User: ''',user,'''@''',host,''';') AS query FROM mysql.user;
查看字符集：SHOW VARIABLES LIKE 'character_set_%';

/etc/my.cnf添加：
[client]
default-character-set=UTF8
在[mysqld]里面添加：
default-character-set=UTF8
