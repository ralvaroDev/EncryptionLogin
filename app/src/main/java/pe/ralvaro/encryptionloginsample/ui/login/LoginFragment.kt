package pe.ralvaro.encryptionloginsample.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.encryptionloginsample.databinding.FragmentLoginBinding
import pe.ralvaro.encryptionloginsample.ui.home.HomeActivity
import pe.ralvaro.encryptionloginsample.util.Result
import pe.ralvaro.encryptionloginsample.util.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        private val EMAIL_PATTERN = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableOrDisableButtonSignIn()

        launchAndRepeatWithViewLifecycle {
            loginViewModel.channelCredential.collect {
                binding.tfEmail.setText(it)
                // Only if here comes credentials, means this have to be checked
                binding.swtRememberCredentials.isChecked = true
            }
        }

        binding.swtRememberCredentials.setOnCheckedChangeListener { _, isChecked ->
            loginViewModel.isSaveActiveSaveCredentials = isChecked
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.sendCredentialsToLogin(
                email = binding.tfEmail.text.toString(),
                password = binding.tfPassword.text.toString()
            ).run {
                launchAndRepeatWithViewLifecycle {
                    collect {
                        when (it) {
                            is Result.Error -> {
                                binding.tflEmail.isErrorEnabled = true
                                binding.tflEmail.error = it.exception.message

                                binding.tflPassword.isErrorEnabled = true
                                binding.tflPassword.error = it.exception.message

                                binding.btnLogin.isEnabled = true
                            }

                            Result.Loading -> {
                                binding.btnLogin.isEnabled = false
                            }

                            is Result.Success -> {
                                doNavigationToHome(it.data.username)
                            }
                        }
                    }
                }
            }
        }

    }

    private fun doNavigationToHome(username: String) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.putExtra("user_name", username)
        startActivity(intent)
        requireActivity().finish()
    }


    private fun enableOrDisableButtonSignIn() {
        var emailWithValidPattern = false
        var passWithValidPattern = false
        var passLengthWithPattern = false
        binding.tfEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailWithValidPattern = EMAIL_PATTERN.matches(s.toString())
                binding.tflEmail.isErrorEnabled = false
                binding.btnLogin.isEnabled =
                    emailWithValidPattern && passWithValidPattern && passLengthWithPattern
            }
        })

        binding.tfPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passWithValidPattern = s.toString().trim { it <= ' ' }.isNotEmpty()
                passLengthWithPattern = s.toString().length > 6
                binding.tflPassword.isErrorEnabled = false
                binding.btnLogin.isEnabled =
                    emailWithValidPattern && passWithValidPattern && passLengthWithPattern
            }
        })
    }

}