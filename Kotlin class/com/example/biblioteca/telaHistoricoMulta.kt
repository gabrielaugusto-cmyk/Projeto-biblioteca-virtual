package com.example.biblioteca

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class telaHistoricoMulta : AppCompatActivity() {

    private lateinit var recycler_view: RecyclerView
    private lateinit var txt_total_multas: TextView
    private lateinit var fb: FirebaseFirestore

    private val lista_multas = mutableListOf<Multa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_historico_multa_activity)

        recycler_view = findViewById(R.id.rv_multas)
        txt_total_multas = findViewById(R.id.txt_total_multas)

        fb = FirebaseFirestore.getInstance()

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = MeuAdapterMulta(lista_multas)

        val email = intent.getStringExtra("email")

        if (email.isNullOrEmpty()) return

        fb.collection("Emprestimo")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { resultado ->

                lista_multas.clear()

                val calendario_hoje = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val hoje = calendario_hoje.timeInMillis

                for (documento in resultado) {

                    val titulo = documento.getString("titulo_livro") ?: ""
                    val data_texto = documento.getString("data_devolucao") ?: ""

                    if (data_texto.isEmpty()) continue

                    val partes = data_texto.split("/")
                    if (partes.size != 3) continue

                    val ano = partes[0].toInt()
                    val mes = partes[1].toInt()
                    val dia = partes[2].toInt()

                    val calendario_devolucao = Calendar.getInstance().apply {
                        set(ano, mes - 1, dia, 0, 0, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    val atraso_millis =
                        hoje - calendario_devolucao.timeInMillis

                    val dias_atraso =
                        (atraso_millis / (1000L * 60L * 60L * 24L)).toInt()

                    if (dias_atraso > 0) {

                        val valor_multa = dias_atraso * 2.0


                        lista_multas.add(
                            Multa(
                                email = email,
                                titulo_livro = titulo,
                                dias_atraso = dias_atraso,
                                valor_multa = valor_multa
                            )
                        )


                        val doc_id = "${email}_${titulo}"

                        val dados_multa = hashMapOf(
                            "email" to email,
                            "titulo_livro" to titulo,
                            "dias_atraso" to dias_atraso,
                            "valor_multa" to valor_multa
                        )

                        fb.collection("Multa")
                            .document(doc_id)
                            .set(dados_multa)
                    }
                }

                recycler_view.adapter?.notifyDataSetChanged()


                var total = 0.0

                for (multa in lista_multas) {
                    total += multa.valor_multa
                }

                txt_total_multas.text =
                    "Total em Multas: R$ %.2f".format(total)
            }
    }
}
