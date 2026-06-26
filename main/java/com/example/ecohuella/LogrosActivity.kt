package com.example.ecohuella

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class LogrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logros)

        val switchRecordatorio: SwitchCompat = findViewById(R.id.SwitchRecordatorio)
        val btnBorrarProgreso: Button = findViewById(R.id.btnBorrarProgreso)

        val prefs = getSharedPreferences("eco_huella_prefs", Context.MODE_PRIVATE)

        switchRecordatorio.isChecked = prefs.getBoolean("notificaciones_activas", false)

        switchRecordatorio.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notificaciones_activas", isChecked).apply()
            if (isChecked) {
                Toast.makeText(this, "Recordatorio activado: 'cumplir con las tareas de hoy'", Toast.LENGTH_SHORT).show()
                        } else {
                Toast.makeText(this, "Recordatorio desactivado", Toast.LENGTH_SHORT).show()
            }
        }

        btnBorrarProgreso.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Borrar Progreso")
                .setMessage("¿Estás seguro de que quieres borrar el progreso de tu EcoHuella?")
                .setPositiveButton("Sí") { _, _ ->

                    DataHelper.BorrarProgreso(this)

                    switchRecordatorio.isChecked = false

                    Toast.makeText(this, "Progreso borrado con éxito", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
        }

    }
}
