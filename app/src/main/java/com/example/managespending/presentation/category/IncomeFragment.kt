package com.example.managespending.presentation.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyTouchHelper
import com.bumptech.glide.gifdecoder.GifHeader
import com.example.managespending.ItemIncomeBindingModel_
import com.example.managespending.databinding.FragmentIncomeBinding
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeFragment : Fragment() {
    private lateinit var binding : FragmentIncomeBinding
    private lateinit var controller: IncomeController
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var myViewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding =  FragmentIncomeBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatabase()
        setupView()
    }

    private fun setupDatabase() {
        val dao = MyDatabase.getInstance(requireContext().applicationContext).myDao()
        val factory = MyViewModelFactory(dao)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
    }

    private fun setupView() {
        controller = IncomeController()
        layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        controller.spanCount = 1
        binding.epoxyincome.layoutManager = layoutManager
        binding.epoxyincome.setControllerAndBuildModels(controller)

        myViewModel.allCategoryList.observe(viewLifecycleOwner){ category ->
            controller.listCategory = category.toMutableList()
            controller.requestModelBuild()
        }

        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.epoxyincome.addItemDecoration(itemDecoration)

        EpoxyTouchHelper.initDragging(controller)
            .withRecyclerView(binding.epoxyincome)
            .forVerticalList()
            .withTarget(ItemIncomeBindingModel_::class.java)
            .andCallbacks(object : EpoxyTouchHelper.DragCallbacks<ItemIncomeBindingModel_>(){
                override fun onModelMoved(
                    fromPosition: Int,
                    toPosition: Int,
                    modelBeingMoved: ItemIncomeBindingModel_?,
                    itemView: View?
                ) {
                    controller.listCategory.removeAt(toPosition)
                    controller.requestModelBuild()
                }
            })

        EpoxyTouchHelper.initSwiping(binding.epoxyincome)
            .leftAndRight()
            .withTarget(ItemIncomeBindingModel_::class.java)
            .andCallbacks(object: EpoxyTouchHelper.SwipeCallbacks<ItemIncomeBindingModel_>(){
                override fun onSwipeCompleted(
                    model: ItemIncomeBindingModel_?,
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
        fun newInstance() : IncomeFragment{
            val args = Bundle()
            val fragment : IncomeFragment = IncomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}