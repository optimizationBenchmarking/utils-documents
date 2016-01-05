package test.junit.org.optimizationBenchmarking.utils.bibliography;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

import examples.org.optimizationBenchmarking.utils.bibliography.YearSpanningTestBibliography;

/**
 * a test case to test generating a paper whose date spans multiple years
 */
public class YearSpanningBibliographyTestWithIO
    extends BibliographyTestWithIO {

  /** create */
  public YearSpanningBibliographyTestWithIO() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography createBibliography() {
    return new YearSpanningTestBibliography().call();
  }
}
