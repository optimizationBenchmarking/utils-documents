package test.junit.org.optimizationBenchmarking.utils.bibliography;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

import examples.org.optimizationBenchmarking.utils.bibliography.RandomBibliography;

/**
 * a test case to test generating a random bibliography with the
 * bibliography api
 */
public class RandomBibliographyTestWithIO extends BibliographyTestWithIO {

  /** create */
  public RandomBibliographyTestWithIO() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography createBibliography() {
    return new RandomBibliography().call();
  }

}
