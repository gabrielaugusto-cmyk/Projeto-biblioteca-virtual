package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class telaConfirmacaoEmprestimo : AppCompatActivity() {

    lateinit var txt_titulo: TextView
    lateinit var txt_autor: TextView
    lateinit var fb: FirebaseFirestore
    lateinit var btn_confirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_confirmacao_emprestimo_activity)

        btn_confirmar = findViewById(R.id.buttonConfirmar)

        txt_titulo = findViewById(R.id.txt_titulo)

        txt_autor = findViewById(R.id.txt_autor)

        fb = FirebaseFirestore.getInstance()

        val data_emprestimo = intent.getStringExtra("data_emprestimo")

        val data_devolucao = intent.getStringExtra("data_devolucao")

        fb.collection("Livro").get()
            .addOnSuccessListener { resultado ->

                if (!resultado.isEmpty) {

                    val documento = resultado.documents[0]

                    txt_titulo.text =
                        documento.getString("titulo")

                    txt_autor.text =
                        documento.getString("autor")

                }

            }


        btn_confirmar.setOnClickListener {

            val intent = Intent(this, telaRetirarLivro::class.java)
            intent.putExtra("data_emprestimo", data_emprestimo)
            intent.putExtra("data_devolucao", data_devolucao)
            startActivity(intent)

        }





    }
}