import io.kotlintest.properties.forAll
import io.kotlintest.specs.WordSpec

/**
 * Created by the-magical-llamicorn on 5/29/17.
 */
class VektorTest : WordSpec({
    "+" should {
        "return the element-wise sum of two vectors" {
            forAll(VectorPairs, VectorPairs {
                (vekt1 + vekt2) approximatelyEquals la4j1.add(la4j2)
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads)
    }

    "+" should {
        "return the scalar sum" {
            forAll(VectorScalars, VectorScalars {
                (vekt + scalar) approximatelyEquals la4j.add(scalar.toDouble())
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads)
    }

    "-" should {
        "return the element-wise difference of two vectors" {
            forAll(VectorPairs, VectorPairs {
                (vekt1 - vekt2) approximatelyEquals la4j1.subtract(la4j2)
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads)
    }

    "-" should {
        "return the scalar difference" {
            forAll(VectorScalars, VectorScalars {
                (vekt - scalar) approximatelyEquals la4j.subtract(scalar.toDouble())
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads)
    }

    "*" should {
        "return the element-wise product of two vectors" {
            forAll(VectorPairs, VectorPairs {
                (vekt1 * vekt2) approximatelyEquals la4j1.hadamardProduct(la4j2)
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads)
    }

    "*" should {
        "return the scalar product" {
            forAll(VectorScalars, VectorScalars {
                (vekt * scalar) approximatelyEquals la4j.multiply(scalar.toDouble())
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads)
    }

    "/" should {
        "return the element-wise quotient of two vectors" {
            forAll(VectorPairs, VectorPairs {
                (vekt1 / vekt2) approximatelyEquals la4j1.hadamardProduct(la4j2.transform { _, value -> 1 / value })
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads, enabled = false)
        // floating point division is inaccurate, risk diving by a very small number
    }

    "/" should {
        "return the scalar quotient" {
            forAll(VectorScalars, VectorScalars {
                (vekt / scalar) approximatelyEquals la4j.divide(scalar.toDouble())
            })
        }.config(invocations = VektorTestConfig.invocations, threads = VektorTestConfig.threads, enabled = false)
        // floating point division is inaccurate, risk diving by a very small number
    }
})

