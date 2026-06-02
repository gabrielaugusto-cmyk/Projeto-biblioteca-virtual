package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class telaMeusFavoritos : AppCompatActivity() {

    val lista_favoritos = mutableListOf<Livro>()
    lateinit var recyclerView: RecyclerView
    lateinit var seta_voltar: ImageView

    lateinit var fb: FirebaseFirestore

    lateinit var bottomNav: BottomNavigationView
    lateinit var tela_favorito: Intent
    lateinit var tela_historico_multa: Intent
    lateinit var tela_ia: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_meus_favoritos_activity)

        seta_voltar = findViewById(R.id.imageView3)

        recyclerView = findViewById(R.id.recyclerView)

        bottomNav = findViewById(R.id.bottonmenu)

        fb = FirebaseFirestore.getInstance()

        recyclerView.layoutManager =
            GridLayoutManager(this, 1)

        recyclerView.adapter =
            MeuAdapter(lista_favoritos, null)

        fb.collection("Favorito")
            .get()
            .addOnSuccessListener { resultado ->

                lista_favoritos.clear()

                for (documento in resultado) {

                    val livro = Livro(

                        titulo =
                            documento.getString("titulo") ?: "",

                        autor =
                            documento.getString("autor") ?: "",

                        status =
                            documento.getString("status") ?: ""
                    )

                    lista_favoritos.add(livro)
                }

                recyclerView.adapter?.notifyDataSetChanged()
            }

        seta_voltar.setOnClickListener {

            val intent =
                Intent(
                    this,
                    telaPrincipal::class.java
                )

            startActivity(intent)
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
                    true
                }

                else -> false
            }
        }


    }
}