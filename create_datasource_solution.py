# Create data source script.

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
connect(ADMIN_USER, ADMIN_PSWD, ADMIN_URL)

edit()
startEdit()

## delete old DS
# cd('/')
# cmo.destroyJDBCSystemResource(getMBean('/JDBCSystemResources/testds'))

# activate()

# edit()
# startEdit()
## end of delete old DS 

cd('/')
cmo.createJDBCSystemResource(DS_NAME)

cd('/JDBCSystemResources/' + DS_NAME + '/JDBCResource/' + DS_NAME)
cmo.setName(DS_NAME)
cmo.setDatasourceType('GENERIC')

cd('/JDBCSystemResources/' + DS_NAME+ '/JDBCResource/' + DS_NAME + '/JDBCDataSourceParams/' + DS_NAME)
set('JNDINames', jarray.array([JNDI], String))

cd('/JDBCSystemResources/' + DS_NAME + '/JDBCResource/' + DS_NAME + '/JDBCDriverParams/' + DS_NAME)
cmo.setUrl(JDBC_URL)
cmo.setDriverName(DRIVER_CLASS)
set('Password', PASSWORD)

cd('/JDBCSystemResources/' + DS_NAME + '/JDBCResource/' + DS_NAME + '/JDBCDriverParams/' + DS_NAME
   + '/Properties/' + DS_NAME)
cmo.createProperty('user')

cd('/JDBCSystemResources/' + DS_NAME + '/JDBCResource/' + DS_NAME + '/JDBCDriverParams/' + DS_NAME +
   '/Properties/' + DS_NAME + '/Properties/user')
cmo.setValue(USERNAME)

cd('/JDBCSystemResources/' + DS_NAME + '/JDBCResource/' + DS_NAME + '/JDBCDataSourceParams/' + DS_NAME)
cmo.setGlobalTransactionsProtocol('OnePhaseCommit')

cd('/JDBCSystemResources/' + DS_NAME)
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

save()
activate()
disconnect()

exit()
