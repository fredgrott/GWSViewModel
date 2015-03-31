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

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Create and keep this class inside your Activity. Store it
 * in {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()
 * and restore in {@link android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)} before
 * calling the super implemenentation.
 *
 *
 * @author Fred Grott
 * Created by fgrott on 3/30/2015.
 */
public class ViewModelProvider {

    private final HashMap<String, AbstractViewModel<? extends IView>> mViewModelCache;

    public ViewModelProvider() {
        mViewModelCache = new HashMap<>();
    }

    boolean remove(@NonNull String key) {
        return mViewModelCache.remove(key) != null;
    }

    public static class ViewModelWrapper<T extends IView> {
        @NonNull
        public final AbstractViewModel<T> viewModel;
        public final boolean wasCreated;

        private ViewModelWrapper(@NonNull AbstractViewModel<T> mViewModel, boolean mWasCreated) {
            this.viewModel = mViewModel;
            this.wasCreated = mWasCreated;
        }
    }

    /**
     * Call this in {@link android.app.Activity#onStop()} if {@link android.app.Activity#isFinishing()}
     */
    public void removeAllViewModels() {
        mViewModelCache.clear();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public synchronized <T extends IView> ViewModelWrapper<T> getViewModel(@NonNull String key, @NonNull Class<? extends AbstractViewModel<T>> viewModelClass) throws InstantiationException, IllegalAccessException {
        AbstractViewModel<T> instance = (AbstractViewModel<T>) mViewModelCache.get(key);
        if (instance != null) {
            return new ViewModelWrapper<>(instance, false);
        }

        instance = viewModelClass.newInstance();
        mViewModelCache.put(key, instance);
        return new ViewModelWrapper<>(instance, true);
    }

}
