CREATE TABLE IF NOT EXISTS Stops ( `name` varchar(255), `direction` varchar(4), `time` bigint, `timeReadable` varchar(40), primary key (name, direction, time));