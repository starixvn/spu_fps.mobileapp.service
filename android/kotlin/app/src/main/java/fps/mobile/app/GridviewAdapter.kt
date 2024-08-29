package fps.mobile.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PropertyAdapter(private val context: Context, private val properties: List<DeviceInfoModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return properties.size
    }

    override fun getItem(position: Int): Any {
        return properties[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.deviceinfo_item, parent, false)
        }

        // Get the data for this item
        val property = properties[position]

        // Set the property name and value
        val propertyNameTextView = view?.findViewById<TextView>(R.id.propertyName)
        val propertyValueTextView = view?.findViewById<TextView>(R.id.propertyValue)

        propertyNameTextView?.text = property.getPropertyName()
        propertyValueTextView?.text = property.getPropertyValue()

        return view!!
    }
}
