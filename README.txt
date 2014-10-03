It needs Tomcat 7 and JVM 1.7.

The project needs to be built with Maven.
It needs two additional things to work:
- add the following line in the 'context.xml' configuration file of Tomcat 7, in the <Context> element:
	<Resource name="jdbc/DemiBox" auth="Container" type="javax.sql.DataSource" maxTotal="100" maxIdle="30" maxWaitMillis="10000" username="username" password="password" driverClassName="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/demibox_db?characterEncoding=utf8" />
- the dependency 'mysql-connector-java' version 5.1.33 must be added manually so you must manually download the .jar file and put it in the TOMCAT_HOME/lib folder and restart Tomcat

The database tables are defined in the 'demibox_db.sql' file.