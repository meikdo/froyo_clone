package de.hshl.isd.parkaid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class ListAdapterCustom(private val context: Context, private val list: List<Parkhaus_m>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertViewX = convertView
        val holder: ViewHolder

        if (convertViewX == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewX = inflater.inflate(R.layout.listview_item, null, true)

            holder.name = convertViewX!!.findViewById(R.id.name_list) as TextView
            holder.preis = convertViewX.findViewById(R.id.preis_list) as TextView
            holder.distance = convertViewX.findViewById(R.id.distance_list) as TextView
            holder.strasse = convertViewX.findViewById(R.id.adress_list) as TextView
            holder.ort = convertViewX.findViewById(R.id.ort_list) as TextView


            convertViewX.tag = holder
        } else {
            holder = convertViewX.tag as ViewHolder
        }


        holder.name!!.setText(list[position].name)
        holder.preis!!.setText(list[position].preisString)
        holder.distance!!.setText(list[position].distanceString)
        holder.strasse!!.setText(list[position].adresse.strasse)
        holder.ort!!.setText(list[position].adresse.ort)

        //Preis-Color (indikator)
      //  if(list[position].preisIndikator=="low")

        return convertViewX
    }

    private inner class ViewHolder {

        var name: TextView? = null
        var preis: TextView? = null
        var distance: TextView? = null
        var strasse: TextView? = null
        var ort: TextView?=null

    }

}