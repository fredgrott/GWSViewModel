/*
 * Copyright (c) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions andlimitations under the License.
 */
package com.grottworkshop.gwsviewmodellibrary.viewmodel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.grottworkshop.gwsviewmodellibrary.viewmodel.view.IView;

import java.util.UUID;

/**
 *
 * @author Fred Grott
 * Created by fgrott on 3/30/2015.
 */
public class ViewModelHelper<T extends IView, R extends AbstractViewModel<T>> {

    private String mScreenId;
    private R mViewModel;

    @SuppressWarnings("unchecked")
    public void onCreate(@NonNull Activity activity, @Nullable Bundle savedInstanceState,
                         @NonNull Class<? extends AbstractViewModel<T>> viewModelClass) throws InstantiationException {
        if (savedInstanceState == null) {
            mScreenId = UUID.randomUUID().toString();
        } else {
            mScreenId = savedInstanceState.getString("identifier");
        }

        ViewModelProvider.ViewModelWrapper<T> viewModelWrapper = null;
        try {
            viewModelWrapper = getViewModelProvider(activity).getViewModelProvider().getViewModel(mScreenId, viewModelClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mViewModel = (R) (viewModelWrapper != null ? viewModelWrapper.viewModel : null);
        assert viewModelWrapper != null;
        if (savedInstanceState != null && viewModelWrapper.wasCreated) {
            Log.e("model", "Application recreated by system - restoring viewmodel");
            mViewModel.restoreState(savedInstanceState);
        }
    }

    public void initWithView(T view) {
        if (mViewModel == null) {
            //no viewmodel for this fragment
            return;
        }
        mViewModel.initWithView(view);
    }

    public void onDestroyView(@NonNull Fragment fragment) {
        if (mViewModel == null) {
            //no viewmodel for this fragment
            return;
        }
        mViewModel.clearView();
        if (fragment.getActivity() != null && fragment.getActivity().isFinishing()) {
            removeViewModel(fragment.getActivity());
        }
    }

    public void onDestroy(@NonNull Fragment fragment) {
        if (mViewModel == null) {
            //no viewmodel for this fragment
            return;
        }
        if (fragment.getActivity().isFinishing()) {
            removeViewModel(fragment.getActivity());
        } else if (fragment.isRemoving()) {
            Log.d("mode", "Removing viewmodel - fragment replaced");
            removeViewModel(fragment.getActivity());
        }
    }

    @SuppressWarnings("unused")
    public void onDestroy(@NonNull Activity activity) {
        if (mViewModel == null) {
            //no viewmodel for this fragment
            return;
        }
        if (activity.isFinishing()) {
            removeViewModel(activity);
        }
    }

    public void onStop() {
        if (mViewModel == null) {
            //no viewmodel for this fragment
            return;
        }
        mViewModel.onStop();
    }

    public void onStart() {
        if (mViewModel == null) {
            //no viewmodel for this fragment
            return;
        }
        mViewModel.onStart();
    }

    public R getViewModel() {
        return mViewModel;
    }

    public void onSaveInstanceState(@Nullable Bundle bundle) {
        if (bundle != null) {
            bundle.putString("identifier", mScreenId);
        }
        if (mViewModel != null) {
            mViewModel.saveState(bundle);
        }
    }

    private IViewModelProvider getViewModelProvider(@NonNull Activity activity) {
        if (!(activity instanceof IViewModelProvider)) {
            throw new IllegalStateException("Your activity must implement IViewModelProvider");
        }
        return ((IViewModelProvider)activity);
    }

    protected boolean removeViewModel(@NonNull Activity activity) {
        boolean removed = getViewModelProvider(activity).getViewModelProvider().remove(mScreenId);
        mViewModel.onModelRemoved();
        return removed;
    }

}
