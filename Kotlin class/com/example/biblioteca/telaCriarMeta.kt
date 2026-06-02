package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class telaCriarMeta : AppCompatActivity() {
    lateinit var btn_criar_meta: Button

    lateinit var edt_tempo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_criar_meta_activity)

        btn_criar_meta = findViewById(R.id.btn_criar_meta)

        edt_tempo = findViewById(R.id.edt_tempo)

        btn_criar_meta.setOnClickListener {
            val intent = Intent(this, telaLeitura::class.java)
            intent.putExtra("tempo", edt_tempo.text.toString().toLong())
            startActivity(intent)
        }

    }
}