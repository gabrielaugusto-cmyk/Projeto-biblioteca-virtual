package com.example.biblioteca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class telaGerenciamentoEmprestimo : AppCompatActivity() {

    lateinit var recycler_view: RecyclerView
    lateinit var fb: FirebaseFirestore
    lateinit var adapter: MeuAdapterDevolucao

    private val lista_emprestimos = mutableListOf<Emprestimo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_gerenciamento_emprestimo_activity)

        fb = FirebaseFirestore.getInstance()

        recycler_view = findViewById(R.id.rvLivros)

        adapter = MeuAdapterDevolucao(lista_emprestimos) { emprestimo ->

            fb.collection("Emprestimo")
                .document(emprestimo.id)
                .update("status", "Devolvido")
                .addOnSuccessListener {

                    val posicao = lista_emprestimos.indexOfFirst {
                        it.id == emprestimo.id
                    }

                    if (posicao != -1) {
                        adapter.remover_item(posicao)
                    }
                }
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        carregar_emprestimos()
    }

    private fun carregar_emprestimos() {

        fb.collection("Emprestimo")
            .whereEqualTo("status", "Emprestado")
            .get()
            .addOnSuccessListener { resultado ->

                lista_emprestimos.clear()

                for (documento in resultado) {

                    val emprestimo = documento.toObject(Emprestimo::class.java)

                    emprestimo.id = documento.id  // 🔥 obrigatório

                    lista_emprestimos.add(emprestimo)
                }

                adapter.notifyDataSetChanged()
            }
    }
}
