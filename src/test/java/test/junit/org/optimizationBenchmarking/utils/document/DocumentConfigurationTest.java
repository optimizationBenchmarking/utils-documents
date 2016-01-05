package test.junit.org.optimizationBenchmarking.utils.document;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

import examples.org.optimizationBenchmarking.utils.document.TemplateDocumentExample;
import shared.FileProducerCollector;
import shared.junit.CategorySlowTests;
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

  /**
   * test the document driver for creating template-based documents
   *
   * @param service
   *          the service
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doSerialTemplateTest(final ExecutorService service)
      throws IOException, InterruptedException, ExecutionException {
    final DocumentConfiguration config;
    TemplateDocumentExample example;
    FileProducerCollector files;
    Future<?> future;

    config = this.getInstance();
    Assert.assertNotNull(config);
    try {
      files = new FileProducerCollector();

      try (final TempDir td = new TempDir()) {
        try (final IDocument doc = this.createDocument(config,
            td.getPath(), "document", files, null)) { //$NON-NLS-1$
          example = new TemplateDocumentExample(doc);
          try {
            if (service != null) {
              try {
                future = service.submit(example);
                future.get();
              } finally {
                future = null;
              }
            } else {
              example.run();
            }
          } finally {
            example = null;
          }
        }
      }

      files.assertFilesOfType(this.getRequiredTypes());
    } finally {
      files = null;
    }

    MemoryUtils.fullGC();
  }

  /**
   * Test the serial generation of documents
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testSerialTemplateDocumentCreation() throws Throwable {
    this.__doSerialTemplateTest(null);
  }

  /**
   * test the document driver
   *
   * @param proc
   *          the processors
   * @param fifo
   *          do this fifo-style
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doParallelTemplateTest(final int proc,
      final boolean fifo)
          throws IOException, InterruptedException, ExecutionException {
    final ForkJoinPool p;

    p = new ForkJoinPool(proc,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, fifo);

    this.__doSerialTemplateTest(p);

    p.shutdown();
    p.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    MemoryUtils.fullGC();
  }

  /**
   * Test the fifo parallel generation of documents with 1 processor
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelTemplateDocumentCreation_1_fifo()
      throws Throwable {
    this.__doParallelTemplateTest(1, true);
  }

  /**
   * Test the default parallel generation of documents with 1 processor
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelTemplateDocumentCreation_1_default()
      throws Throwable {
    this.__doParallelTemplateTest(1, false);
  }

  /**
   * Test the fifo parallel generation of documents with 2 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelTemplateDocumentCreation_2_fifo()
      throws Throwable {
    this.__doParallelTemplateTest(2, true);
  }

  /**
   * Test the default parallel generation of documents with 2 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelTemplateDocumentCreation_2_default()
      throws Throwable {
    this.__doParallelTemplateTest(2, false);
  }

  /**
   * Test the fifo parallel generation of documents with 3 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testParallelTemplateDocumentCreation_3_fifo()
      throws Throwable {
    this.__doParallelTemplateTest(3, true);
  }

  /**
   * Test the default parallel generation of documents with 3 processors
   *
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testParallelTemplateDocumentCreation_3_default()
      throws Throwable {
    this.__doParallelTemplateTest(3, false);
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    try {
      this.testParallelTemplateDocumentCreation_1_default();
      this.testParallelTemplateDocumentCreation_1_fifo();
      this.testParallelTemplateDocumentCreation_2_default();
      this.testParallelTemplateDocumentCreation_2_fifo();
      this.testParallelTemplateDocumentCreation_3_default();
      this.testParallelTemplateDocumentCreation_3_fifo();
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
