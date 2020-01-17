package com.example.codingexercise.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.codingexercise.R
import com.example.codingexercise.api.DataRowsList
import java.util.*

class DataAdapter(dataModel : List<DataRowsList>?, context : Context) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

  private var dataList = ArrayList<DataRowsList>()
  private var context : Context? = null

  init {
    this.dataList = dataModel as ArrayList<DataRowsList>
    this.context = context
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.data_list_item,
        parent, false)
    return DataViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return dataList.size
  }

  override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
    val dataItem = dataList[position]
    holder.context = context
    holder?.dataListItem(dataItem)

  }

  fun addData(data: List<DataRowsList>) {
    dataList.clear()
    dataList.addAll(data)
    notifyDataSetChanged()
  }

  class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var title = itemView.findViewById<TextView>(R.id.txttitle)!!
    private var description = itemView.findViewById<TextView>(R.id.txtdescription)!!
    private var imgView : ImageView = itemView.findViewById<ImageView>(R.id.imgView)!!
    var context : Context? = null

    fun dataListItem(dataItem: DataRowsList) {
      if(dataItem.title != null || dataItem.description!= null || dataItem.imageHref != null) {
        title.text = dataItem.title
        description.text = dataItem.description
        if (dataItem.imageHref != null) {
          imgView.visibility = VISIBLE

          Glide.with(context!!).load(dataItem.imageHref.replace("http","https")).apply(
            RequestOptions().diskCacheStrategy(
              DiskCacheStrategy.ALL
            )).into(imgView)
        } else imgView.visibility = GONE
      }else{
        imgView.visibility = GONE
        title.visibility = GONE
        description.visibility = GONE
      }
    }
  }
}