# Kerberos BeeLine
A wrapper of Hive Beeline to ease the pain when connect to HiveServer2 secured by kerberos.

# Compile
```
mvn clean package
```

# Usage
1. Add kerberos-beeline.jar to hive's or spark's lib directory.  
For hive, it's `<hive_install_dir>/lib`.  
For spark, it's `<spark_install_dir>/jars`.

2. Add shell script to hive's or spark's bin directory.  
For hive, copy [hive/kerberos-beeline](hive/kerberos-beeline) to `<hive_install_dir>/bin`. Copy [hive/kerberos-beeline.sh](hive/kerberos-beeline.sh) to `<hive_install_dir>/bin/ext`.  
For spark, copy [spark/kerberos-beeline](spark/kerberos-beeline) to `<spark_install_dir>/bin`.
   
3. Prepare a config file and run  
beeline.conf
   ```
   # It is a java properties file
   krb5Conf=<krb5.conf>
   principal=<principal>
   keytab=<keytab>
   jdbcUrl=<jdbcUrl>
   ```
   Command:
   ```shell
   $ <install_dir>/bin/kerberos-beeline beeline.conf
   ```
