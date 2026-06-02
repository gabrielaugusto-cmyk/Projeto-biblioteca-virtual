package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class telaCadastroLivro : AppCompatActivity() {
    lateinit var edt_nome: EditText
    lateinit var edt_autor: EditText
    lateinit var btn_cancelar: Button
    lateinit var btn_cadastrar: Button

    lateinit var fb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_cadastro_livro_activity)

        edt_nome = findViewById(R.id.edt_nome)

        edt_autor = findViewById(R.id.edt_autor)

        btn_cancelar = findViewById(R.id.btn_cancelar)

        btn_cadastrar = findViewById(R.id.btn_cadastrar)

        fb = FirebaseFirestore.getInstance()

        btn_cancelar.setOnClickListener { finish() }

        btn_cadastrar.setOnClickListener {

            val nome = edt_nome.text.toString().trim()

            val autor = edt_autor.text.toString().trim()

            if (nome.isEmpty() || autor.isEmpty()) {

                Toast.makeText(
                    this,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val dados_livro = hashMapOf(

                "titulo" to nome,

                "autor" to autor,

                "status" to "Disponível"
            )

            fb.collection("Livro")
                .add(dados_livro)
                .addOnSuccessListener {

                    Toast.makeText(this, "Livro cadastrado com sucesso", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, telaPainelAdministrador::class.java)

                    startActivity(intent)

                }
                .addOnFailureListener {

                    Toast.makeText(this, "Erro ao cadastrar livro", Toast.LENGTH_SHORT).show()
                }
        }
    }
}