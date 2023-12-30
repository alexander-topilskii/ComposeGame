package com.jazzy.mycomposegame.domain.data

import com.jazzy.mycomposegame.asRadians
import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.clamp
import dev.romainguy.kotlin.math.dot
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class PointF(val x: Float = 0f, val y: Float = 0f) {
    constructor(v: Float) : this(v, v)
    constructor(v: PointF) : this(v.x, v.y)

    inline operator fun plus(v: Float) = PointF(x + v, y + v)
    inline operator fun minus(v: Float) = PointF(x - v, y - v)
    inline operator fun times(v: Float) = PointF(x * v, y * v)
    inline operator fun div(v: Float) = PointF(x / v, y / v)

    inline operator fun plus(v: PointF) = PointF(x + v.x, y + v.y)
    inline operator fun minus(v: PointF) = PointF(x - v.x, y - v.y)
    inline operator fun times(v: PointF) = PointF(x * v.x, y * v.y)
    inline operator fun div(v: PointF) = PointF(x / v.x, y / v.y)

    inline fun transform(block: (Float) -> Float): PointF {
        return PointF(block(x), block(y))
    }

    fun toFloatArray() = floatArrayOf(x, y)
}

inline fun length(v: PointF) = sqrt(v.x * v.x + v.y * v.y)

inline fun dot(a: PointF, b: PointF) = a.x * b.x + a.y * b.y

fun PointF.angle(): Float {
    val rawAngle = atan2(y = this.y, x = this.x)
    return ((rawAngle / PI) * 180F).toFloat()
}

inline fun angle(a: PointF, b: PointF): Float {
    val l = length(a) * length(b)
    return if (l == 0.0f) 0.0f else acos(clamp(dot(a, b) / l, -1.0f, 1.0f))
}


fun PointF.rotate(degrees: Float, origin: PointF = PointF(0f, 0f)): PointF {
    val p = this - origin
    val a = degrees.asRadians

    val w = PointF(
        p.x * cos(a) - p.y * sin(a),
        p.y * cos(a) + p.x * sin(a)
    )

    return w + origin
}