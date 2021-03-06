/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2021 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.formulaeditor;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.testsuites.annotations.Cat;
import org.catrobat.catroid.testsuites.annotations.Level;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.uiespresso.content.brick.utils.BrickTestUtils;
import org.catrobat.catroid.uiespresso.formulaeditor.utils.FormulaEditorWrapper;
import org.catrobat.catroid.uiespresso.util.UiTestUtils;
import org.catrobat.catroid.uiespresso.util.rules.FragmentActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import androidx.annotation.StringRes;

import static org.catrobat.catroid.uiespresso.content.brick.utils.BrickDataInteractionWrapper.onBrickAtPosition;
import static org.catrobat.catroid.uiespresso.formulaeditor.utils.FormulaEditorWrapper.onFormulaEditor;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@Category({Cat.AppUi.class, Level.Smoke.class})
@RunWith(Parameterized.class)
public class FormulaEditorListsListTest {
	@Rule
	public FragmentActivityTestRule<SpriteActivity> baseActivityTestRule = new
			FragmentActivityTestRule<>(SpriteActivity.class, SpriteActivity.EXTRA_FRAGMENT_POSITION, SpriteActivity.FRAGMENT_SCRIPTS);

	@Parameterized.Parameters(name = "{2}" + "-Test")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { {R.string.formula_editor_function_number_of_items,
				R.string.formula_editor_function_number_of_items_parameter,"number of items"},
				{R.string.formula_editor_function_list_item,
						R.string.formula_editor_function_list_item_parameter,"item"},{R.string.formula_editor_function_contains,
				R.string.formula_editor_function_contains_parameter,"contains"}});
	}

	@Parameterized.Parameter
	public @StringRes
	int formulaEditorList;

	@Parameterized.Parameter(1)
	public @StringRes int formulaEditorListParameter;

	@Parameterized.Parameter(2)
	public String testName;

	private static Integer whenBrickPosition = 0;
	private static Integer changeSizeBrickPosition = 1;

	@Before
	public void setUp() throws Exception {
		Script script = BrickTestUtils.createProjectAndGetStartScript("FormulaEditorListsListTest");
		script.addBrick(new ChangeSizeByNBrick(0));
		baseActivityTestRule.launchActivity();
	}


	@Test
	public void testFunctionsListElements() {
		onBrickAtPosition(whenBrickPosition).checkShowsText(R.string.brick_when_started);
		onBrickAtPosition(changeSizeBrickPosition).checkShowsText(R.string.brick_change_size_by);
		onBrickAtPosition(changeSizeBrickPosition).onChildView(withId(R.id.brick_change_size_by_edit_text))
				.perform(click());

		String formulaEditorFunctionString = UiTestUtils.getResourcesString(formulaEditorList);
		String formulaEditorFunctionParameterString = UiTestUtils.getResourcesString(formulaEditorListParameter);
		String editorFunction = formulaEditorFunctionString + formulaEditorFunctionParameterString;
		String selectedFunctionString = getSelectedFunctionString(editorFunction);

		onFormulaEditor()
				.performOpenCategory(FormulaEditorWrapper.Category.LISTS)
				.performSelect(editorFunction);
		  //      .perform(typeText(stringToBetyped), closeSoftKeyboard());

		onFormulaEditor()
				.checkShows(selectedFunctionString);
	}

	private String getSelectedFunctionString(String functionString) {
		return functionString
				.replaceAll("^(.+?)\\(", "$1( ")
				.replace(",", " , ")
				.replace("-", "- ")
				.replaceAll("\\)$", " )")
				.concat(" ");
	}



































}
