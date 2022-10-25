package model

import java.util.UUID

data class Departamento(
    val uuid: UUID=UUID.randomUUID(),
    var nombre: String,
    var presupuesto: Double
){
    override fun toString(): String {
        return "Departamento(uuid=$uuid," +
                " nombre='$nombre'," +
                " presupuesto='$presupuesto)"
    }
}