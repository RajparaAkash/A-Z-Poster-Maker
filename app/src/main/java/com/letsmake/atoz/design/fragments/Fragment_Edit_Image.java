package com.letsmake.atoz.design.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.makeramen.roundedimageview.RoundedImageView;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.listener.On_Data_Snap_Listener;
import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class Fragment_Edit_Image extends Fragment implements OnClickListener {

    private String[] pallete = new String[]{"#ffffff", "#cccccc", "#999999", "#666666", "#333333", "#000000", "#ffee90", "#ffd700", "#daa520", "#b8860b", "#ccff66", "#adff2f", "#00fa9a", "#00ff7f", "#00ff00", "#32cd32", "#3cb371", "#99cccc", "#66cccc", "#339999", "#669999", "#006666", "#336666", "#ffcccc", "#ff9999", "#ff6666", "#ff3333", "#ff0033", "#cc0033"};

    private On_Data_Snap_Listener onGetSnap;
    private Uri uri;
    private ImageView btn_back;
    private Button buttonDone, button3;
    private LineColorPicker horizontalPicker;
    private RoundedImageView imageView;
    private int sCorner = 0, sColor = 0, sBorder = 0, sMargin = 45;
    private SeekBar seekBar4, seekRound;
    private TextView txtTitle;

    public static Fragment_Edit_Image newInstance(Uri uri) {
        Fragment_Edit_Image fragmentEditImage = new Fragment_Edit_Image();
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri.toString());
        fragmentEditImage.setArguments(bundle);
        return fragmentEditImage;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btn_back) {
            switch (id) {
                case R.id.button2:
                    On_Data_Snap_Listener onDataSnapListener = this.onGetSnap;
                    if (onDataSnapListener != null) {
                        onDataSnapListener.onSnapFilter(this.uri, this.sCorner, this.sMargin, this.sColor, this.sBorder, true);
                        return;
                    }
                    return;
                case R.id.button3:
                    On_Data_Snap_Listener onDataSnapListener2 = this.onGetSnap;
                    if (onDataSnapListener2 != null) {
                        onDataSnapListener2.onSnapFilter(this.uri, this.sCorner, this.sMargin, this.sColor, this.sBorder, false);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = 0;
        View inflate = layoutInflater.inflate(R.layout.edit_fragment, viewGroup, false);
        this.onGetSnap = (On_Data_Snap_Listener) getActivity();
        this.uri = Uri.parse(getArguments().getString("uri"));
        this.btn_back = inflate.findViewById(R.id.btn_back);
        this.imageView = inflate.findViewById(R.id.imageViews);
        this.buttonDone = inflate.findViewById(R.id.button3);
        this.button3 = inflate.findViewById(R.id.button2);
        this.seekRound = inflate.findViewById(R.id.seekRound);
        this.seekBar4 = inflate.findViewById(R.id.seekBar4);
        this.horizontalPicker = inflate.findViewById(R.id.picker1);
        this.txtTitle = inflate.findViewById(R.id.txtTitle);
        this.txtTitle.setTypeface(Typeface.createFromAsset(viewGroup.getContext().getAssets(), "font/MONTSERRATSUBRAYADA-BOLD.ttf"));
        this.btn_back.setOnClickListener(this);
        this.buttonDone.setOnClickListener(this);
        this.button3.setOnClickListener(this);
        this.imageView.setImageURI(this.uri);

        this.seekRound.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Fragment_Edit_Image.this.sCorner = i;
                Fragment_Edit_Image.this.imageView.setCornerRadius((float) i);
                Fragment_Edit_Image.this.imageView.invalidate();
            }
        });
        this.seekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Fragment_Edit_Image.this.sBorder = i;
                Fragment_Edit_Image.this.imageView.setBorderWidth((float) i);
                Fragment_Edit_Image.this.imageView.invalidate();
            }
        });
        int[] iArr = new int[this.pallete.length];
        while (i < iArr.length) {
            iArr[i] = Color.parseColor(this.pallete[i]);
            i++;
        }
        this.horizontalPicker.setColors(iArr);
        this.horizontalPicker.setOnColorChangedListener(new OnColorChangedListener() {
            public void onColorChanged(int i) {
                Fragment_Edit_Image.this.sColor = i;
                Fragment_Edit_Image.this.imageView.setBorderColor(Fragment_Edit_Image.this.sColor);
                Fragment_Edit_Image.this.imageView.invalidate();
            }
        });
        return inflate;
    }


}
