# Create data source script.

# Change constants values to reflect your environment
ADMIN_USER='wls'
ADMIN_PSWD='admin123*'
ADMIN_URL='t3://localhost:7001'

DS_NAME='testds'
JNDI='jdbc/testds'
JDBC_URL='jdbc:h2:tcp://localhost/~/test'
DRIVER_CLASS='org.h2.jdbcx.JdbcDataSource'
USERNAME='sa'
PASSWORD='sa'

# Connect to running WLS instance - the data source will be created in online mode.

# if you wish to delete existing DS, uncomment following lines:
# ------------ delete old DS -------------

# cd('/')
# cmo.destroyJDBCSystemResource(getMBean('/JDBCSystemResources/' + DS_NAME))

# activate()

# edit()
# startEdit()

# ----------- end of delete old DS ----------


save()
activate()
disconnect()
exit()
