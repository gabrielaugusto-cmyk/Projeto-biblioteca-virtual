package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class telaBusca: AppCompatActivity() {

    private val lista_livros = mutableListOf<Livro>()
    private lateinit var btn_voltar: ImageView
    private lateinit var edt_busca: EditText
    private lateinit var limpar_campo: ImageView
    private lateinit var recycler_view: RecyclerView
    lateinit var layout_sem_resultado: LinearLayout
    private lateinit var fb: FirebaseFirestore
    private lateinit var adapter: MeuAdapter

    private fun buscar_livro() {

        val titulo = edt_busca.text.toString().trim()

        if (titulo.isEmpty()) {
            lista_livros.clear()
            adapter.notifyDataSetChanged()
            return
        }

        fb.collection("Livro")
            .whereEqualTo("titulo", titulo)
            .get()
            .addOnSuccessListener { resultado ->

                lista_livros.clear()

                if (resultado.isEmpty) {

                    layout_sem_resultado.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE

                    return@addOnSuccessListener
                }

                recycler_view.visibility = View.VISIBLE
                layout_sem_resultado.visibility = View.GONE

                for (documento in resultado) {

                    val livro = documento.toObject(Livro::class.java)

                    lista_livros.add(livro)
                }
                adapter.notifyDataSetChanged()


            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_busca_activity)

        btn_voltar = findViewById(R.id.imageView19)
        edt_busca = findViewById(R.id.edt_busca)
        limpar_campo = findViewById(R.id.limpar_campo)
        recycler_view = findViewById(R.id.recyclerView)
        layout_sem_resultado = findViewById(R.id.layout_sem_resultado)

        val email = intent.getStringExtra("email")

        fb = FirebaseFirestore.getInstance()

        adapter = MeuAdapter(lista_livros, email)

        recycler_view.layoutManager = GridLayoutManager(this, 1)
        recycler_view.adapter = adapter

        limpar_campo.setOnClickListener { edt_busca.setText("") }

        layout_sem_resultado.visibility = View.VISIBLE

        edt_busca.setOnEditorActionListener { _, action_id, _ ->

            buscar_livro()
            true

        }

        btn_voltar.setOnClickListener { startActivity(Intent(this,telaPrincipal::class.java)) }
    }
}

