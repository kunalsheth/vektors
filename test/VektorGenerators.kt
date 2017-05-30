import io.kotlintest.properties.Gen
import org.la4j.Vector
import java.util.*

/**
 * Created by the-magical-llamicorn on 5/29/17.
 */
val random = Random()

fun randomBetween(min: Number, max: Number) = (random.nextDouble() * (max.toDouble() - min.toDouble())) + min.toDouble()

object VectorPairs : Gen<VectorPairs.VectorPairContext> {
    override fun generate(): VectorPairContext {
        val length = VektorTestConfig.run { randomBetween(minLength, maxLength).toInt() }
        val x = FloatArray(length, { VektorTestConfig.run { randomBetween(minValue, maxValue).toFloat() } })
        val y = FloatArray(length, { VektorTestConfig.run { randomBetween(minValue, maxValue).toFloat() } })
        return VectorPairContext(
                Vektor(x), Vektor(y),
                Vector.fromArray(x.map { it.toDouble() }.toDoubleArray()),
                Vector.fromArray(y.map { it.toDouble() }.toDoubleArray())
        )
    }

    operator fun invoke(block: VectorPairContext.() -> Boolean): (VectorPairContext) -> Boolean = {
        context ->
        context.run(block)
    }

    data class VectorPairContext(val vekt1: Vektor, val vekt2: Vektor, val la4j1: org.la4j.Vector, val la4j2: org.la4j.Vector) : Context
}

object VectorScalars : Gen<VectorScalars.VectorScalarContext> {
    override fun generate(): VectorScalarContext {
        val length = VektorTestConfig.run { randomBetween(minLength, maxLength).toInt() }
        val x = FloatArray(length, { VektorTestConfig.run { randomBetween(minValue, maxValue).toFloat() } })
        return VectorScalarContext(
                Vektor(x),
                Vector.fromArray(x.map { it.toDouble() }.toDoubleArray()),
                VektorTestConfig.run { randomBetween(minValue, maxValue).toFloat() }
        )
    }

    operator fun invoke(block: VectorScalarContext.() -> Boolean): (VectorScalarContext) -> Boolean = {
        context ->
        context.run(block)
    }

    data class VectorScalarContext(val vekt: Vektor, val la4j: org.la4j.Vector, val scalar: Float) : Context
}