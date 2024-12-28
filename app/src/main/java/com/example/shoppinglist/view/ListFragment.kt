package com.example.shoppinglist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentListBinding
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.model.ShopViewModel


class ListFragment : Fragment(), ProductAdapter.ItemClickListener {


    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var shopViewModel: ShopViewModel? = null

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)
        shopViewModel!!.loadData(requireContext())

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentListBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        shopViewModel!!.getProductLiveData().observe(viewLifecycleOwner) {
            setRecycler(it)
        }
        binding.saveBtn.setOnClickListener {
            shopViewModel?.saveAll()
        }

    }
    private fun initRecycler(){
        binding.itemRv.apply {
            productAdapter = ProductAdapter(this@ListFragment,null)
            adapter = productAdapter
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            addItemDecoration(DividerItemDecoration( context,DividerItemDecoration.VERTICAL))

        }
    }
    private fun setRecycler(list: MutableList<Product>?){
        productAdapter.productList = list
        productAdapter.notifyDataSetChanged()
    }



    override fun onDeleteClicked(p: Product, pos: Int) {
        shopViewModel?.removeItem(pos)
    }


}