package com.example.nsaai.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {

    private var _isSelected = mutableStateOf("SignUp")

    var isSelected= _isSelected


}