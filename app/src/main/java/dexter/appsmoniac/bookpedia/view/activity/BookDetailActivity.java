package dexter.appsmoniac.bookpedia.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import org.parceler.Parcels;

import dexter.appsmoniac.bookpedia.R;
import dexter.appsmoniac.bookpedia.databinding.ActivityDetailBookBinding;
import dexter.appsmoniac.bookpedia.model.pojo.Book;
import dexter.appsmoniac.bookpedia.mvp.CollectionMvp;
import dexter.appsmoniac.bookpedia.presenter.AddCollectionPresenter;

public class BookDetailActivity extends AppCompatActivity
    implements CollectionMvp.AddView {

    public static final String BOOK_OBJECT = "bookObject";
    public static final String SEARCH_DETAIL = "searchDetail";
    private static CollectionMvp.AddPresenter presenter;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBookBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail_book);

        this.book = Parcels.unwrap(getIntent().getParcelableExtra(BOOK_OBJECT));
        binding.setBook(book);

        if (!getIntent().getBooleanExtra(SEARCH_DETAIL, false)) {
            binding.btAddCollection.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void addBookToCollection(View view) {
        if (presenter == null) {
            presenter = new AddCollectionPresenter();
        }

        int status = presenter.saveBook(this.book);

        switch (status) {
            case CollectionMvp.Model.PERSIST_OK:
                Toast.makeText(this, R.string.msg_success_adding_collection, Toast.LENGTH_SHORT).show();
                break;
            case CollectionMvp.Model.PERSIST_PROBLEM:
                Toast.makeText(this, R.string.msg_error_adding_collection, Toast.LENGTH_SHORT).show();
                break;
            case CollectionMvp.Model.BOOK_EXISTS_NOT_EXISTS:
                Toast.makeText(this, R.string.msg_book_already_in_collection, Toast.LENGTH_SHORT).show();
                break;
        }

        presenter.closeRealm();
    }

}
