package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class telaLogin : AppCompatActivity() {

    lateinit var edt_email: EditText
    lateinit var edt_senha: EditText
    lateinit var btn_entrar: Button
    lateinit var txtCadastro: TextView

    lateinit var tela_cadastro: Intent

    lateinit var tela_administrador: Intent
    lateinit var tela_principal: Intent
    lateinit var fb: FirebaseFirestore


    lateinit var tela_livro: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_login_activity)

        edt_email = findViewById(R.id.edt_email)

        edt_senha = findViewById(R.id.edt_senha)

        btn_entrar = findViewById(R.id.button)

        txtCadastro = findViewById(R.id.txt_cadastro)

        fb = FirebaseFirestore.getInstance()

        tela_livro = Intent(this, telaLivro::class.java)

        tela_principal = Intent(this, telaPrincipal::class.java)

        tela_cadastro = Intent(this, telaCadastro::class.java)

        tela_administrador = Intent(this, telaPainelAdministrador::class.java)


        btn_entrar.setOnClickListener {

            Log.d("funciona", "entrou")

            val email = edt_email.text.toString().trim()
            val senha = edt_senha.text.toString().trim()

            fb.collection("Administrador")
                .whereEqualTo("email", email)
                .whereEqualTo("senha", senha)
                .get()
                .addOnSuccessListener { documento_admin ->

                    if (!documento_admin.isEmpty) {

                        val intent_admin = Intent(this, telaPainelAdministrador::class.java)
                        startActivity(intent_admin)

                    } else {

                        fb.collection("Usuário")
                            .whereEqualTo("email", email)
                            .whereEqualTo("senha", senha)
                            .get()
                            .addOnSuccessListener { resultado ->

                                if (!resultado.isEmpty) {

                                    val intent_usuario = Intent(this, telaPrincipal::class.java)
                                    intent_usuario.putExtra("email", email)
                                    intent_usuario.putExtra("tipo", "usuario")
                                    startActivity(intent_usuario)

                                } else {
                                    edt_email.error = "Login inválido"
                                    edt_senha.error = "Verifique seus dados"
                                }
                            }
                            .addOnFailureListener {
                                edt_email.error = "Erro de conexão"
                            }
                    }
                }
        }

        txtCadastro.setOnClickListener {startActivity(tela_cadastro)}

    }

}
