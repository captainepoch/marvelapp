package com.adolfo.marvel.features.character.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.marvel.databinding.ItemCharacterBinding
import com.adolfo.marvel.features.character.view.adapter.CharactersListAdapter.CharacterViewHolder

class CharactersListAdapter : ListAdapter<CharacterView, CharacterViewHolder>(CharacterDiff) {

    internal var characterListener: (CharacterView) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), characterListener)
    }

    inner class CharacterViewHolder(private val item: ItemCharacterBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(character: CharacterView, listener: (CharacterView) -> Unit) {
            item.liHeroe.inflateItem(
                character.name,
                character.thumbnail
            )

            item.liHeroe.setOnClickListener {
                listener(character)
            }
        }
    }

    private object CharacterDiff : DiffUtil.ItemCallback<CharacterView>() {
        override fun areItemsTheSame(oldItem: CharacterView, newItem: CharacterView): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(oldItem: CharacterView, newItem: CharacterView): Boolean {
            return (oldItem.id == oldItem.id)
        }
    }
}
