package com.kotlin.loyal.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwm.loyal.imp.OnFragment2ActivityListener;
import com.mwm.loyal.imp.Contact;

public abstract class BaseFragment extends Fragment implements View.OnClickListener ,Contact {
    protected final String TAG = BaseFragment.class.getSimpleName();
    protected OnFragment2ActivityListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2ActivityListener) {
            mListener = (OnFragment2ActivityListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment2ActivityListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragment2Activity(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
