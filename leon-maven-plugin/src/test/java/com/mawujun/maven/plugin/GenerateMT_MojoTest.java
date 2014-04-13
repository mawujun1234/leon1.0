package com.mawujun.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class GenerateMT_MojoTest {
	@Rule
    public MojoRule rule = new MojoRule()
    {
      @Override
      protected void before() throws Throwable 
      {
      }

      @Override
      protected void after()
      {
      }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testSomething()
        throws Exception
    {
        //File pom = rule.getTestFile( "src/test/resources/unit/project-to-test/testpom.xml" );
    	File pom = new File("E:/eclipse/workspace/leon/leon-maven-plugin/src/test/java/com/mawujun/maven/plugin/GenerateMT_Mojopom.xml");
        assertNotNull( pom );
        assertTrue( pom.exists() );

        GenerateMT_Mojo generateDMojo = (GenerateMT_Mojo) rule.lookupMojo( "generateMT", pom );
        assertNotNull( generateDMojo );
        generateDMojo.execute();
    }

//    /** Do not need the MojoRule. */
//    @WithoutMojo
//    @Test
//    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn()
//    {
//
//    }
}
