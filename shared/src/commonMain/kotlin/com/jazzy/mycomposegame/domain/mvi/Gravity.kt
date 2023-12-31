package com.jazzy.mycomposegame.domain.mvi

import com.jazzy.mycomposegame.asDegrees
import com.jazzy.mycomposegame.asRadians
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.ui.GameStoreFactory
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object Gravity{
    suspend fun gravity(
        dt: Float,
        playerData: PlayerData?,
        action: suspend (GameStoreFactory.Msg) -> Unit
    ) {
        if (playerData == null) return

        val gravity = 1f
        val currentSpeed = playerData.speed
        val currentAngle = playerData.angle

        val newSpeed = gravity * dt
        val angleToDown = 90f

        val (updatedSpeed, updatedAngle) = calculate(currentSpeed, currentAngle, newSpeed, angleToDown)


        action(GameStoreFactory.Msg.PlayerMovementUpdated(updatedSpeed, updatedAngle))
    }
}

fun averageAngle(angle1: Float, angle2: Float): Float {
    // Приведение углов к диапазону от 0 до 360 градусов
    val normalizedAngle1 = (angle1 + 360f) % 360f
    val normalizedAngle2 = (angle2 + 360f) % 360f

    // Вычисление среднего угла
    val average = (normalizedAngle1 + normalizedAngle2) / 2f


    // Приведение среднего угла к диапазону от 0 до 360, если необходимо
    return average % 360f
}

fun calculate(speed1: Float, angle1: Float, speed2: Float, angle2: Float): Pair<Float, Float> {
    // Преобразование углов в радианы
    val angle1Rad = angle1.asRadians
    val angle2Rad = angle2.asRadians

    // Разложение векторов на компоненты
    val x1 = speed1 * cos(angle1Rad)
    val y1 = speed1 * sin(angle1Rad)

    val x2 = speed2 * cos(angle2Rad)
    val y2 = speed2 * sin(angle2Rad)

    // Сложение компонентов
    val x = x1 + x2
    val y = y1 + y2

    // Вычисление модуля и угла результирующего вектора
    val resultSpeed = sqrt(x * x + y * y)
    val resultAngle = atan2(y, x).asDegrees

    return Pair(resultSpeed, resultAngle)
}