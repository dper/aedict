/**
 *     Aedict - an EDICT browser for Android
 Copyright (C) 2009 Martin Vysny
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package sk.baka.aedict;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Contains utility methods for searching with Lucene.
 * 
 * @author Martin Vysny
 */
public final class SearchUtils {
	private final Activity activity;

	/**
	 * Creates new utility class instance.
	 * 
	 * @param activity
	 *            owning activity, must not be null.
	 */
	public SearchUtils(final Activity activity) {
		this.activity = activity;
	}

	/**
	 * Performs search for a japanese word or expression.
	 * 
	 * @param romaji
	 *            word spelling. This string is converted to both hiragana and
	 *            katakana, then the EDict search is performed.
	 * @param isExact
	 *            if true then only exact matches are returned.
	 */
	public void searchForJapan(final String romaji, final boolean isExact) {
		final SearchQuery q = new SearchQuery();
		q.isJapanese = true;
		q.query = new String[] { JpUtils.toHiragana(romaji), JpUtils.toKatakana(romaji) };
		q.matcher = isExact ? MatcherEnum.ExactMatchEng : MatcherEnum.SubstringMatch;
		performSearch(q);
	}

	/**
	 * Performs search for an english word or expression.
	 * 
	 * @param text
	 *            the text to search for.
	 * @param isExact
	 *            if true then only exact matches are returned.
	 */
	public void searchForEnglish(final String text, final boolean isExact) {
		final SearchQuery q = new SearchQuery();
		q.isJapanese = false;
		q.query = new String[] { text };
		q.matcher = isExact ? MatcherEnum.ExactMatchEng : MatcherEnum.SubstringMatch;
		performSearch(q);
	}

	private void performSearch(final SearchQuery query) {
		final Intent intent = new Intent(activity, ResultActivity.class);
		query.putTo(intent);
		activity.startActivity(intent);
	}

	/**
	 * Registers search functionality to a standardized set of three components:
	 * the "IsExact" check box, the search query edit box and the "Search"
	 * button.
	 * 
	 * @param isExactCheckBox
	 *            the "IsExact" check box resource id
	 * @param searchEditText
	 *            the search query edit box
	 * @param handleSelections
	 *            if true then only selected portions of text will be used for
	 *            search (if a selection exists).
	 * @param searchButton
	 *            the search button
	 * @param isJapanSearch
	 *            if true then we are searching for japanese text (in romaji).
	 */
	public void registerSearch(final int isExactCheckBox, final int searchEditText, final boolean handleSelections, final int searchButton, final boolean isJapanSearch) {
		final EditText searchEdit = (EditText) activity.findViewById(searchEditText);
		final Button searchBtn = (Button) activity.findViewById(searchButton);
		final SearchText handler = new SearchText(isExactCheckBox, searchEditText, handleSelections, isJapanSearch);
		searchEdit.setOnEditorActionListener(handler);
		searchBtn.setOnClickListener(handler);
	}

	private class SearchText implements TextView.OnEditorActionListener, View.OnClickListener {
		private final int isExactCheckBox;
		private final int searchEditText;
		private final boolean handleSelections;
		private final boolean isJapanSearch;

		public SearchText(final int isExactCheckBox, final int searchEditText, final boolean handleSelections, final boolean isJapanSearch) {
			this.isExactCheckBox = isExactCheckBox;
			this.searchEditText = searchEditText;
			this.handleSelections = handleSelections;
			this.isJapanSearch = isJapanSearch;
		}

		public void onClick(View v) {
			final EditText searchEdit = (EditText) activity.findViewById(searchEditText);
			final CheckBox exactMatch = (CheckBox) activity.findViewById(isExactCheckBox);
			String query = searchEdit.getText().toString();
			if (handleSelections && (searchEdit.getSelectionStart() >= 0) && (searchEdit.getSelectionEnd() >= 0)) {
				String selected = query.substring(searchEdit.getSelectionStart(), searchEdit.getSelectionEnd()).trim();
				if (selected.length() > 0) {
					query = selected;
				}
			}
			final boolean isExact = exactMatch.isChecked();
			if (isJapanSearch) {
				searchForJapan(query, isExact);
			} else {
				searchForEnglish(query, isExact);
			}
		}

		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			onClick(v);
			return true;
		}
	}
}
