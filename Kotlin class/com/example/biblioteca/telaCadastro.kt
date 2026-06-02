package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class telaCadastro : AppCompatActivity() {
    lateinit var edit_email: EditText
    lateinit var edit_senha: EditText
    lateinit var txt_login: TextView
    lateinit var btn_cadastrar: Button
    lateinit var msg_erro: TextView
    lateinit var fb: FirebaseFirestore
    lateinit var tela_pricipal: Intent
    lateinit var tela_login: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_cadastro_activity)

        fb = FirebaseFirestore.getInstance()

        edit_email = findViewById(R.id.edt_email)

        msg_erro = findViewById(R.id.mensagem_erro_email)

        edit_senha = findViewById(R.id.edt_senha)

        btn_cadastrar = findViewById(R.id.btn_cadastrar)

        txt_login = findViewById(R.id.txt_entrar)

        tela_pricipal = Intent(this, telaPrincipal::class.java)

        tela_login = Intent(this, telaLogin::class.java)

        btn_cadastrar.setOnClickListener {

            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()


            if (email.isEmpty()) {
                msg_erro.text = "Digite um e-mail"
                msg_erro.visibility = View.VISIBLE
                return@setOnClickListener
            }


            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                msg_erro.text = "E-mail inválido"
                msg_erro.visibility = View.VISIBLE
                return@setOnClickListener
            }


            if (senha.isEmpty()) {
                msg_erro.text = "Digite uma senha"
                msg_erro.visibility = View.VISIBLE
                return@setOnClickListener
            }


            if (senha.length < 8) {
                msg_erro.text = "A senha deve ter no mínimo 8 caracteres"
                msg_erro.visibility = View.VISIBLE
                return@setOnClickListener
            }

            msg_erro.visibility = View.GONE

            val dados = hashMapOf(
                "email" to email,
                "senha" to senha
            )

            fb.collection("Usuário")
                .add(dados)
                .addOnSuccessListener {
                    startActivity(tela_pricipal)
                }
                .addOnFailureListener {
                    msg_erro.text = "Erro ao cadastrar"
                    msg_erro.visibility = View.VISIBLE
                }
        }

        txt_login.setOnClickListener {startActivity(tela_login)}
    }
}