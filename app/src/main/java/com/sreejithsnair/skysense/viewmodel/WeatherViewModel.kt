package com.sreejithsnair.skysense.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sreejithsnair.skysense.utilities.Constants
import com.sreejithsnair.skysense.model.WeatherModel
import com.sreejithsnair.skysense.repository.NetworkResponse
import com.sreejithsnair.skysense.repository.WeatherRepository
import com.sreejithsnair.skysense.utilities.CryptoManager
import kotlinx.coroutines.launch
import java.io.File

class WeatherViewModel: ViewModel() {
    private val weatherRepository = WeatherRepository.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getWeatherData(city: String){

        _weatherResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeatherData(Constants.API_KEY, city)
                if(response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.postValue(NetworkResponse.Success(it))
                    }
                }
                else{
                    _weatherResult.postValue(NetworkResponse.Error("No matching location found."))
                }
            } catch (e: Exception) {
                _weatherResult.postValue(NetworkResponse.Error("Failed to load data."))
            }
        }
    }
}