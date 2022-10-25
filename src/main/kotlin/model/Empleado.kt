package model

import java.time.LocalDate
import java.util.UUID

data class Empleado(
    val uuid: UUID,
    var nombre: String,
    var fecha: LocalDate
){
    override fun toString(): String {
        return super.toString()
    }
}