CREATE TABLE commonLog (
    id BIGINT AUTO_INCREMENT,
    appName STRING,
    IPaddr STRING,
    traceId STRING,
    method STRING,
    env STRING,
    logTime BIGINT,
    content STRING,
    logLevel STRING,
    className STRING,
    threadName STRING,
    seq BIGINT,
    dateTime STRING,
    PRIMARY KEY (id)
);
