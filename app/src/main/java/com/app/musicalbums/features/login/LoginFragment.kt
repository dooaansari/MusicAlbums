package com.app.musicalbums.features.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.musicalbums.contracts.IView
import com.app.musicalbums.databinding.FragmentLoginBinding
import com.app.musicalbums.helpers.Authenticator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() , IView, ILoginFragment{
    @Inject lateinit var authenticator: Authenticator

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.presenter = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("response", "here")
        subscribeComments()
    }

    private fun subscribeComments(){
        viewModel.getAppComments()
        viewModel.commentsSuccess.observe(viewLifecycleOwner,{
            Log.i("response",it.toString())
        })
        viewModel.commentsFailure.observe(viewLifecycleOwner, {
            Log.i("response","error")
        })
    }


    override fun bindLogin(){
        Log.i("tag","tag")
        authenticator.launchSignIn()
    }
//    val bindLogin = {
//        Log.i("tag","button here")
//        authenticator.launchSignIn()
//    }

    override fun onResult() {
      viewModel.loginUser()
    }

}