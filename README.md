"# LynnLin" 

## 创建table
```User
create table USER
(
    ID           int auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED INTEGER,
    BIO          VARCHAR(256),
);
```

## flyway migration执行脚本sql
```
    mvn flyway:migrate
```