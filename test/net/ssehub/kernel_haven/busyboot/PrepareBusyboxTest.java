/*
 * Copyright 2018-2019 University of Hildesheim, Software Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ssehub.kernel_haven.busyboot;

import static net.ssehub.kernel_haven.util.null_checks.NullHelpers.notNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.ssehub.kernel_haven.util.Util;
import net.ssehub.kernel_haven.util.null_checks.NonNull;

/**
 * Tests the {@link PrepareBusybox}.
 * 
 * @author Adam
 */
public class PrepareBusyboxTest {
    
    private static final @NonNull File TESTDATA = new File("testdata/prepare_busybox");
    
    private static final @NonNull File TMP_DIR = new File(TESTDATA, "tmp");
    
    /**
     * Cleans (or creates) the temporary directory before each test.
     * 
     * @throws IOException If cleaning the directory fails.
     */
    @Before
    public void createOrClearTmpDir() throws IOException {
        Util.clearFolder(TMP_DIR);
    }

    /**
     * Tests line substitution with no lines (empty array).
     */
    @Test
    public void testSubstituteLineContinuationEmpty() {
        List<@NonNull String> input = notNull(Arrays.asList());
        List<@NonNull String> expected = notNull(Arrays.asList());
        
        assertThat(PrepareBusybox.substituteLineContinuation(input), is(expected));
    }
    
    /**
     * Tests line substitution with nothing to substitute.
     */
    @Test
    public void testSubstituteLineContinuationNoSubstitution() {
        List<@NonNull String> input = notNull(Arrays.asList(
            "",
            "/* Hello World */",
            "",
            "int main() {",
            "    return 0;",
            "}",
            ""
        ));
        List<@NonNull String> expected = notNull(Arrays.asList(
            "",
            "/* Hello World */",
            "",
            "int main() {",
            "    return 0;",
            "}",
            ""
        ));
        
        assertThat(PrepareBusybox.substituteLineContinuation(input), is(expected));
    }
    
    /**
     * Tests line substitution with only a simple substitution.
     */
    @Test
    public void testSubstituteLineContinuationSimpleSubstitution() {
        List<@NonNull String> input = notNull(Arrays.asList(
            "ab \\",
            "c"
        ));
        List<@NonNull String> expected = notNull(Arrays.asList(
            "ab c"
        ));
        
        assertThat(PrepareBusybox.substituteLineContinuation(input), is(expected));
    }
    
    /**
     * Tests line substitution with multiple following substitutions.
     */
    @Test
    public void testSubstituteLineContinuationMultipleSubstitution() {
        List<@NonNull String> input = notNull(Arrays.asList(
            "a\\",
            "b\\",
            "c"
        ));
        List<@NonNull String> expected = notNull(Arrays.asList(
            "abc"
        ));
        
        assertThat(PrepareBusybox.substituteLineContinuation(input), is(expected));
    }
    
    /**
     * Tests line substitution without a following line.
     */
    @Test
    public void testSubstituteLineContinuationNoFollowingLine() {
        List<@NonNull String> input = notNull(Arrays.asList(
            "a\\"
        ));
        List<@NonNull String> expected = notNull(Arrays.asList(
            "a"
        ));
        
        assertThat(PrepareBusybox.substituteLineContinuation(input), is(expected));
    }
    
    /**
     * Tests line substitution with another backslash in the middle of the line.
     */
    @Test
    public void testSubstituteLineContinuationBackslashInMiddle() {
        List<@NonNull String> input = notNull(Arrays.asList(
            "a \\",
            "\\ b",
            "a \\ c",
            "a \\ b \\",
            "c"
        ));
        List<@NonNull String> expected = notNull(Arrays.asList(
            "a \\ b",
            "a \\ c",
            "a \\ b c"
        ));
        
        assertThat(PrepareBusybox.substituteLineContinuation(input), is(expected));
    }
    
}
