import VektorConfig.calculator
import VektorConfig.context
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.util.*

/**
 * Created by the-magical-llamicorn on 5/26/17.
 */
class Vektor(val data: Deferred<FloatArray>) {

    constructor(floatArray: FloatArray) : this(async(context) { floatArray })
    constructor(len: Int) : this(FloatArray(len))
    constructor(len: Int, init: (Int) -> Float) : this(FloatArray(len, init))

    suspend fun execute(operation: Calculator.Operations, that: Vektor) =
            calculator.execute(this@Vektor.data.await(), operation, that.data.await())

    suspend fun execute(operation: Calculator.Operations, that: Float) =
            calculator.execute(this@Vektor.data.await(), operation, that)

    operator fun plus(that: Vektor) = Vektor(async(context) { execute(Calculator.Operations.ADD, that) })
    operator fun plus(that: Number) = Vektor(async(context) { execute(Calculator.Operations.ADD, that.toFloat()) })

    operator fun minus(that: Vektor) = Vektor(async(context) { execute(Calculator.Operations.SUBTRACT, that) })
    operator fun minus(that: Number) = Vektor(async(context) { execute(Calculator.Operations.SUBTRACT, that.toFloat()) })

    operator fun times(that: Vektor) = Vektor(async(context) { execute(Calculator.Operations.MULTIPLY, that) })
    operator fun times(that: Number) = Vektor(async(context) { execute(Calculator.Operations.MULTIPLY, that.toFloat()) })

    operator fun div(that: Vektor) = Vektor(async(context) { execute(Calculator.Operations.DIVIDE, that) })
    operator fun div(that: Number) = Vektor(async(context) { execute(Calculator.Operations.DIVIDE, that.toFloat()) })

    operator fun compareTo(that: Vektor) = this.compareTo(that.magnitude)
    operator fun compareTo(that: Number) = this.magnitude.compareTo(that.toFloat())

    operator fun unaryMinus() = this * -1f
    operator fun unaryPlus() = this

    infix fun euclideanDistance(that: Vektor) = async(context) { (this@Vektor - that).magnitude }
    infix fun cosineSimilarity(that: Vektor) = async(context) { (this@Vektor * that).data.await().sum() / (this@Vektor.magnitude * that.magnitude) }

    val magnitude by lazy { runBlocking { Math.sqrt(data.await().map { it * it }.sum().toDouble()).toFloat() } }
    override fun toString() = runBlocking { Arrays.toString(data.await()) ?: data.toString() }
    override fun equals(that: Any?) = runBlocking { this === that || (that is Vektor && Arrays.equals(this@Vektor.data.await(), that.data.await())) }
    override fun hashCode() = runBlocking { Arrays.hashCode(data.await()) }
}