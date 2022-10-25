package db

import mu.KotlinLogging
import java.sql.*

private val logger = KotlinLogging.logger {}

object DataBaseManager {
    private lateinit var serverUrl: String
    private lateinit var serverPort: String
    private lateinit var dataBaseName: String
    private lateinit var user: String
    private lateinit var password: String


    private var jdbcDriver: String = "org.h2.Driver"

    private var connection: Connection? = null
    private var preparedStatement: PreparedStatement? = null


    init {
        initConfig()
    }

    private fun initConfig() {
        serverUrl = "localhost"
        serverPort = "3306"
        dataBaseName = "departamento-empleado"
        jdbcDriver = "org.h2.Driver"
        user = "blog"
        password = "blog1234"

        logger.debug { "Configuración de acceso a la Base de Datos cargada" }
    }

    @Throws(SQLException::class)
    fun open() {
        val url = "jdbc:h2:mem:${this.dataBaseName};DB_CLOSE_DELAY=-1"

        connection = DriverManager.getConnection(url, user, password)
        logger.debug { "Conexión a la Base de Datos establecida: $url" }
    }

    @Throws(SQLException::class)
    fun close() {
        preparedStatement?.close()
        connection?.close()
        logger.debug { "Conexión a la Base de Datos cerrada" }
    }

    @Throws(SQLException::class)
    private fun executeQuery(querySQL: String, vararg params: Any?): ResultSet? {
        preparedStatement = connection?.prepareStatement(querySQL)
        // Si hay parámetros, los asignamos
        return preparedStatement?.let {
            for (i in params.indices) {
                it.setObject(i + 1, params[i])
            }
            logger.debug { "Ejecutando consulta: $querySQL con parámetros: ${params.contentToString()}" }
            it.executeQuery()
        }
    }

    @Throws(SQLException::class)
    fun select(querySQL: String, vararg params: Any?): ResultSet? {
        return executeQuery(querySQL, *params)
    }

    @Throws(SQLException::class)
    fun select(querySQL: String, limit: Int, offset: Int, vararg params: Any?): ResultSet? {
        val query = "$querySQL LIMIT $limit OFFSET $offset"
        return executeQuery(query, *params)
    }

    @Throws(SQLException::class)
    fun insert(insertSQL: String, vararg params: Any?): Int {
        return updateQuery(insertSQL, *params)
    }

    @Throws(SQLException::class)
    fun update(updateSQL: String, vararg params: Any?): Int {
        return updateQuery(updateSQL, *params)
    }

    @Throws(SQLException::class)
    fun delete(deleteSQL: String, vararg params: Any?): Int {
        return updateQuery(deleteSQL, *params)
    }

    @Throws(SQLException::class)
    private fun updateQuery(genericSQL: String, vararg params: Any?): Int {
        preparedStatement = connection?.prepareStatement(genericSQL)
        return preparedStatement?.let {
            for (i in params.indices) {
                preparedStatement!!.setObject(i + 1, params[i])
            }
            logger.debug { "Ejecutando consulta: $genericSQL con parámetros: ${params.contentToString()}" }
            it.executeUpdate()
        } ?: 0

    }

    fun createTables(genericSQL: String): Int {
        logger.debug { "Creando Tablas..." }
        return updateQuery(genericSQL)
    }
}
