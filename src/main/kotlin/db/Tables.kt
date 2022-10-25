package db

fun createTables() = """
    CREATE TABLE IF NOT EXISTS departamento (
            uuid UUID PRIMARY KEY,
            nombre VARCHAR(255) NOT NULL,
            presupuesto DOUBLE NOT NULL  
        );
    CREATE TABLE IF NOT EXISTS empleado (
            uuid UUID PRIMARY KEY,
            nombre VARCHAR(255) NOT NULL,
            fecha DATE NOT NULL
        )
    """.trimIndent()
