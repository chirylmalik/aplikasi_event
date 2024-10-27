package com.example.submissionawaldicoding.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.submissionawaldicoding.databinding.FragmentSettingBinding
import com.example.submissionawaldicoding.ui.setting.preferences.ThemePreferences
import com.example.submissionawaldicoding.ui.setting.preferences.ThemeViewModel

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ThemeViewModel by viewModels {
        ThemeViewModel.Factory(ThemePreferences(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.themeSetting.observe(viewLifecycleOwner) { isDarkMode ->
            binding.switchTheme.isChecked = isDarkMode
        }
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}