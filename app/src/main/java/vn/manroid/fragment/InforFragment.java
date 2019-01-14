package vn.manroid.fragment;


import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import vn.manroid.mydictionary.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InforFragment extends Fragment {
    private TextView txt1,txt2;
    private ImageView btnClose;
    private ShimmerTextView txtCoder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_infor, container, false);

        btnClose = (ImageView)view.findViewById(R.id.btn_close);
        txt1 = (TextView) view.findViewById(R.id.txt_1);
        txt2 = (TextView)view.findViewById(R.id.txt_2);
        txtCoder = (ShimmerTextView)view.findViewById(R.id.txt_head);
        txt1.setSelected(true);
        txt2.setSelected(true);
        startShimmer();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Close", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();            }
        });
    }

    private void startShimmer() {
        Shimmer shimmer = new Shimmer();
        shimmer.setRepeatCount(100)
                .setDuration(3000)
                .setStartDelay(1000)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
                .setAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // do something to here
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // do something to here
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // do something to here
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // do something to here
                    }
                });

        shimmer.start(txtCoder);
    }
}
