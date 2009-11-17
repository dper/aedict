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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sk.baka.aedict.dict.LuceneSearch;
import sk.baka.aedict.dict.SearchQuery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

/**
 * Allows search for Kanji characters using a Radical lookup.
 * 
 * @author Martin Vysny
 * 
 */
public class KanjiSearchRadicalActivity extends AbstractActivity {
	private static final int PADDING = 3;
	private static final int SIZE = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanjisearch_radical);
		final TableLayout v = (TableLayout) findViewById(R.id.kanjisearchRadicals);
		// there is no stupid flow layout in the great Android. Oh well. Let's
		// emulate that with a table.
		radicalsPerRow = getWindowManager().getDefaultDisplay().getWidth() / (SIZE + 2 * PADDING);
		currentColumn = 0;
		row = null;
		int strokeCount = -1;
		// general idea: add two-state pushbutton-like textviews for each
		// radical. We cannot use ToggleButton as it is too large.
		for (final char radical : Radicals.RADICAL_ORDERING.toCharArray()) {
			final int strokes = Radicals.getRadical(radical).strokes;
			if (strokeCount != strokes) {
				strokeCount = strokes;
				addRadicalToggle(v, null, strokes);
			}
			addRadicalToggle(v, radical, strokes);
		}
		findViewById(R.id.btnRadicalsSearch).setOnClickListener(AedictApp.safe(new View.OnClickListener() {

			public void onClick(View v) {
				try {
					performSearch();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}));
		// check that KANJIDIC exists
		new SearchUtils(this).checkKanjiDic();
	}

	private int radicalsPerRow;
	private TableRow row = null;
	private int currentColumn = 0;

	/**
	 * 
	 * @param v
	 * @param radical
	 * @param strokes
	 */
	private void addRadicalToggle(final TableLayout v, final Character radical, final int strokes) {
		if (currentColumn++ >= radicalsPerRow) {
			row = null;
			currentColumn = 0;
		}
		if (row == null) {
			row = new TableRow(this);
			v.addView(row);
		}
		int drawable = radical != null ? Radicals.getRadical(radical).resource : -1;
		final View vv;
		if (drawable != -1) {
			final ImageView iv = new ImageView(this);
			vv = iv;
			iv.setImageResource(drawable);
			iv.setMinimumHeight(SIZE + 2 * PADDING);
			iv.setMinimumWidth(SIZE + 2 * PADDING);
			iv.setScaleType(ScaleType.FIT_CENTER);
		} else {
			final TextView tv = new TextView(this);
			vv = tv;
			tv.setText(radical == null ? String.valueOf(strokes) : radical.toString());
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(SIZE);
			if (radical == null) {
				tv.setBackgroundColor(0xFF993333);
			}
		}
		vv.setPadding(PADDING, PADDING, PADDING, PADDING);
		if (radical != null) {
			final PushButtonListener pbl = new PushButtonListener(radical);
			vv.setOnClickListener(pbl);
			vv.setTag(pbl);
		}
		row.addView(vv);
	}

	/**
	 * Adds a ToggleButton-like functionality to a view.
	 */
	private final class PushButtonListener implements View.OnClickListener {

		public PushButtonListener(char radical) {
			super();
			this.radical = radical;
		}

		public final char radical;
		private boolean pushed = false;

		public boolean isPushed() {
			return pushed;
		}

		public void onClick(View v) {
			pushed = !pushed;
			v.setBackgroundColor(pushed ? 0xFF449977 : 0x00000000);
			recomputeRadical();
		}
	}

	/**
	 * Updates the activity caption to reflect selected radicals.
	 */
	private void recomputeRadical() {
		final String selectedRadicals = getRadicals();
		this.setTitle(getString(R.string.kanjiRadicalLookup) + ": " + selectedRadicals);
	}

	/**
	 * Computes currently selected radicals.
	 */
	private String getRadicals() {
		final StringBuilder sb = new StringBuilder();
		final TableLayout v = (TableLayout) findViewById(R.id.kanjisearchRadicals);
		for (int i = 0; i < v.getChildCount(); i++) {
			final TableRow tr = (TableRow) v.getChildAt(i);
			for (int j = 0; j < tr.getChildCount(); j++) {
				final PushButtonListener pbl = (PushButtonListener) tr.getChildAt(j).getTag();
				if (pbl != null && pbl.isPushed()) {
					sb.append(pbl.radical);
				}
			}
		}
		return sb.toString();
	}

	private Integer getInt(final int editResId) {
		final String text = ((EditText) findViewById(editResId)).getText().toString();
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/**
	 * Performs kanji search.
	 * 
	 * @throws IOException
	 */
	private void performSearch() throws IOException {
		final String radicals = getRadicals();
		if (radicals.length() == 0) {
			new AndroidUtils(this).showErrorDialog("No radicals selected");
			return;
		}
		final Integer strokes = getInt(R.id.editKanjiStrokes);
		Integer plusMinus = getInt(R.id.editKanjiStrokesPlusMinus);
		if (plusMinus != null) {
			if (plusMinus < 0) {
				plusMinus = -plusMinus;
			}
			if (plusMinus > 3) {
				plusMinus = 3;
			}
		}
		final Set<Character> matches = Radicals.getKanjisWithRadicals(radicals.toCharArray());
		// filter the matches based on stroke count
		if (strokes != null) {
			final LuceneSearch ls = new LuceneSearch(true);
			try {
				for (final Iterator<Character> kanjis = matches.iterator(); kanjis.hasNext();) {
					final char kanji = kanjis.next();
					final SearchQuery sq = SearchQuery.kanjiSearch(kanji, strokes, plusMinus);
					final List<String> result = ls.search(sq);
					if (result.isEmpty()) {
						// the kanji didn't matched
						kanjis.remove();
					}
				}
			} finally {
				MiscUtils.closeQuietly(ls);
			}
		}
		// we have the kanjis. launch the analyze activity
		final StringBuilder kanjis = new StringBuilder();
		for (final Character kanji : matches) {
			kanjis.append(kanji);
		}
		final Intent i = new Intent(this, KanjiAnalyzeActivity.class);
		i.putExtra(KanjiAnalyzeActivity.INTENTKEY_WORD, kanjis.toString());
		startActivity(i);
	}
}