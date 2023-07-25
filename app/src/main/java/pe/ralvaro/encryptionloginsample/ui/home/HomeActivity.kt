package pe.ralvaro.encryptionloginsample.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.encryptionloginsample.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.extras?.getString("user_name") ?: "Error displaying name"

        binding.tvUserName.text = userName

    }
}