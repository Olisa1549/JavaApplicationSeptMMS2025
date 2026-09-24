# SQL Server setup

Run `LIFESAVER_DATABASE.sql` in SSMS using an account permitted to create a database. This is the standalone LifeSaver schema; it does not use or modify the legacy ABC database. It creates the core clinical tables required by the supplied DAOs and inserts a small set of clearly marked demo records so the dashboard and patient workflow are not empty.

Do not use the older partial `DATABASE_SETUP.sql` as the primary schema. It is retained as a bootstrap reference, while `LIFESAVER_DATABASE.sql` is the correct setup script for this separate project.

The Java login has two separate accounts: the application user such as `admin`, and the SQL Server connection login `mainhospital`. If the console reports `Login failed for user 'mainhospital'`, the SQL Server connection was rejected before the application could inspect the `Users` table. Run `CREATE_SQL_LOGIN.sql` on the same SQL Server instance named in `properties.properties`, then restart the application.

The supplied project contains DAOs for a larger clinical schema but no authoritative schema export. Do not infer production constraints from Java models alone; add the organization-approved clinical schema migration before production use.

After creation, update `properties.properties` with the instance name, database name, SQL login, and password. Verify that the Microsoft JDBC driver remains on the NetBeans classpath.

## Create the first administrator

After the database exists, run `CREATE_ADMIN_USER.sql` in the same database. It creates or resets the full-permission `ADMIN` account:

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `LifeSaver@123` |
| Role | `ADMIN` |

Change the password immediately after first login. The supplied authentication code currently compares `Users.PasswordHash` directly with the entered password; therefore this bootstrap uses the same format for compatibility. Do not expose this account or password in a production environment until the login flow is upgraded to salted one-way password hashing.

For production, use encrypted connections, a dedicated least-privilege SQL login, regular backups, and a secrets-management process rather than storing passwords in source control.
