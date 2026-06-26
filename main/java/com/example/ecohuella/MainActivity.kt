package com.example.ecohuella

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var tvPuntos: TextView
    private lateinit var contenedor: LinearLayout
    private lateinit var btnGuardar: Button

    private var puntosAcumuladosHoy = 0

    private val listaTareas = listOf(
        Ecotarea("No usar pitillo", 10),
        Ecotarea("Usar bolsa de tela", 15),
        Ecotarea("Apagar luces innecesarias", 5),
        Ecotarea("Ducha de menos de 5 min", 20)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvPuntos = findViewById(R.id.tvPuntos)
        contenedor = findViewById(R.id.contenedorTareas)
        btnGuardar = findViewById(R.id.btnGuardar)

        actualizarPuntosEnPantalla()
        cargarTareas()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val solicitudRecordatorio = PeriodicWorkRequestBuilder<RecordatorioWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(solicitudRecordatorio)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "EcoHuella"

        tvPuntos = findViewById(R.id.tvPuntos)
        contenedor = findViewById(R.id.contenedorTareas)
        btnGuardar = findViewById(R.id.btnGuardar)


        btnGuardar.setOnClickListener {
            if (puntosAcumuladosHoy > 0) {
                val sdf = SimpleDateFormat("EEEE", Locale("es", "ES"))
                val diaActual = sdf.format(Calendar.getInstance().time)
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else
                            it.toString()
                    }
                DataHelper.saveDayPoints(this, puntosAcumuladosHoy, diaActual, updateTotal = true)
                DataHelper.actualizarRacha(this, diaActual)

                actualizarPuntosEnPantalla()
                Toast.makeText(this, "Progreso de $diaActual guardado con éxito!", Toast.LENGTH_SHORT).show()

                puntosAcumuladosHoy = 0
                btnGuardar.isEnabled = false
            } else {
                Toast.makeText(this, "Marcar al menos una tarea primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarTareas() {
        for (tarea in listaTareas) {
            val checkBox = CheckBox(this)
            checkBox.text = "${tarea.nombre} (+${tarea.puntos} pts)"
            checkBox.textSize = 18f
            checkBox.setPadding(0, 20, 0, 20)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    puntosAcumuladosHoy += tarea.puntos
                    //DataHelper.saveDayPoints(this, tarea.puntos)
                    //actualizarPuntosEnPantalla()
                    val puntosTotalesActuales = DataHelper.getPoints(this)
                    tvPuntos.text= "Puntos: $puntosTotalesActuales + puntosAcumuladosHoy"


                    checkBox.isEnabled = false
                }
            }
            contenedor.addView(checkBox)
        }
    }

    private fun actualizarPuntosEnPantalla() {
        val puntosTotales = DataHelper.getPoints(this)
        tvPuntos.text = "Puntos: $puntosTotales"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_navegacion, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.nav_estadisticas -> {
                startActivity(Intent(this, EstadisticasActivity::class.java))
                true
            }
            R.id.nav_logros -> {
                startActivity(Intent(this, LogrosActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
