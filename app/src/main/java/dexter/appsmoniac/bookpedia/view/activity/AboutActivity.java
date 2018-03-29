package dexter.appsmoniac.bookpedia.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dexter.appsmoniac.bookpedia.R;
import dexter.appsmoniac.bookpedia.databinding.ActivityAboutBinding;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        this.populateTextViewContent();
        PhotoViewAttacher photoView = new PhotoViewAttacher(this.binding.ivDiagram);
    }

    private void populateTextViewContent() {
        String[] contentArray = getResources().getStringArray(R.array.about_content_array);
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : contentArray) {
            stringBuilder.append("\n" + item);
        }
        this.binding.tvContent.setText(stringBuilder.toString());
    }

}
