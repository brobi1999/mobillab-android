package hu.bme.aut.mobillab_android.ui.list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.mobillab_android.databinding.PokeItemBinding
import hu.bme.aut.mobillab_android.model.ui.Poke
import hu.bme.aut.mobillab_android.util.MyUtil

class PokeAdapter: ListAdapter<Poke, PokeAdapter.PokeViewHolder>(
    PokeComparator
) {

    private var listener: PokeListListener? = null

    fun setListener(newListener: PokeListListener){
        listener = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeAdapter.PokeViewHolder {
        val binding = PokeItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val poke = getItem(position)

        holder.binding.btnDelete.setOnClickListener {
            listener?.deletePoke(poke.id)
            true
        }

        holder.binding.tvPokeName.text = poke?.name.toString().replaceFirstChar { it.uppercase() }

        MyUtil.configureStatLayout(holder.binding.statLayout, poke?.hp, poke?.def, poke?.atk, poke?.sp, holder.binding.root.context)

        Glide.with(holder.binding.root.context).load(poke?.front_default).into(holder.binding.ivPoke)

        MyUtil.configureLabelTypeLayout(holder.binding.labelType, poke?.typeSlotOne.toString(), holder.binding.root.context)

        holder.binding.root.setOnClickListener {
            poke?.let { it1 -> listener?.onPokeClicked(it1) }
        }

        holder.binding.cbIsFav.setOnCheckedChangeListener(null)
        holder.binding.cbIsFav.isChecked = poke?.isFavourite == true

        holder.binding.cbIsFav.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                listener?.addPokeToFavourites(poke.id)
            }
            else{
                listener?.removePokeFromFavourites(poke.id)
            }

        }
    }



    inner class PokeViewHolder(val binding: PokeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    interface PokeListListener {
        fun deletePoke(id: Int)
        fun addPokeToFavourites(id: Int)
        fun removePokeFromFavourites(id: Int)
        fun onPokeClicked(poke: Poke)
    }


    override fun submitList(list: List<Poke>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}

object PokeComparator : DiffUtil.ItemCallback<Poke>() {

    override fun areItemsTheSame(oldItem: Poke, newItem: Poke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Poke, newItem: Poke): Boolean {
        return oldItem == newItem
    }
}