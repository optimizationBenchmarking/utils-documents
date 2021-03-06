package test.junit.org.optimizationBenchmarking.utils.document;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

import examples.org.optimizationBenchmarking.utils.document.TemplateDocumentExample;
import shared.FileProducerCollector;
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
    final IDocumentBuilder builder;
    final String dontCompileLaTeX;
    Logger useLogger;

    builder = this.getInstance().createDocumentBuilder();
    builder.setBasePath(basePath);
    builder.setMainDocumentNameSuggestion(name);
    if (listener != null) {
      builder.setFileProducerListener(listener);
    }
    if (logger != null) {
      builder.setLogger(logger);
    }
    if (builder instanceof LaTeXDocumentBuilder) {
      dontCompileLaTeX = System.getProperty("dontCompileLaTeX");//$NON-NLS-1$
      if (dontCompileLaTeX != null) {
        if (logger != null) {
          useLogger = logger;
        } else {
          useLogger = Logger.getGlobal();
        }
        if ((useLogger != null) && (useLogger.isLoggable(Level.WARNING))) {
          useLogger.warning(((//
          "Compilation of LaTeX document has been switched off via System.getProperty(\"dontCompileLaTeX\")=\"" //$NON-NLS-1$ "
              + dontCompileLaTeX) + '"') + '.');
        }
        ((LaTeXDocumentBuilder) builder).setCompile(false);
      }
    }

    return builder.create();
  }

  /** {@inheritDoc} */
  @Override
  protected final IDocumentDriver getDocumentDriver(
      final DocumentConfiguration type) {
    return type.getDocumentDriver();
  }

  /**
   * Test the serial generation of documents
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testSerialTemplateDocumentCreation() throws Throwable {
    final DocumentConfiguration config;
    FileProducerCollector files;

    config = this.getInstance();
    Assert.assertNotNull(config);
    try {
      files = new FileProducerCollector();

      try (final TempDir td = new TempDir()) {
        try (final IDocument doc = this.createDocument(config,
            td.getPath(), "document", files, null)) { //$NON-NLS-1$
          new TemplateDocumentExample(doc).run();
        }
      }

      files.assertFilesOfType(this.getRequiredTypes());
    } finally {
      files = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    try {
      this.testSerialRandomDocumentCreation();
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
