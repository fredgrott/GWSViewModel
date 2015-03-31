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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.grottworkshop.gwsviewmodellibrary.viewmodel.view.IView;

/**
 * AbstractViewModel class which when properly used gives some methods
 * to our own ViewModel instance that pertain to initialization, getting the view,
 * clearing the view, and the different bundle state methods for an
 * FragmentActivity or a Fragment.
 *
 * @author Fred Grott
 * Created by fgrott on 3/30/2015.
 */
public abstract class AbstractViewModel<T extends IView> {

    @Nullable
    private T mView;

    public void initWithView(@NonNull T view) {
        mView = view;
    }

    public T getView() {
        return mView;
    }

    public void clearView() {
        mView = null;
    }

    @SuppressWarnings("unused")
    public void saveState(Bundle bundle) {

    }

    /**
     * Will be called only in case the system is killed due to low memory and restored
     * @param bundle the bundle
     */
    @SuppressWarnings("unused")
    public void restoreState(Bundle bundle) {

    }

    public void onStop() {

    }

    public void onStart() {

    }

    /**
     * Called when there parent fragment or view is already gone and destroyed.
     * This is a good place to empty any planned tasks that are useless without a UI.
     */
    public void onModelRemoved() {

    }
}
