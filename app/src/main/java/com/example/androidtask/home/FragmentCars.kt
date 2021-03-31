package com.seven.delivery29.normaluser.bottomnav.homeuser.allservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.androidtask.R
import com.example.androidtask.databinding.FragmentCarBinding
import com.example.androidtask.home.HomePresenter
import com.example.androidtask.home.model.Data
import com.example.androidtask.home.model.ResponseCarList
import com.example.androidtask.util.BlurDialog
import com.example.androidtask.util.NameUtil
import com.example.androidtask.util.UtilKotlin
import com.example.androidtask.util.ViewModelHandleChangeFragmentclass
import com.imperialcreation.remcoowner.utils.NestedScrollPaginationView
import com.seven.util.ApiConfiguration.ApiManagerDefault
import com.seven.util.ApiConfiguration.WebService
import com.seven.util.GridModel
import io.reactivex.observers.DisposableObserver
import retrofit2.Response


class FragmentCars : Fragment(), NestedScrollPaginationView.OnMyScrollChangeListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
   var pageNumber = 1
    var hasMorePages = false
    lateinit var binder : FragmentCarBinding
    var model : ViewModelHandleChangeFragmentclass?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        model = UtilKotlin.declarViewModel(requireActivity())
        binder =  FragmentCarBinding.inflate(inflater,container,false)
        webService = ApiManagerDefault(context!!).apiService
        binder.smartNestedScrolView.myScrollChangeListener =this
        return binder.root
    }
    var progressDialog : BlurDialog?=null
    var webService :WebService?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = UtilKotlin.ProgressDialog()
        setInitatateAdapter()
        binder?.swiperefresh?.post{
            serviceCallApi()
        }
        binder.swiperefresh.setOnRefreshListener(
            OnRefreshListener {
                reloadPage()
                serviceCallApi()
                binder.swiperefresh.setRefreshing(false)

            }
        )
    }

    private fun setInitatateAdapter() {
        carAdapter = CarsAdaptor(activity!!, arrayList, model!!)
        UtilKotlin.setRecycleView(
            binder.gridRecycleServices, carAdapter!!, null, context!!,
            GridModel(2, 15), true
        ) // set servuces
    }

    var arrayList = ArrayList<Data>()
    var carAdapter :CarsAdaptor ?=null
    override fun onDestroyView() {
        dispposibleCall?.dispose()
        model?.notifyItemSelected?.removeObservers(requireActivity())
        super.onDestroyView()
    }
  private fun observeCall() {
        model?.notifyItemSelected?.observe(activity!!, Observer<Any> { result ->
                if (result != null) {

                    if (result is Int && result == NameUtil.isNetworkError)
                        serviceCallApi()
                    model?.setNotifyItemSelected(null)
                }
        })
    }
    private fun serviceCallApi() {
        if (UtilKotlin.isNetworkAvailable(context!!)) {
            UtilKotlin.showLoader(progressDialog!!,activity!!)
            HomePresenter.getCarListCall(webService!! ,pageNumber, getCarResponse())
        } else {
            UtilKotlin.showNetworkIssueDialog(activity)

        }
    }
    var dispposibleCall : DisposableObserver<Response<ResponseCarList>>? =null
    private fun getCarResponse(): DisposableObserver<Response<ResponseCarList>> {

         dispposibleCall = object : DisposableObserver<Response<ResponseCarList>>() {
             override fun onComplete() {
                 UtilKotlin?.hideLoader(progressDialog)
                 dispose()
             }

             override fun onError(e: Throwable) {
                 UtilKotlin.showSnackErrorInto(activity!!, e.message.toString())
                 UtilKotlin?.hideLoader(progressDialog)
                 dispose()
             }

             override fun onNext(response: Response<ResponseCarList>) {
                 UtilKotlin?.hideLoader(progressDialog)
                 if (response!!.isSuccessful) {
                     //  arrayList.clear()
                     if (response.body()?.data?.isNotEmpty()==true) {
                         carAdapter?.updateData(response.body()?.data ?: ArrayList())
                         hasMorePages = true
                         }
                    // arrayList.addAll(response.body()?.data ?: ArrayList())
                     else
                         hasMorePages = false


                 } else {
                     if (response.errorBody() != null) { // should parse error body in future
                         // val error = UtilKotlin.getErrorBodyResponse(response.errorBody(), context!!)
                         UtilKotlin.showSnackErrorInto(
                             activity!!,
                             getString(R.string.issue_happend)
                         )
                     }

                 }
             }
         }
      return  dispposibleCall!!
    }
    fun reloadPage() {
        arrayList.clear()
        pageNumber = 1
        binder.smartNestedScrolView.resetPageCounter()


    }

    override fun onLoadMore(currentPage: Int) {
        if (hasMorePages) {
            pageNumber = currentPage
            serviceCallApi()
        }    }

}