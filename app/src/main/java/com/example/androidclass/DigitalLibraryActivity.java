package com.example.androidclass;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DigitalLibraryActivity extends AppCompatActivity {

    private static final int NAV_CATALOG = 101;
    private static final int NAV_BORROWED = 102;
    private static final int NAV_ABOUT = 103;
    private static final String ALL_CATEGORIES = "All Categories";

    private static final List<Book> BOOKS = new ArrayList<>(Arrays.asList(
            new Book("Learn Java Programming", "James Gosling", "Programming",
                    "A complete guide to Java fundamentals, object-oriented programming, and modern application development."),
            new Book("Python Basics", "Guido van Rossum", "Programming",
                    "Get started with Python syntax, data structures, and simple scripting."),
            new Book("Clean Code", "Robert C. Martin", "Programming",
                    "Practical advice for writing readable, maintainable software."),
            new Book("The Alchemist", "Paulo Coelho", "Fiction",
                    "A young shepherd follows a journey of discovery and learns to listen to his dreams."),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fiction",
                    "Bilbo Baggins sets out on an unexpected adventure across Middle-earth."),
            new Book("A Brief History of Time", "Stephen Hawking", "Science",
                    "An accessible exploration of the universe, black holes, and the nature of time."),
            new Book("Cosmos", "Carl Sagan", "Science",
                    "A journey through the origins of the universe and humanity's place in it."),
            new Book("Think and Grow Rich", "Napoleon Hill", "Business",
                    "Classic lessons on goal setting, persistence, and personal achievement."),
            new Book("Business Wisdom", "Peter F. Drucker", "Business",
                    "Ideas and practical insights for thoughtful leadership and effective management.")
    ));

    private FrameLayout contentContainer;
    private String selectedCategory = ALL_CATEGORIES;
    private String searchQuery = "";
    private String currentScreen = "catalog";
    private String screenBeforeDetails = "catalog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_library);

        MaterialToolbar toolbar = findViewById(R.id.libraryToolbar);
        toolbar.setTitleTextColor(getColor(R.color.library_ivory));

        contentContainer = findViewById(R.id.libraryContent);
        configureNavigation(findViewById(R.id.libraryNavigation));
        showCatalog();
    }

    private void configureNavigation(BottomNavigationView navigation) {
        navigation.getMenu().add(0, NAV_CATALOG, 0, "Catalog")
                .setIcon(android.R.drawable.ic_menu_search);
        navigation.getMenu().add(0, NAV_BORROWED, 1, "My Books")
                .setIcon(android.R.drawable.ic_menu_agenda);
        navigation.getMenu().add(0, NAV_ABOUT, 2, "About")
                .setIcon(android.R.drawable.ic_menu_info_details);
        navigation.setLabelVisibilityMode(BottomNavigationView.LABEL_VISIBILITY_LABELED);
        navigation.setItemIconTintList(ColorStateList.valueOf(getColor(R.color.library_olive)));
        navigation.setItemTextColor(ColorStateList.valueOf(getColor(R.color.library_olive)));
        navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == NAV_CATALOG) {
                currentScreen = "catalog";
                showCatalog();
            } else if (item.getItemId() == NAV_BORROWED) {
                currentScreen = "borrowed";
                showBorrowedBooks();
            } else if (item.getItemId() == NAV_ABOUT) {
                currentScreen = "about";
                showAbout();
            } else {
                return false;
            }
            return true;
        });
        navigation.setSelectedItemId(NAV_CATALOG);
    }

    private void showCatalog() {
        currentScreen = "catalog";
        LinearLayout page = createPage();
        addHeading(page, "Find your next read", "Search by title or browse a category.");

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setHint("Search books by title");
        search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        search.setPadding(dp(16), dp(12), dp(16), dp(12));
        search.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.library_olive)));
        search.setText(searchQuery);
        page.addView(search, matchWidth(dp(12), 0, dp(12), 0));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence value, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                searchQuery = value.toString();
                showCatalogResults(page);
            }

            @Override
            public void afterTextChanged(Editable value) {
            }
        });

        HorizontalScrollView categoryScroller = new HorizontalScrollView(this);
        categoryScroller.setHorizontalScrollBarEnabled(false);
        ChipGroup categories = new ChipGroup(this);
        categories.setSingleSelection(true);
        categories.setSelectionRequired(true);
        categories.setChipSpacingHorizontal(dp(8));
        categories.setChipSpacingVertical(dp(4));
        String[] categoryNames = {
                ALL_CATEGORIES, "Programming", "Fiction", "Science", "Business"
        };
        for (String category : categoryNames) {
            Chip chip = new Chip(this);
            chip.setText(category);
            chip.setCheckable(true);
            chip.setChecked(category.equals(selectedCategory));
            chip.setChipBackgroundColor(ColorStateList.valueOf(
                    category.equals(selectedCategory)
                            ? getColor(R.color.library_olive)
                            : Color.WHITE));
            chip.setTextColor(category.equals(selectedCategory)
                    ? Color.WHITE : getColor(R.color.library_deep_olive));
            chip.setOnClickListener(view -> {
                selectedCategory = category;
                showCatalog();
            });
            categories.addView(chip);
        }
        categoryScroller.addView(categories);
        page.addView(categoryScroller, matchWidth(0, 0, 0, dp(8)));

        LinearLayout results = new LinearLayout(this);
        results.setOrientation(LinearLayout.VERTICAL);
        results.setTag("catalog-results");
        page.addView(results, matchWidth());
        setContent(page);
        populateCatalogResults(results);
    }

    private void showCatalogResults(LinearLayout page) {
        View resultView = page.findViewWithTag("catalog-results");
        if (resultView instanceof LinearLayout) {
            populateCatalogResults((LinearLayout) resultView);
        }
    }

    private void populateCatalogResults(LinearLayout results) {
        results.removeAllViews();
        int count = 0;
        String query = searchQuery.trim().toLowerCase(Locale.ROOT);
        for (Book book : BOOKS) {
            boolean matchesCategory = ALL_CATEGORIES.equals(selectedCategory)
                    || selectedCategory.equals(book.category);
            boolean matchesTitle = query.isEmpty()
                    || book.title.toLowerCase(Locale.ROOT).contains(query);
            if (matchesCategory && matchesTitle) {
                results.addView(createBookCard(book, false));
                count++;
            }
        }
        if (count == 0) {
            addMessage(results, "No books found. Try another title or category.");
        }
    }

    private void showBorrowedBooks() {
        currentScreen = "borrowed";
        LinearLayout page = createPage();
        addHeading(page, "My Borrowed Books", "Books you have borrowed from the library.");

        int count = 0;
        for (Book book : BOOKS) {
            if (book.isBorrowed) {
                page.addView(createBookCard(book, true));
                count++;
            }
        }
        if (count == 0) {
            addMessage(page, "You have not borrowed any books yet.");
        }
        setContent(page);
    }

    private void showAbout() {
        currentScreen = "about";
        LinearLayout page = createPage();
        addHeading(page, "About This Application", "Digital Library · Version 1.0");
        addMessage(page, "Digital Library helps students browse, search, and filter books across Programming, Fiction, Science, and Business.");
        addSection(page, "Features",
                "• Case-insensitive title search\n"
                        + "• Filter by book category\n"
                        + "• Borrow and return books in memory\n"
                        + "• Catalog and borrowed books update immediately");
        addSection(page, "Technical Details",
                "Theme: Olive & Ivory\n"
                        + "Navigation: Catalog, My Books, and About");
        setContent(page);
    }

    private void showBookDetails(Book book) {
        screenBeforeDetails = currentScreen;
        LinearLayout page = createPage();

        MaterialButton backButton = createButton("Back to " +
                ("borrowed".equals(screenBeforeDetails) ? "My Books" : "Catalog"));
        backButton.setIconResource(android.R.drawable.ic_media_previous);
        backButton.setOnClickListener(view -> returnFromDetails());
        page.addView(backButton, wrapWidthWrapHeight());

        MaterialCardView cover = new MaterialCardView(this);
        cover.setCardBackgroundColor(getColor(R.color.library_olive));
        cover.setRadius(dp(18));
        TextView coverLabel = createText("BOOK", 16, Color.WHITE, true);
        coverLabel.setGravity(Gravity.CENTER);
        cover.addView(coverLabel, new MaterialCardView.LayoutParams(
                dp(112), dp(148)));
        LinearLayout.LayoutParams coverParams = wrapWidthWrapHeight();
        coverParams.gravity = Gravity.CENTER_HORIZONTAL;
        coverParams.topMargin = dp(20);
        coverParams.bottomMargin = dp(20);
        page.addView(cover, coverParams);

        addText(page, book.title, 26, getColor(R.color.library_deep_olive), true);
        addText(page, "by " + book.author, 16, getColor(R.color.library_muted), false);
        addText(page, book.category, 14, getColor(R.color.library_olive), true);
        addSection(page, "Availability Status",
                book.isBorrowed ? "Borrowed" : "Available");
        addSection(page, "About this book", book.description);

        MaterialButton action = createButton(book.isBorrowed ? "Return Book" : "Borrow Book");
        action.setOnClickListener(view -> {
            book.isBorrowed = !book.isBorrowed;
            Toast.makeText(this, book.isBorrowed
                    ? "Successfully borrowed " + book.title
                    : "Returned " + book.title, Toast.LENGTH_SHORT).show();
            showBookDetails(book);
        });
        LinearLayout.LayoutParams actionParams = matchWidth();
        actionParams.topMargin = dp(16);
        page.addView(action, actionParams);
        setContent(page);
    }

    private void returnFromDetails() {
        if ("borrowed".equals(screenBeforeDetails)) {
            showBorrowedBooks();
        } else {
            showCatalog();
        }
    }

    private MaterialCardView createBookCard(Book book, boolean showReturnButton) {
        MaterialCardView card = new MaterialCardView(this);
        card.setCardBackgroundColor(Color.WHITE);
        card.setStrokeColor(getColor(R.color.library_border));
        card.setStrokeWidth(dp(1));
        card.setRadius(dp(14));
        card.setCardElevation(dp(2));
        card.setClickable(true);
        card.setFocusable(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(14), dp(16), dp(14));
        card.addView(content);

        addText(content, book.title, 18, getColor(R.color.library_deep_olive), true);
        addText(content, "by " + book.author + "  ·  " + book.category, 14,
                getColor(R.color.library_muted), false);
        addText(content, book.description, 14, getColor(R.color.library_text), false);
        addText(content, book.isBorrowed ? "Borrowed" : "Available", 13,
                book.isBorrowed ? getColor(R.color.library_muted)
                        : getColor(R.color.library_olive), true);

        if (showReturnButton) {
            MaterialButton returnButton = createButton("Return Book");
            returnButton.setOnClickListener(view -> {
                book.isBorrowed = false;
                Toast.makeText(this, "Returned " + book.title, Toast.LENGTH_SHORT).show();
                showBorrowedBooks();
            });
            LinearLayout.LayoutParams buttonParams = matchWidth();
            buttonParams.topMargin = dp(8);
            content.addView(returnButton, buttonParams);
        }

        card.setOnClickListener(view -> showBookDetails(book));
        LinearLayout.LayoutParams cardParams = matchWidth();
        cardParams.bottomMargin = dp(12);
        card.setLayoutParams(cardParams);
        return card;
    }

    private LinearLayout createPage() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(getColor(R.color.library_ivory));

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(18), dp(20), dp(24));
        scrollView.addView(page, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
        scrollView.setTag("library-page");
        return page;
    }

    private void setContent(LinearLayout page) {
        View oldPage = contentContainer.findViewWithTag("library-page");
        if (oldPage != null) {
            contentContainer.removeView(oldPage);
        }
        ScrollView scrollView = (ScrollView) page.getParent();
        if (scrollView == null) {
            throw new IllegalStateException("Library page must be attached to its scroll view.");
        }
        contentContainer.addView(scrollView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void addHeading(LinearLayout parent, String title, String subtitle) {
        addText(parent, title, 23, getColor(R.color.library_deep_olive), true);
        addText(parent, subtitle, 14, getColor(R.color.library_muted), false);
    }

    private void addSection(LinearLayout parent, String title, String body) {
        TextView sectionTitle = createText(title, 17, getColor(R.color.library_deep_olive), true);
        LinearLayout.LayoutParams titleParams = wrapWidthWrapHeight();
        titleParams.topMargin = dp(18);
        parent.addView(sectionTitle, titleParams);
        addText(parent, body, 15, getColor(R.color.library_text), false);
    }

    private void addMessage(LinearLayout parent, String message) {
        TextView text = createText(message, 15, getColor(R.color.library_muted), false);
        text.setPadding(0, dp(16), 0, dp(8));
        parent.addView(text, wrapWidthWrapHeight());
    }

    private void addText(LinearLayout parent, String value, int sizeSp, int color, boolean bold) {
        TextView text = createText(value, sizeSp, color, bold);
        LinearLayout.LayoutParams params = wrapWidthWrapHeight();
        params.bottomMargin = dp(6);
        parent.addView(text, params);
    }

    private TextView createText(String value, int sizeSp, int color, boolean bold) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        text.setTextColor(color);
        if (bold) {
            text.setTypeface(text.getTypeface(), android.graphics.Typeface.BOLD);
        }
        return text;
    }

    private MaterialButton createButton(String label) {
        MaterialButton button = new MaterialButton(this);
        button.setText(label);
        button.setTextColor(Color.WHITE);
        button.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.library_olive)));
        return button;
    }

    private LinearLayout.LayoutParams matchWidth() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams matchWidth(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = matchWidth();
        params.setMargins(left, top, right, bottom);
        return params;
    }

    private LinearLayout.LayoutParams wrapWidthWrapHeight() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private static final class Book {
        final String title;
        final String author;
        final String category;
        final String description;
        boolean isBorrowed;

        Book(String title, String author, String category, String description) {
            this.title = title;
            this.author = author;
            this.category = category;
            this.description = description;
        }
    }
}
