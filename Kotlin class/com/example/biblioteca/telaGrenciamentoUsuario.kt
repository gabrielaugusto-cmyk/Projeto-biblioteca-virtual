package com.example.biblioteca

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class telaGrenciamentoUsuario : AppCompatActivity() {
    private lateinit var recycler_usuarios: RecyclerView
    private lateinit var adapter_usuarios: MeuAdapterUsuario
    private val lista_usuarios = mutableListOf<Usuario>()
    private val fb = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_gerenciamento_usuario_activity)

        recycler_usuarios = findViewById(R.id.recycler_usuarios)

        adapter_usuarios = MeuAdapterUsuario(lista_usuarios) { usuario, posicao ->

            lista_usuarios.removeAt(posicao)
            adapter_usuarios.notifyItemRemoved(posicao)
        }

        recycler_usuarios.layoutManager = LinearLayoutManager(this)
        recycler_usuarios.adapter = adapter_usuarios

        carregar_usuarios()
    }

    private fun carregar_usuarios() {

        fb.collection("Usuário")
            .get()
            .addOnSuccessListener { resultado ->

                lista_usuarios.clear()

                for (doc in resultado) {

                    val email = doc.getString("email") ?: ""

                    lista_usuarios.add(Usuario(email))
                }

                adapter_usuarios.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.e("DEBUG", "Erro ao carregar usuários")
            }
    }
        }