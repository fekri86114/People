package fekri.com.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fekri.com.databinding.ActivityMainBinding
import fekri.com.databinding.DialogAddNewItemBinding
import fekri.com.databinding.DialogDeleteItemBinding
import fekri.com.databinding.DialogUpdateItemBinding
import fekri.com.ux.adapter.PeopleAdapter
import fekri.com.ux.data.People

class MainActivity : AppCompatActivity(), PeopleAdapter.PeopleEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: PeopleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peopleLis = arrayListOf(
            People(
                "https://avatars.githubusercontent.com/u/92860582?v=4",
                "Mohammadreza Fekri",
                "0914 703 1237"
            ),
            People(
                "https://img.a.transfermarkt.technology/portrait/big/28003-1631171950.jpg?lm=1",
                "Messi",
                "1234 567 8900"
            ),
            People(
                "https://upload.wikimedia.org/wikipedia/commons/8/8c/Cristiano_Ronaldo_2018.jpg",
                "Ronaldo",
                "1234 567 8901"
            ),
            People(
                "https://1000logos.net/wp-content/uploads/2016/10/Android-Logo.png",
                "Android",
                "1234 567 8902"
            ),
            People(
                "https://avatars.githubusercontent.com/u/9919?s=200&v=4",
                "Github",
                "1234 567 8903"
            ),
            People(
                "https://avatars.githubusercontent.com/u/82592?s=200&v=4",
                "Square",
                "1234 567 8904"
            ),
            People(
                "https://avatars.githubusercontent.com/u/93353203?s=200&v=4",
                "Someone",
                "1234 567 8905"
            ),
            People(
                "https://honkajokioy.fi/wp-content/uploads/2022/02/person-icon.png",
                "Mr.Person",
                "1234 567 8906"
            ),
            People(
                "chrome://branding/content/about-logo.png",
                "Firefox",
                "1234 567 8907"
            ),
            People(
                "https://avatars.githubusercontent.com/u/92860582?v=4",
                "Mohammadreza Fekri",
                "0914 703 1237"
            ),
            People(
                "https://img.a.transfermarkt.technology/portrait/big/28003-1631171950.jpg?lm=1",
                "Messi",
                "1234 567 8900"
            ),
            People(
                "https://upload.wikimedia.org/wikipedia/commons/8/8c/Cristiano_Ronaldo_2018.jpg",
                "Ronaldo",
                "1234 567 8901"
            ),
            People(
                "https://1000logos.net/wp-content/uploads/2016/10/Android-Logo.png",
                "Android",
                "1234 567 8902"
            ),
            People(
                "https://avatars.githubusercontent.com/u/9919?s=200&v=4",
                "Github",
                "1234 567 8903"
            ),
            People(
                "https://avatars.githubusercontent.com/u/82592?s=200&v=4",
                "Square",
                "1234 567 8904"
            ),
            People(
                "https://avatars.githubusercontent.com/u/93353203?s=200&v=4",
                "Someone",
                "1234 567 8905"
            ),
            People(
                "https://honkajokioy.fi/wp-content/uploads/2022/02/person-icon.png",
                "Mr.Person",
                "1234 567 8906"
            ),
            People(
                "chrome://branding/content/about-logo.png",
                "Firefox",
                "1234 567 8907"
            ),
        )
        mAdapter = PeopleAdapter(peopleLis.clone() as ArrayList<People>, this)
        binding.recyclerviewMain.adapter = mAdapter
        binding.recyclerviewMain.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // add new person:
        binding.btnAddNewPerson.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)

            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()

            dialogBinding.dialogBtnDone.setOnClickListener {

                if ( // check dialog input is not empty, then do wht you wanna do
                    dialogBinding.dialogEdtPersonNickName.text!!.isNotEmpty() &&
                    dialogBinding.dialogEdtPersonPhoneNumber.text!!.isNotEmpty()
                ) {

                    val txtName = dialogBinding.dialogEdtPersonNickName.text.toString()
                    val txtPhoneNumber = dialogBinding.dialogEdtPersonPhoneNumber.text.toString()

                    val randomForUrl = (1..18).random()
                    val urlPic = peopleLis[randomForUrl].urlImage

                    val newPerson = People(urlPic, txtName, txtPhoneNumber)
                    mAdapter.addNewPerson(newPerson)

                    dialog.dismiss() // close dialog

                    binding.recyclerviewMain.scrollToPosition(0) // scroll up

                } else {
                    Toast.makeText(this, "Please, fill out the blanks!", Toast.LENGTH_SHORT).show()
                }

            }

        }

        binding.edtSearch.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {
                // filter data:

                val cloneList = peopleLis.clone() as ArrayList<People>

                val filteredList = cloneList.filter { peopleItem ->
                    peopleItem.nickname.contains(editTextInput)
                }

                mAdapter.setData( ArrayList( filteredList ) )

            } else {
                // show data:
                mAdapter.setData(peopleLis.clone() as ArrayList<People>)

            }

        }

    }

    override fun onPersonClicked(person: People, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogUpdateItemBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialogUpdateItemBinding.dialogEdtPersonNickName.setText(person.nickname)
        dialogUpdateItemBinding.dialogEdtPersonPhoneNumber.setText(person.phoneNumber)

        dialog.setView(dialogUpdateItemBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogUpdateItemBinding.dialogUpdateBtnConfirm.setOnClickListener {
            // update item:

            if (dialogUpdateItemBinding.dialogEdtPersonNickName.text!!.isNotEmpty() &&
                dialogUpdateItemBinding.dialogEdtPersonPhoneNumber.text!!.isNotEmpty()
            ) {
                // create mew person to add to the recyclerview
                val txtName = dialogUpdateItemBinding.dialogEdtPersonNickName.text.toString()
                val txtPhoneNumber =
                    dialogUpdateItemBinding.dialogEdtPersonPhoneNumber.text.toString()

                val newPerson = People(person.urlImage, txtName, txtPhoneNumber)

                mAdapter.updatePerson(newPerson, position)

                dialog.dismiss()

            } else {
                Toast.makeText(this, "Please, fill out the blanks", Toast.LENGTH_SHORT).show()
            }

        }

        dialogUpdateItemBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onPersonLongClicked(person: People, position: Int) {
        val dialog = AlertDialog.Builder(this).create()

        val dialogDeleteItemBinding = DialogDeleteItemBinding.inflate(layoutInflater)

        dialog.setView(dialogDeleteItemBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteItemBinding.dialogDeleteBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteItemBinding.dialogDeleteBtnDone.setOnClickListener {
            dialog.dismiss()
            mAdapter.removePerson(person, position)
        }

    }

}