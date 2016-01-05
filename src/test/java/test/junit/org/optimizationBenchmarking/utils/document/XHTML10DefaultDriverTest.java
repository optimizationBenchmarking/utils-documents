package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10ConfigurationBuilder;
import org.optimizationBenchmarking.utils.io.IFileType;

/** The default XHTML 1.0 driver test */
public class XHTML10DefaultDriverTest extends DocumentConfigurationTest {

  /** create the test */
  public XHTML10DefaultDriverTest() {
    super(new XHTML10ConfigurationBuilder().immutable());
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    return new IFileType[] { XHTML.XHTML_1_0 };
  }
}
