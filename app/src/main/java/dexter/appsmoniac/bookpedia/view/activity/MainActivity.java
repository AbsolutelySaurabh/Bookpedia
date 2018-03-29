package dexter.appsmoniac.bookpedia.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import org.parceler.Parcels;

import dexter.appsmoniac.bookpedia.R;
import dexter.appsmoniac.bookpedia.model.pojo.Book;
import dexter.appsmoniac.bookpedia.model.realm.po.RealmBook;
import dexter.appsmoniac.bookpedia.presenter.MyCollectionPresenter;
import dexter.appsmoniac.bookpedia.view.fragment.GoogleBooksListFragment;
import dexter.appsmoniac.bookpedia.view.fragment.MyCollectionFragment;
import dexter.appsmoniac.bookpedia.view.listeners.ClickListener;
import dexter.appsmoniac.bookpedia.view.listeners.LongClickListener;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        ClickListener, LongClickListener {

    public static final String SEARH_ACTIVE = "searchActive";
    private SearchView searchView;
    private MyCollectionFragment myCollectionFragment;

    private static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.myCollectionFragment = MyCollectionFragment.getViewInstance();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        this.showMyCollection();
    }

    private void showMyCollection() {
        if (!getIntent().getBooleanExtra(SEARH_ACTIVE, false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list, myCollectionFragment, "myCollection")
                    .commit();
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getIntent().putExtra(SEARH_ACTIVE, true);
        GoogleBooksListFragment bookListFragment = GoogleBooksListFragment.getViewInstance();

        if (!bookListFragment.isVisible() || !bookListFragment.isResumed()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list, bookListFragment, "searchList")
                    .commit();

            bookListFragment.searhByQuery(query);
        }

        this.searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        getIntent().putExtra(SEARH_ACTIVE, false);
                        showMyCollection();
                        return true;
                    }
                });
        this.searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        this.searchView.setOnQueryTextListener(this);

        return true;
    }

    public void onFloatButtonClick(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    /**
     * Quando acontece a busca, e a tela é rodada, é preciso lidar com o botão voltar para
     * que o aplicativo não seja fechado, pois quando a activity ser recriada
     * o onMenuItemActionCollapse não será mais chamado ao pressionar o botão boltar.
     */
    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra(SEARH_ACTIVE, false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list, myCollectionFragment, "myCollection")
                    .commit();

            getIntent().putExtra(SEARH_ACTIVE, false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBookCLick(Book book, boolean bundle) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        Parcelable parcelable = Parcels.wrap(book);
        intent.putExtra(BookDetailActivity.BOOK_OBJECT, parcelable);
        intent.putExtra(BookDetailActivity.SEARCH_DETAIL, bundle);
        startActivity(intent);
    }

    @Override
    public void onBookLongCLick(RealmBook realmBook) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(String.format(
                getString(R.string.dialog_message), realmBook.getTitle()));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                (dialog, which) -> myCollectionFragment.removeBookFromMyCollection(realmBook));

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
