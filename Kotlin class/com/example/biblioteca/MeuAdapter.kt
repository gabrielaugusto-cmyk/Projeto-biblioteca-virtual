package com.example.biblioteca

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MeuAdapter (

    var livros: List<Livro>,

    val email: String?

) : RecyclerView.Adapter<MeuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int { return livros.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val livro = livros[position]

        holder.titulo.text = livro.titulo

        holder.autor.text = livro.autor

        holder.status.text = livro.status

        holder.itemLivro.setOnClickListener {
            val intent = Intent(holder.itemView.context, telaLivro::class.java)
            intent.putExtra("email", email)
            intent.putExtra("titulo", livro.titulo)
            intent.putExtra("autor", livro.autor)
            intent.putExtra("status", livro.status)

            holder.itemView.context.startActivity(intent)


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val autor: TextView = itemView.findViewById(R.id.txtAutor)
        val status: TextView = itemView.findViewById(R.id.txtStatus)
        val itemLivro: LinearLayout = itemView.findViewById(R.id.itemLivro)

    }

}