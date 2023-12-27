package com.example.filemanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class MyAdapter(var context: Context, var filesAndFolders: Array<File>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.file_name_text_view)
        val imageView: ImageView = itemView.findViewById(R.id.icon_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.recycle_item,parent,false)
        return ViewHolder(view)
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return filesAndFolders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var selectedFile: File = filesAndFolders[position]
        holder.textView.setText(selectedFile.name)
        if(selectedFile.isDirectory()){
            holder.imageView.setImageResource(R.drawable.baseline_folder_24);
        }else{
            holder.imageView.setImageResource(R.drawable.baseline_insert_drive_file_24);
        }
        holder.itemView.setOnClickListener {
            if (selectedFile.isDirectory) {
                val intent = Intent(context, FileListActivity::class.java)
                val path = selectedFile.absolutePath
                intent.putExtra("path", path)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } else {
                //open thte file
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    val type = "image/*"
                    intent.setDataAndType(Uri.parse(selectedFile.absolutePath), type)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context.applicationContext,
                        "Cannot open the file",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        holder.itemView.setOnLongClickListener { v ->
            val popupMenu = PopupMenu(context, v)
            popupMenu.getMenu().add("DELETE")
            popupMenu.getMenu().add("MOVE")
            popupMenu.getMenu().add("RENAME")
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    if (item.title == "DELETE") {
                        val deleted = selectedFile.delete()
                        if (deleted) {
                            Toast.makeText(
                                context.applicationContext,
                                "DELETED ",
                                Toast.LENGTH_SHORT
                            ).show()
                            v.visibility = View.GONE
                        }
                    }
                    if (item.title == "MOVE") {
                        Toast.makeText(context.applicationContext, "MOVED ", Toast.LENGTH_SHORT)
                            .show()
                    }
                    if (item.title == "RENAME") {
                        Toast.makeText(context.applicationContext, "RENAME ", Toast.LENGTH_SHORT)
                            .show()
                    }
                    return true
                }
            })
            popupMenu.show()
            true
        }

        TODO("Not yet implemented")
    }

}