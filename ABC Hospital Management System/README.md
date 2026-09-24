# LifeSaver Hospital Management System

LifeSaver is a Java Swing desktop hospital information system built on the supplied DAO, service, authentication, session, and role-based authorization layers. The modernization keeps the application desktop-first and SQL Server-compatible while providing a cohesive clinical interface.

## Technology

- Java 19
- NetBeans 19 / Ant project
- Java Swing
- Microsoft SQL Server
- Microsoft JDBC Driver 12.4.1, included in `lib/`

## Open and run

1. Open the project folder in NetBeans 19. The user-facing application is branded LifeSaver; the legacy folder name is retained for safe compatibility.
2. Confirm Java 19 is the active platform.
3. Edit the root `properties.properties` file for the SQL Server instance, database, username, and password.
4. Run `database/DATABASE_SETUP.sql` in SQL Server Management Studio, then update the connection settings if your instance differs.
5. Clean and build, then run the project. The JDBC driver is configured in `nbproject/project.properties`.

## Authentication and permissions

The existing `UserService`, `Session`, `Authorization`, and `Permission` implementations remain the source of truth. No fake login or parallel authorization system was introduced. Navigation continues to respect the existing permission model.

## Configuration

`properties.properties` is external to compiled classes. Do not commit production passwords. Use a local copy on each workstation and grant the database user only the permissions required by the application.

## Main modules

The project includes patient management, staff management, doctors, nurses, pharmacists, laboratory technicians, nurse assignments, appointments, medical records, billing entities, dashboard statistics, authentication, and SQL Server DAOs.

## Known limitations

Some legacy navigation areas are intentionally presented as controlled placeholders when the supplied backend does not expose a complete GUI workflow. Existing backend entities and services are preserved rather than replaced with fabricated statistics or duplicate implementations.
