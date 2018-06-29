SHOW search_path
ALTER DATABASE sigadb SET search_path = siga,public;

--conexiones activas
select * from pg_stat_activity
where datname = 'clinicadb'

SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'clinicadb';