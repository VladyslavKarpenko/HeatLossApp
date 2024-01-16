package com.chi.heat.loss.app.base

import androidx.appcompat.app.AppCompatActivity
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope

abstract class BaseActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope by activityRetainedScope()

}