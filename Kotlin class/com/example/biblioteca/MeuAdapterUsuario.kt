package com.example.biblioteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MeuAdapterUsuario(
    private val lista_usuarios: MutableList<Usuario>,
    private val on_excluir_click: (Usuario, Int) -> Unit
) : RecyclerView.Adapter<MeuAdapterUsuario.UsuarioViewHolder>() {

    class UsuarioViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        val text_email_usuario: TextView = item_view.findViewById(R.id.text_email_usuario)
        val botao_excluir_usuario: Button = item_view.findViewById(R.id.botao_excluir_usuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {

        val usuario_atual = lista_usuarios[position]

        holder.text_email_usuario.text = usuario_atual.email_usuario

        holder.botao_excluir_usuario.setOnClickListener {

            val posicao_atual = holder.bindingAdapterPosition

            if (posicao_atual != RecyclerView.NO_POSITION) {
                on_excluir_click(lista_usuarios[posicao_atual], posicao_atual)
            }
        }
    }

    override fun getItemCount(): Int {
        return lista_usuarios.size
    }

    fun remover_item(posicao: Int) {
        if (posicao in lista_usuarios.indices) {
            lista_usuarios.removeAt(posicao)
            notifyItemRemoved(posicao)
        }
    }
}