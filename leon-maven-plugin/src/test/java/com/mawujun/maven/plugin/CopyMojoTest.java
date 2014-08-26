package com.mawujun.maven.plugin;

import java.io.File;
import java.net.URL;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class CopyMojoTest {
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
    	URL url=this.getClass().getResource("CopyMojoTestpom.xml");
    	File pom = new File(url.getFile());
        assertNotNull( pom );
        assertTrue( pom.exists() );

        CopyMojo generateP = (CopyMojo) rule.lookupMojo( "copy", pom );
        assertNotNull( generateP );
        generateP.execute();
    }
}
