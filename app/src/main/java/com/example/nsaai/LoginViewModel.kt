package com.example.nsaai

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {

    private var _isSelected = mutableStateOf("SignUp")

    var isSelected= _isSelected


}