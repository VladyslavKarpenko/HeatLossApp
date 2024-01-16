package com.chi.heat.loss.app.base

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope

abstract class BaseBottomSheetDialogFragment<VIEW_BINDING : ViewBinding, VIEW_MODEL : CoroutinesViewModel> :
    BottomSheetDialogFragment(), AndroidScopeComponent {

    override val scope by fragmentScope()

    protected abstract val binding: VIEW_BINDING

    protected abstract val viewModel: VIEW_MODEL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()
    }

    protected open fun setUpView() = Unit

    protected open fun observeViewModel() = Unit

    override fun onDestroyView() {
        clear()
        super.onDestroyView()
    }

    // clear all dependencies or view links in case of interrupted animation with navigation
    protected open fun clear() = Unit

    protected fun <T> Flow<T>.observe(action: suspend (T) -> Unit) {
        onEach { action.invoke(it) }
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .launchIn(lifecycleScope)
    }
}