package com.jcandksolutions.gradle.androidunittest

import com.android.build.gradle.api.ApplicationVariant

import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.junit.Before
import org.junit.Test

import static org.fest.assertions.api.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

public class AppVariantWrapperTest {
  private ApplicationVariant mVariant
  private AppVariantWrapper mTarget
  private Project mProject

  @Before
  public void setUp() {
    DependencyInjector.provider = new MockProvider()
    mProject = DependencyInjector.provideProject()
    mVariant = mock(ApplicationVariant.class)
    mTarget = new AppVariantWrapper(mVariant)
  }

  @Test
  public void testCreateRealMergedResourcesDirName() {
    when(mProject.buildDir).thenReturn(new File("build"))
    when(mVariant.dirName).thenReturn("mVariant")
    String resourcesDirName = mTarget.createRealMergedResourcesDirName()
    assertThat(resourcesDirName).isEqualTo("build${File.separator}intermediates${File.separator}res${File.separator}mVariant".toString())
  }

  @Test
  public void testGetAndroidCompileTask() {
    JavaCompile javaCompile = mock(JavaCompile.class)
    when(mVariant.javaCompile).thenReturn(javaCompile)
    assertThat(mTarget.androidCompileTask).isEqualTo(javaCompile)
  }
}
