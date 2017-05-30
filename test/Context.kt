import kotlinx.coroutines.experimental.runBlocking
import org.la4j.Vector

/**
 * Created by the-magical-llamicorn on 5/29/17.
 */
interface Context {

    infix fun Vector.approximatelyEquals(vekt: Vektor) =
            runBlocking {
                withinThreshold(
                        vekt.data.await(),
                        toDenseVector().toArray().map { it.toFloat() }.toFloatArray()
                )
            }

    infix fun Vector.approximatelyEquals(vect: Vector) =
            runBlocking {
                withinThreshold(
                        vect.toDenseVector().toArray().map { it.toFloat() }.toFloatArray(),
                        toDenseVector().toArray().map { it.toFloat() }.toFloatArray()
                )
            }

    infix fun Vektor.approximatelyEquals(vect: Vector) =
            runBlocking {
                withinThreshold(
                        data.await(),
                        vect.toDenseVector().toArray().map { it.toFloat() }.toFloatArray()
                )
            }

    infix fun Vektor.approximatelyEquals(vekt: Vektor) =
            runBlocking {
                withinThreshold(
                        data.await(),
                        vekt.data.await()
                )
            }

    private fun withinThreshold(x1: FloatArray, x2: FloatArray) = VektorTestConfig.run {
        x1.mapIndexed { i, value -> value - x2[i] }
                .all { Math.abs(it) < floatingPointThreshold }
    }
}