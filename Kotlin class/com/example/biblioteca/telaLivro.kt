package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class telaLivro : AppCompatActivity() {
    lateinit var btnFavoritar: ImageView
    lateinit var btnEmprestar: Button
    lateinit var btn_ler_online: Button
    lateinit var txtTitulo: TextView
    lateinit var txtAutor: TextView
    lateinit var fb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_livro_activity)

        btnFavoritar = findViewById(R.id.btnFavoritar)
        btnEmprestar = findViewById(R.id.btnEmprestar)
        btn_ler_online = findViewById(R.id.btn_ler_online)

        txtTitulo = findViewById(R.id.txtTitulo)
        txtAutor = findViewById(R.id.txtAutor)

        fb = FirebaseFirestore.getInstance()

        val titulo = intent.getStringExtra("titulo")
        val autor = intent.getStringExtra("autor")
        val disponibilidade = intent.getStringExtra("status")
        val email = intent.getStringExtra("email")

        txtTitulo.text = titulo
        txtAutor.text = autor

        val dados_favorito = hashMapOf(

            "titulo" to titulo,

            "autor" to autor,

            "status" to disponibilidade

        )

        btnFavoritar.setOnClickListener {
            fb.collection("Favorito").add(dados_favorito)

        }

        val calendario_emprestimo = Calendar.getInstance()

        val calendario_devolucao = Calendar.getInstance()

        calendario_devolucao.add(
            Calendar.DAY_OF_MONTH,
            -5
        )

        val formatador = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale("pt", "BR")
        )

        val data_emprestimo =
            formatador.format(
                calendario_emprestimo.time
            )

        val data_devolucao =
            formatador.format(
                calendario_devolucao.time
            )

        btnEmprestar.setOnClickListener {

            fb.collection("Emprestimo")
                .whereEqualTo("email", email)
                .whereEqualTo("status", "Emprestado")
                .get()
                .addOnSuccessListener { resultado ->

                    if (resultado.size() >= 3) {

                        Toast.makeText(
                            this,
                            "Limite de empréstimos atingido",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        val dados_emprestimo = hashMapOf(

                            "email" to email,

                            "titulo_livro" to titulo,

                            "data_emprestimo" to data_emprestimo.toString(),

                            "data_devolucao" to data_devolucao.toString(),

                            "status" to "Emprestado"
                        )

                        fb.collection("Emprestimo")
                            .add(dados_emprestimo)
                            .addOnSuccessListener {

                                val intent = Intent(this, telaConfirmacaoEmprestimo::class.java)
                                intent.putExtra("data_devolucao", data_devolucao)
                                intent.putExtra("data_emprestimo", data_emprestimo)
                                intent.putExtra("titulo", titulo)
                                intent.putExtra("autor", autor)
                                startActivity(intent)
                            }
                    }
                }
        }

        btn_ler_online.setOnClickListener {

            val intent = Intent(this, telaCriarMeta::class.java)
            startActivity(intent)

        }
    }
}