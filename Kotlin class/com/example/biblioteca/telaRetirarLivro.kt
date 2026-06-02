package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class telaRetirarLivro : AppCompatActivity() {

    lateinit var btn_cancelar: Button
    lateinit var btn_ok: Button
    lateinit var txt_data_retirada: TextView
    lateinit var txt_dia_entrega: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_retirar_livro_activity)

        btn_cancelar = findViewById(R.id.button6)

        btn_ok = findViewById(R.id.button7)

        txt_data_retirada =
            findViewById(R.id.txt_data_retirada)

        txt_dia_entrega =
            findViewById(R.id.txt_dia_entrega)

        val data_emprestimo =
            intent.getStringExtra(
                "data_emprestimo"
            )

        val data_devolucao =
            intent.getStringExtra(
                "data_devolucao"
            )

        txt_data_retirada.text =
            data_emprestimo

        txt_dia_entrega.text =
            data_devolucao

        btn_cancelar.setOnClickListener {

            val intent = Intent(
                this,
                telaLivro::class.java
            )

            startActivity(intent)
        }

        btn_ok.setOnClickListener {

            val intent = Intent(
                this,
                telaPrincipal::class.java
            )

            startActivity(intent)
        }
    }
}