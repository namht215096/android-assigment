import java.util.Scanner
import kotlin.math.abs

// class phan so 
class Fraction(private var numerator: Int, private var denominator: Int) {

    init {
        if (denominator == 0) {
            throw IllegalArgumentException("mau phai khac 0")
        }
    }

    // Input 
    fun input(scanner: Scanner) {
        while (true) {
            print("tu: ")
            numerator = scanner.nextInt()
            print("mau: ")
            denominator = scanner.nextInt()
            if (denominator != 0) break
            println("mau phai khac 0")
        }
    }

    // Print 
    fun printFraction() {
        println("$numerator/$denominator")
    }

    // rut gon
    fun simplify() {
        val gcd = gcd(abs(numerator), abs(denominator))
        numerator /= gcd
        denominator /= gcd
        if (denominator < 0) { // denominator always positive
            numerator = -numerator
            denominator = -denominator
        }
    }

    // so sanh  (-1, 0, 1)
    fun compare(other: Fraction): Int {
        val left = numerator * other.denominator
        val right = other.numerator * denominator
        return when {
            left < right -> -1
            left > right -> 1
            else -> 0
        }
    }

    // tong
    fun add(other: Fraction): Fraction {
        val newNum = numerator * other.denominator + other.numerator * denominator
        val newDen = denominator * other.denominator
        val result = Fraction(newNum, newDen)
        result.simplify()
        return result
    }

    // Return 
    fun value(): Double {
        return numerator.toDouble() / denominator.toDouble()
    }

    companion object {
        fun gcd(a: Int, b: Int): Int {
            if (b == 0) return a
            return gcd(b, a % b)
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)

    print("nhap so phan so: ")
    val n = scanner.nextInt()
    val arr = Array(n) { Fraction(1, 1) }

    // Input 
    for (i in arr.indices) {
        println("phan so ${i + 1}:")
        arr[i].input(scanner)
    }

    println("\nphan so da nhap :")
    for (f in arr) f.printFraction()

    // toi gian
    println("\nphan so toi gian:")
    for (f in arr) {
        f.simplify()
        f.printFraction()
    }

    // tong
    var sum = arr[0]
    for (i in 1 until n) {
        sum = sum.add(arr[i])
    }
    println("\ntong cac phan so:")
    sum.printFraction()

    // ps lon nhat
    var maxF = arr[0]
    for (i in 1 until n) {
        if (arr[i].compare(maxF) > 0) {
            maxF = arr[i]
        }
    }
    println("\nphan so lon nhat:")
    maxF.printFraction()

    // thu tu giam dan
    val sortedArr = arr.sortedWith(Comparator { a, b -> b.compare(a) })
    println("\npahn so sap xep theo thu tu:")
    for (f in sortedArr) f.printFraction()
}
