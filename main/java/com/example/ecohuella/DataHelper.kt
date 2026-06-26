
package com.example.ecohuella

import android.content.Context
import androidx.core.content.edit

object DataHelper {
    private const val PREFS_NAME = "eco_huella_prefs"
    private const val KEY_TOTAL_POINTS = "total_points"
    private const val KEY_RACHA = "racha_actual"
    private const val KEY_LAST_DAY = "ultimo_dia_guardado"
    
    fun saveDayPoints(context: Context, points: Int, day: String = "today", updateTotal: Boolean = true){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentTotal = getPoints(context)
        val currentDayPoints = getDayPoints(context, day)
        
        prefs.edit {
            if (updateTotal) {
                putInt(KEY_TOTAL_POINTS, currentTotal + points)
            }
            putInt(day, currentDayPoints + points)
        }
    }

    fun getPoints(context: Context): Int{
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_TOTAL_POINTS, 0)
    }

    fun getDayPoints(context: Context, day: String): Int{
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(day, 0)
    }

    fun actualizarRacha(context: Context, hoy: String){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val ultimoDia = prefs.getString(KEY_LAST_DAY, "")
        var racha = prefs.getInt(KEY_RACHA, 0)

        if (ultimoDia !=hoy){
            racha++
            prefs.edit{
                putInt(KEY_RACHA, racha)
                putString(KEY_LAST_DAY, hoy)
            }
        }
    }

    fun getRacha(context: Context): Int{
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_RACHA, 0)
    }

    fun BorrarProgreso(context: Context){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            clear()
        }
    }
}

data class Ecotarea(
    val nombre: String,
    val puntos: Int,
    var completada: Boolean = false
)