package com.adolfo.marvel.features.character.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.marvel.databinding.ItemCharacterBinding
import com.adolfo.marvel.features.character.view.adapter.CharactersListAdapter.CharacterViewHolder
import kotlin.properties.Delegates

class CharactersListAdapter : RecyclerView.Adapter<CharacterViewHolder>() {

    internal var items: List<CharacterView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CharacterViewHolder(private val item: ItemCharacterBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(character: CharacterView) {
            item.liHeroe.setName(character.name)
        }
    }
}
