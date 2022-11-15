package fekri.com.ux.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fekri.com.databinding.ItemPeopleBinding
import fekri.com.ux.data.People

class PeopleAdapter(private val data: ArrayList<People>, private val peopleEvents: PeopleEvents) :
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    inner class PeopleViewHolder(private val binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(position: Int) {

            Glide
                .with(binding.root.context)
                .load(data[position].urlImage).transform()
                .into(binding.itemImgPerson)

            binding.itemTxtNamePerson.text = data[position].nickname
            binding.itemTxtPhoneNumberPerson.text = data[position].phoneNumber

            itemView.setOnClickListener {
                peopleEvents.onPersonClicked(data[adapterPosition], adapterPosition)
            }

            itemView.setOnLongClickListener {
                peopleEvents.onPersonLongClicked(data[adapterPosition], adapterPosition)

                true
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addNewPerson(newPerson: People) {
        // add person to list:
        data.add(0, newPerson) // '0' = first item in the list
        notifyItemInserted(0) // '0' = first item in the list
    }

    fun removePerson(oldPeople: People, position: Int) {
        // remove item from list:
        data.remove(oldPeople)
        notifyItemRemoved(position)
    }

    fun updatePerson(newPerson: People, position: Int) {
        // update item from list:
        data.set(position, newPerson)
        notifyItemChanged(position)
    }

    fun setData(newList: ArrayList<People>) {
        // set new data to list:

        data.clear()
        data.addAll( newList )

        notifyDataSetChanged() // make recyclerview update of changes from data

    }

    interface PeopleEvents {

        /*
        * How to create interface?
        * 1. create interface in adapter
        * 2. get n object of interface in args of adapter
        * 3. fill (call) objects of interface with your data data
        * 4. implementation in MainActivity
        * */

        fun onPersonClicked(person: People, position: Int)
        fun onPersonLongClicked(person: People, position: Int)
    }

}