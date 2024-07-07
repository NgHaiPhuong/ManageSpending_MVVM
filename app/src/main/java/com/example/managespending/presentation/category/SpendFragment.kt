package com.example.managespending.presentation.category

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyDataBindingPattern
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.managespending.ItemSpendBindingModel_
import com.example.managespending.databinding.FragmentSpendBinding
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory
import com.example.managespending.epoxy.EpoxyDataBindingConfig
import com.example.managespending.model.Category

class SpendFragment : Fragment() {
    private lateinit var binding : FragmentSpendBinding
    private lateinit var controller: SpendController
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var myViewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding =  FragmentSpendBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatabase()
        setupView()
        handleEvent()
    }

    private fun handleEvent() {

    }

    private fun setupDatabase() {
        val dao = MyDatabase.getInstance(requireContext().applicationContext).myDao()
        val factory = MyViewModelFactory(dao)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
    }

    private fun setupView() {
        controller = SpendController()
        layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        controller.spanCount = 1
        binding.epoxyspend.layoutManager = layoutManager
        binding.epoxyspend.setControllerAndBuildModels(controller)

        myViewModel.allCategoryList.observe(viewLifecycleOwner){ category ->
            controller.listCategory = category.toMutableList()
            controller.requestModelBuild()
        }

        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.epoxyspend.addItemDecoration(itemDecoration)

        EpoxyTouchHelper.initDragging(controller)
            .withRecyclerView(binding.epoxyspend)
            .forVerticalList()
            .withTarget(ItemSpendBindingModel_::class.java)
            .andCallbacks(object : EpoxyTouchHelper.DragCallbacks<ItemSpendBindingModel_>(){
                override fun onModelMoved(
                    fromPosition: Int,
                    toPosition: Int,
                    modelBeingMoved: ItemSpendBindingModel_?,
                    itemView: View?
                ) {
                    controller.listCategory.removeAt(toPosition)
                    controller.requestModelBuild()
                }
            })

        EpoxyTouchHelper.initSwiping(binding.epoxyspend)
            .leftAndRight()
            .withTarget(ItemSpendBindingModel_::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<ItemSpendBindingModel_>() {
                override fun onSwipeCompleted(
                    model: ItemSpendBindingModel_?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    controller.listCategory.removeAt(position)
                    controller.notifyModelChanged(position)
                    controller.requestModelBuild()
                    Toast.makeText(requireContext(),
                        "Delete successful",
                        Toast.LENGTH_LONG).show()
                }
            })
    }
    companion object {
        fun newInstance() : SpendFragment{
            val args = Bundle()
            val fragment : SpendFragment = SpendFragment()
            fragment.arguments = args
            return fragment
        }
    }
}