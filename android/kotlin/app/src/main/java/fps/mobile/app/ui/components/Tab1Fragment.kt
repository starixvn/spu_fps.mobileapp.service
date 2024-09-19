package fps.mobile.app.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import fps.mobile.app.data.DeviceInfoModel
import fps.mobile.app.R
import fps.mobile.client.DeviceIdentify

/**
 * A simple [Fragment] subclass.
 * Use the [Tab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Tab1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)

        val gridView = view.findViewById<GridView>(R.id.gvDeviceInfo)

        val deviceInfoList = DeviceIdentify.getDeviceIds()

        val newDeviceInfoList: List<DeviceInfoModel> = deviceInfoList.map {
            DeviceInfoModel(it.name, it.value)
        }

        val adapter = PropertyAdapter(requireContext(), newDeviceInfoList)
        gridView.adapter = adapter
        //val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6")

        //val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
       // gridView.adapter = adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}