package com.example.biblioteca

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class telaLeitura: AppCompatActivity() {
    lateinit var txt_tempo: TextView
    lateinit var txt_titulo: TextView
    lateinit var txt_conteudo: TextView
    lateinit var txt_autor: TextView
    lateinit var btn_proximo: ImageView
    lateinit var btn_voltar: ImageView
    lateinit var fb: FirebaseFirestore
    lateinit var txt_numero_pagina: TextView
    fun temporizador() {

        val min = intent.getLongExtra("tempo", 0L)

        val tempo_total = min * 60 * 1000

        object : CountDownTimer(tempo_total, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val segundos = millisUntilFinished / 1000

                val minutos = segundos / 60

                val segundos_restantes = segundos % 60

                txt_tempo.text = String.format("%02d:%02d", minutos, segundos_restantes)


            }

            override fun onFinish() {

                Toast.makeText(this@telaLeitura, "Meta Concluida", Toast.LENGTH_SHORT).show()

            }

        }.start()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_leitura_activity)

        txt_tempo = findViewById(R.id.txt_tempo)

        txt_titulo = findViewById(R.id.txt_titulo)

        txt_autor = findViewById(R.id.txt_autor)

        txt_conteudo = findViewById(R.id.txt_conteudo)

        fb = FirebaseFirestore.getInstance()

        btn_proximo = findViewById(R.id.btn_pagina_proxima)

        btn_voltar = findViewById(R.id.btn_pagina_anterior)

        txt_numero_pagina = findViewById(R.id.txt_numero_pagina)

        var paginas = listOf<String>()

        var pagina_atual = 0

        fb.collection("Livro").get()
            .addOnSuccessListener { resultado ->

                if (!resultado.isEmpty) {

                    val documento = resultado.documents[0]

                    txt_titulo.text =
                        documento.getString("titulo")

                    txt_autor.text =
                        documento.getString("autor")

                    val historia = documento.getString("historia") ?: ""

                    paginas = historia.chunked(400)

                    pagina_atual = 0

                    txt_conteudo.text =
                        documento.getString("historia") ?: ""



                }

            }



        btn_proximo.setOnClickListener {

            if (pagina_atual < paginas.size - 1) {

                pagina_atual++

                txt_conteudo.text = paginas[pagina_atual]

                txt_conteudo.scrollTo(0, 0)

                txt_numero_pagina.text = pagina_atual.toString()

            } else {

                Toast.makeText(this, "fim do livro", Toast.LENGTH_SHORT).show()

            }
        }

        btn_voltar.setOnClickListener {

            if (pagina_atual > 0) {

                pagina_atual--

                txt_conteudo.text = paginas[pagina_atual]

                txt_conteudo.scrollTo(0, 0)

                txt_numero_pagina.text = pagina_atual.toString()

            }

        }



        temporizador()

    }

}

