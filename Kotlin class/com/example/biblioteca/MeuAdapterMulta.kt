package com.example.biblioteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MeuAdapterMulta(

    private val multas: List<Multa>
) : RecyclerView.Adapter<MeuAdapterMulta.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_multa, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = multas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val multa = multas[position]

        holder.nome_livro.text = multa.titulo_livro
        holder.dias_atraso.text = multa.dias_atraso.toString()
        holder.valor_multa.text = "R$ %.2f".format(multa.valor_multa)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nome_livro: TextView = itemView.findViewById(R.id.txt_nome_livro)
        val dias_atraso: TextView = itemView.findViewById(R.id.txt_dias_atraso)
        val valor_multa: TextView = itemView.findViewById(R.id.txt_valor_multa)
    }
}