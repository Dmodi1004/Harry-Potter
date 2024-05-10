package com.example.harrypotter

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.harrypotter.models.SpellsModel

class SpellDetailsDialog(context: Context, private val spellModel: SpellsModel) :
    Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_spell_details) // Set the dialog layout

        val spellNameTv =
            findViewById<TextView>(R.id.spellNameTv) // Assuming you have a TextView for spell use

        spellNameTv.text = spellModel.spell // Set thekkk  spell details

        // Add any additional functionalities for the dialog buttons, etc.
    }
}
