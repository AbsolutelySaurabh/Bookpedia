package dexter.appsmoniac.bookpedia.view.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageBinding {

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView imageView, String url){
        Picasso.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
