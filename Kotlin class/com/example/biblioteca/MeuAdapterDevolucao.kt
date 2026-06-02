package com.example.biblioteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MeuAdapterDevolucao(

    private val lista_emprestimos: MutableList<Emprestimo>,
    private val on_devolver_click: (Emprestimo) -> Unit
) : RecyclerView.Adapter<MeuAdapterDevolucao.ViewHolder>() {

    class ViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        val text_livro: TextView = item_view.findViewById(R.id.text_livro)
        val text_usuario: TextView = item_view.findViewById(R.id.text_usuario)
        val botao_devolver: Button = item_view.findViewById(R.id.botao_devolver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emprestimo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val emprestimo_atual = lista_emprestimos[position]

        holder.text_livro.text = emprestimo_atual.titulo_livro
        holder.text_usuario.text = emprestimo_atual.email

        holder.botao_devolver.setOnClickListener {

            val posicao = holder.bindingAdapterPosition

            if (posicao != RecyclerView.NO_POSITION) {
                on_devolver_click(lista_emprestimos[posicao])
            }
        }
    }

    override fun getItemCount(): Int {
        return lista_emprestimos.size
    }

    fun remover_item(posicao: Int) {
        if (posicao in lista_emprestimos.indices) {
            lista_emprestimos.removeAt(posicao)
            notifyItemRemoved(posicao)
        }
    }
}