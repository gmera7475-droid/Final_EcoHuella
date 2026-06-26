package com.example.ecohuella

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class RecordatorioWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val prefs = applicationContext.getSharedPreferences("eco_huella_prefs", Context.MODE_PRIVATE)
        val activado = prefs.getBoolean("notificaciones_activas", false)

        if (activado) {
            mostrarNotificacion()
        }

        return Result.success()
    }

    private fun mostrarNotificacion(){
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "eco_alertas"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "EcoAlertas", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Recordatorio de EcoHuella")
            .setContentText("¡Cumplir con las tareas de hoy!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        manager.notify(1, builder.build())
    }


}