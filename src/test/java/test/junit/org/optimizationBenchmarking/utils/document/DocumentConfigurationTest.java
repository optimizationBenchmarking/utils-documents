package test.junit.org.optimizationBenchmarking.utils.document;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

import shared.junit.org.optimizationBenchmarking.utils.document.DocumentDriverTest;

/** A test of a document driver */
@Ignore
public abstract class DocumentConfigurationTest
    extends DocumentDriverTest<DocumentConfiguration> {

  /**
   * create
   *
   * @param config
   *          the configuration
   */
  public DocumentConfigurationTest(final DocumentConfiguration config) {
    super(config);
  }

  /** {@inheritDoc} */
  @Override
  protected final IDocument createDocument(
      final DocumentConfiguration type, final Path basePath,
      final String name, final IFileProducerListener listener,
      final Logger logger) {
    return this.getInstance().createDocument(basePath, name, listener,
        logger);
  }

  /** {@inheritDoc} */
  @Override
  protected final IDocumentDriver getDocumentDriver(
      final DocumentConfiguration type) {
    return type.getDocumentDriver();
  }
}
