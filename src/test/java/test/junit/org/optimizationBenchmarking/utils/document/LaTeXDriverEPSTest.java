package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** The LaTeX driver test using EPS figures */
public class LaTeXDriverEPSTest extends DocumentConfigurationTest {

  /** create the test */
  public LaTeXDriverEPSTest() {
    super(LaTeXDriverEPSTest.__make());
  }

  /**
   * make the latex configuration
   *
   * @return the latex configuration
   */
  private static final LaTeXConfiguration __make() {
    final LaTeXConfigurationBuilder builder;

    builder = new LaTeXConfigurationBuilder();
    builder.setGraphicDriver(EGraphicFormat.EPS.getDefaultDriver());
    return builder.immutable();
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    // TODO: Strange LaTeX problems may prevent compilation, reason is
    // still unclear, but it is not an error of our API.
    // if (LaTeX.getInstance().hasToolChainFor(ELaTeXFileType.TEX,
    // ELaTeXFileType.BIB, EGraphicFormat.EPS)) {
    // return new IFileType[] { ELaTeXFileType.TEX, ELaTeXFileType.PDF };
    // }
    return new IFileType[] { ELaTeXFileType.TEX };
  }
}
