package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class telaPrincipal : AppCompatActivity() {
    val lista_Livros = mutableListOf<Livro>()
    lateinit var barPesquisa: LinearLayout
    lateinit var recyclerView: RecyclerView
    lateinit var bottomNav: BottomNavigationView
    lateinit var tela_busca: Intent
    lateinit var tela_favorito: Intent
    lateinit var tela_historico_multa: Intent
    lateinit var tela_ia: Intent

    lateinit var fb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal_activity)

        barPesquisa = findViewById(R.id.input_pesquisa)

        recyclerView = findViewById(R.id.recyclerView)

        bottomNav = findViewById(R.id.bottonmenu)

        fb = FirebaseFirestore.getInstance()

        val email = intent.getStringExtra("email")

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = MeuAdapter(lista_Livros, email ?: "")

        tela_busca = Intent(this, telaBusca::class.java)

        tela_favorito = Intent(this, telaMeusFavoritos::class.java)

        tela_historico_multa = Intent(this, telaHistoricoMulta::class.java)

        tela_ia = Intent(this, telaInteligenciaArtificial::class.java)

        fb.collection("Livro").get().addOnSuccessListener { resultado ->

            lista_Livros.clear()

            for (documento in resultado) {
                val livro = documento.toObject(Livro::class.java)
                lista_Livros.add(livro)
            }

            recyclerView.adapter?.notifyDataSetChanged()
        }

        barPesquisa.setOnClickListener {
            tela_busca.putExtra("email", email)
            startActivity(tela_busca)
        }

        bottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.favoritos -> {
                    startActivity(tela_favorito)
                    true
                }

                R.id.i_a -> {

                    startActivity(tela_ia)
                    true
                }

                R.id.historico_multa -> {
                    startActivity(tela_historico_multa)
                    tela_historico_multa.putExtra("email", email)
                    true
                }

                else -> false
            }
        }
    }

}

