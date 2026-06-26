package com.example.ecohuella

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EstadisticasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        val tvRacha: TextView = findViewById(R.id.tvRacha)
        val tvPuntosLunes: TextView = findViewById(R.id.tvPuntosLunes)
        val tvPuntosMartes: TextView = findViewById(R.id.tvPuntosMartes)
        val tvPuntosMiercoles: TextView = findViewById(R.id.tvPuntosMiercoles)
        val tvPuntosJueves: TextView = findViewById(R.id.tvPuntosJueves)
        val tvPuntosViernes: TextView = findViewById(R.id.tvPuntosViernes)

        val barLunes: ProgressBar = findViewById(R.id.barLunes)
        val barMartes: ProgressBar = findViewById(R.id.barMartes)
        val barMiercoles: ProgressBar = findViewById(R.id.barMiercoles)
        val barJueves: ProgressBar = findViewById(R.id.barJueves)
        val barViernes: ProgressBar = findViewById(R.id.barViernes)

        tvRacha.text = "Racha actual: ${DataHelper.getRacha(this)} dias!"

        val ptsLunes = DataHelper.getDayPoints(this, "Lunes")
        val ptsMartes = DataHelper.getDayPoints(this, "Martes")
        val ptsMiercoles = DataHelper.getDayPoints(this, "Miercoles")
        val ptsJueves = DataHelper.getDayPoints(this, "Jueves")
        val ptsViernes = DataHelper.getDayPoints(this, "Viernes")

        barLunes.progress = ptsLunes
        barMartes.progress = ptsMartes
        barMiercoles.progress = ptsMiercoles
        barJueves.progress = ptsJueves
        barViernes.progress = ptsViernes

        tvPuntosLunes.text = "Lunes: $ptsLunes pts"
        tvPuntosMartes.text = "Martes: $ptsMartes pts"
        tvPuntosMiercoles.text = "Miercoles: $ptsMiercoles pts"
        tvPuntosJueves.text = "Jueves: $ptsJueves pts"
        tvPuntosViernes.text = "Viernes: $ptsViernes pts"
    }
}
