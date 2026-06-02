package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class telaPainelAdministrador : AppCompatActivity() {

    lateinit var btnCadastrarLivro: Button

    lateinit var btnGerenciarEmprestimos: Button
    lateinit var btnExcluirUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_painel_administrador_activity)

        btnCadastrarLivro = findViewById(R.id.btnCadastrarLivro)

        btnGerenciarEmprestimos = findViewById(R.id.btnEmprestimo)

        btnCadastrarLivro.setOnClickListener {

            val intent = Intent(this, telaCadastroLivro::class.java)
            startActivity(intent)

        }

        btnGerenciarEmprestimos.setOnClickListener {

            val intent = Intent(this, telaGerenciamentoEmprestimo::class.java)
            startActivity(intent)
        }



        btnExcluirUsuario = findViewById(R.id.btnExcluirUsuario)

        btnExcluirUsuario.setOnClickListener {

            val intent = Intent(this, telaGrenciamentoUsuario::class.java)
            startActivity(intent)

        }

    }
}
