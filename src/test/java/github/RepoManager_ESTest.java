/*
 * This file was automatically generated by EvoSuite
 * Tue Aug 22 10:18:38 GMT 2017
 */

package github;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import github.RepoManager;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class RepoManager_ESTest extends RepoManager_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      RepoManager repoManager0 = new RepoManager();
      // Undeclared exception!
      try { 
        repoManager0.push("", (Git) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("github.RepoManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      RepoManager repoManager0 = new RepoManager();
      // Undeclared exception!
      try { 
        repoManager0.pullRequest((String) null, "PRa0,");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("github.RepoManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      RepoManager repoManager0 = new RepoManager();
      try { 
        repoManager0.clone("{\"title\":\"Test suites from EvoSuite\",\"body\":\"It includes a folder named test in src, generated by EvoSuite\",\"head\":\"YONGSHUONI:master\",\"base\":\"master\"}", "curl --user YONGSHUONI:nys19931002Nys -X POST ");
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Cannot start processes in a unit test
         //
         verifyException("org.evosuite.runtime.mock.java.lang.MockRuntime", e);
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      RepoManager repoManager0 = new RepoManager();
      try { 
        repoManager0.fork("https://github.com/YONGSHUONI/", "jZ)Ugi_ 5?e");
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Cannot start processes in a unit test
         //
         verifyException("org.evosuite.runtime.mock.java.lang.MockRuntime", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      RepoManager repoManager0 = new RepoManager();
      try { 
        repoManager0.pullRequest("0 5QOY`", " [Tv9mVR^&45");
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Cannot start processes in a unit test
         //
         verifyException("org.evosuite.runtime.mock.java.lang.MockRuntime", e);
      }
  }
}
