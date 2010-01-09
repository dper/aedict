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

package sk.baka.aedict.kanji;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the {@link KanjiUtils} class.
 * 
 * @author Martin Vysny
 */
public class KanjiUtilsTest {
	@Test
	public void testKanji() {
		assertTrue(KanjiUtils.isKanji('艦'));
		assertFalse(KanjiUtils.isKanji('か'));
		assertFalse(KanjiUtils.isKanji('キ'));
	}

	@Test
	public void testKatakana() {
		assertFalse(KanjiUtils.isKatakana('艦'));
		assertFalse(KanjiUtils.isKatakana('か'));
		assertTrue(KanjiUtils.isKatakana('キ'));
	}

	@Test
	public void testHiragana() {
		assertFalse(KanjiUtils.isHiragana('艦'));
		assertTrue(KanjiUtils.isHiragana('か'));
		assertFalse(KanjiUtils.isHiragana('キ'));
	}

	@Test
	public void testHalfwidthKatakana() {
		assertFalse(KanjiUtils.isHalfwidth('艦'));
		assertFalse(KanjiUtils.isHalfwidth('か'));
		assertFalse(KanjiUtils.isHalfwidth('キ'));
		assertTrue(KanjiUtils.isHalfwidth('ｶ'));
	}

	@Test
	public void testToHalfwidthKatakana() {
		assertEquals("ﾊﾟﾊﾟ", KanjiUtils.toHalfwidth("パパ"));
		assertEquals("ｺﾝﾋﾟｭｰﾀｰ", KanjiUtils.toHalfwidth("コンピューター"));
		assertEquals("JOZOﾊﾟﾊﾟFOO", KanjiUtils.toHalfwidth("JOZOパパFOO"));
		assertEquals("FOOBARBAZ", KanjiUtils.toHalfwidth("FOOBARBAZ"));
	}

	@Test
	public void testToFullwidthKatakana() {
		assertEquals("パパ", KanjiUtils.halfwidthToKatakana("ﾊﾟﾊﾟ"));
		assertEquals("コンピューター", KanjiUtils.halfwidthToKatakana("ｺﾝﾋﾟｭｰﾀｰ"));
		assertEquals("JOZOパパFOO", KanjiUtils.halfwidthToKatakana("JOZOﾊﾟﾊﾟFOO"));
		assertEquals("FOOBARBAZ", KanjiUtils.halfwidthToKatakana("FOOBARBAZ"));
	}
}