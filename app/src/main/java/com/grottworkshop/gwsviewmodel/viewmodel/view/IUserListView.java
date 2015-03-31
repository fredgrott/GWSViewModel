package com.grottworkshop.gwsviewmodel.viewmodel.view;

import com.grottworkshop.gwsviewmodellibrary.viewmodel.view.IView;

import java.util.List;

/**
 * Created by fgrott on 3/30/2015.
 */
public interface IUserListView extends IView {

    public void showLoading();
    public void hideProgress();

    public void setUsers(List<String> users);
    public void showIntermediateProgress(boolean show);
}
